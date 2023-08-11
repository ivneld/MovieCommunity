package Movie.MovieCommunity.service;

import Movie.MovieCommunity.JPADomain.*;
import Movie.MovieCommunity.JPARepository.*;
import Movie.MovieCommunity.JPADomain.Comment;
import Movie.MovieCommunity.JPADomain.JpaWeeklyBoxOffice;
import Movie.MovieCommunity.JPARepository.dao.MovieWithWeeklyDao;
import Movie.MovieCommunity.JPARepository.dao.ProposeMovieDao;
import Movie.MovieCommunity.advice.assertThat.DefaultAssert;
import Movie.MovieCommunity.dataCollection.MovieDataService;
import Movie.MovieCommunity.naverApi.ApiExamTranslateNmt;
import Movie.MovieCommunity.util.CalendarUtil;
import Movie.MovieCommunity.util.CustomPageImpl;
import Movie.MovieCommunity.web.apiDto.credit.CreditDetailSearchDto;
import Movie.MovieCommunity.web.apiDto.movie.entityDto.CreditDto;
import Movie.MovieCommunity.web.apiDto.movie.entityDto.MovieDetailSearchDto;
import Movie.MovieCommunity.web.apiDto.movie.entityDto.SeriesDto;
import Movie.MovieCommunity.web.apiDto.movie.response.*;
import info.movito.themoviedbapi.model.keywords.Keyword;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;

import static Movie.MovieCommunity.JPADomain.QMember.member;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final MemberRepository memberRepository;
    private final MovieDataService movieDataService;
    private final WeeklyBoxOfficeRepository weeklyBoxOfficeRepository;
    private final ApiExamTranslateNmt apiExamTranslateNmt;
    private final LikeMovieRepository likeMovieRepository;
    private final ActorRepository actorRepository;
    private final QuerydslMovieRepository querydslMovieRepository;

    public List<YearRankingResponse> yearRanking(int openDt, Long memberId){
        Member member;
        if(memberId != null){
            member = memberRepository.findById(memberId).orElseThrow();
        } else {
            member = null;
        }
        int startDt = openDt*10000;
        int endDt = (openDt+1)*10000;
        List<Movie> yearRankingByOpenDt = movieRepository.findYearRankingByOpenDt(startDt, endDt);
        List<YearRankingResponse> response = new ArrayList<>();
        String url = null;
        int i = 1;
        for (Movie m : yearRankingByOpenDt) {
            long count = m.getLikeMovies().stream().filter(lm -> lm.getMember() == member).count();

            int topCnt = 0;
            YearRankingResponse yearRankingResponse = YearRankingResponse.builder()
                    .rank(i)
                    .id(m.getId())
                    .movieNm(m.getMovieNm())
                    .showTm(m.getShowTm())
                    .openDt(m.getOpenDt())
                    .prdtStatNm(m.getPrdtStatNm())
                    .voteAverage(m.getVoteAverage())
                    .watchGradeNm(m.getWatchGradeNm())
                    .overview(m.getOverview().length() > 80 ? m.getOverview().substring(0, 80)+"..." : m.getOverview())
                    .posterPath(m.getPosterPath())
                    .interest(m.getLikeMovies().size())
                    .myInterest(count > 0 ? true : false)
                    .build();
            if (i==1 && m.getVideos() != null) {
                url = m.getVideos().get(0).getUrl();
                yearRankingResponse.setUrl(url);
            }
            for (Comment comment : m.getComments()) {
                if(topCnt < comment.getLikeComments().size()){
                    topCnt = comment.getLikeComments().size();
                    yearRankingResponse.setTopComment(comment.getContent());
                }
            }
            i++;
            response.add(yearRankingResponse);
        }
        return response;
    }

    public MovieDetailResponse movieDetail(Long movieId, Long memberId){
        try {Member member;
            if(memberId != null){
                member = memberRepository.findById(memberId).orElseThrow();
            } else {
                member = null;
            }

            Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new EntityNotFoundException("영화가 없습니다."));
            long count = movie.getLikeMovies().stream().filter(lm -> lm.getMember() == member).count();
            MovieDetailResponse movieDetailResponse = MovieDetailResponse.builder()
                    .id(movie.getId())
                    .tmId(movie.getTmId())
                    .posterPath(movie.getPosterPath())
                    .interest(movie.getLikeMovies().size())
                    .myInterest(count > 0 ? true : false)
                    .movieNm(movie.getMovieNm())
                    .openDt(movie.getOpenDt())
                    .voteAverage(movie.getVoteAverage())
                    .watchGradeNm(movie.getWatchGradeNm())
                    .showTm(movie.getShowTm())
                    .nationNm(movie.getNationNm())
                    .audiAcc(movie.getAudiAcc())
                    .overview(movie.getOverview())
                    .build();
            if (movie.getVideos().size() != 0){movieDetailResponse.setVideoUrl(movie.getVideos().get(0).getUrl());}
            for (JpaMovieWithCompany mc : movie.getMovieWithCompanies()) {
                if(mc.getCompany().getCompanyPartNm().equals("제작사")){
                    movieDetailResponse.addCompany(mc.getCompany().getCompanyNm());
                }
            }
            for (JpaMovieWithGenre mg : movie.getMovieWithGenres()) {
                movieDetailResponse.addGenre(mg.getGenre().getGenreNm());
            }
            for (MovieWithCredit mc : movie.getMovieWithCredits()) {
                Credit credit = mc.getCredit();
                try{
                    System.out.println("credit.getActorNm() = " + credit.getActorNm());
                    String tlName = apiExamTranslateNmt.translation(credit.getActorNm());
                    credit.updateNm(tlName);
                    System.out.println("mc.getCast() = " + mc.getCast());
                    String tlCast = apiExamTranslateNmt.translation(mc.getCast());
                    mc.updateCast(tlCast);}
                catch (RuntimeException e){

                }
                movieDetailResponse.addCredit(new CreditDto(credit.getId(), credit.getActorNm(), credit.getProfile_path(), mc.getCast(), credit.getCreditCategory()));
            }
            movieDetailResponse.setWeeklyRanks(getWeeklyRankingResponses(movie));
            if(movie.getCollectionId()!=null && movie.getCollectionId() != 0) {
                List<SeriesDto> seriesDtos = movieDataService.selectSeries(movie.getCollectionId());
                for (SeriesDto seriesDto : seriesDtos) {
                    movieDataService.collectByTmId(seriesDto.getId());
                }
                movieDetailResponse.setSeries(seriesDtos);
            }
            return movieDetailResponse;
        }catch(EntityNotFoundException e){

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return null;
    }


    /**
     * date 기준 주간 랭킹 영화들의 keyword 를 제목, 줄거리에 포함하고 있는 영화들을 반환
     * <p>
     * key : keyword(String)
     * value : Movie List
     *
     * @param date
     * @return
     */
    public List<WeeklyResponse> weeklyRankingByDate(LocalDate date, Long memberId) {
        List<WeeklyResponse> weeklyResponses = new ArrayList<>();

        String weekOfDay = getWeekOfDay(date);
        List<MovieWithWeeklyDao> byShowRange = querydslMovieRepository.findByShowRange(weekOfDay);
        int rank = 1;
        for (MovieWithWeeklyDao movieWithWeeklyDao : byShowRange) {

            WeeklyResponse response = WeeklyResponse.builder()
                    .rank(rank)
                    .id(movieWithWeeklyDao.getId())
                    .movieNm(movieWithWeeklyDao.getMovieNm())
                    .showTm(movieWithWeeklyDao.getShowTm())
                    .openDt(movieWithWeeklyDao.getOpenDt())
                    .prdtStatNm(movieWithWeeklyDao.getPrdtStatNm())
                    .watchGradeNm(movieWithWeeklyDao.getWatchGradeNm())
                    .overview(movieWithWeeklyDao.getOverview())
                    .posterPath(movieWithWeeklyDao.getPosterPath())
                    .voteAverage(movieWithWeeklyDao.getVoteAverage())
                    .build();

            Movie movie = movieRepository.getById(response.getId());
            Optional<Member> optionalMember = memberRepository.findById(memberId);
            if (optionalMember.isPresent()) {
                Member member = optionalMember.get();
                long count = movie.getLikeMovies().stream().filter(lm -> lm.getMember() == member).count();
                response.setMyInterest(count > 0 ? true : false);
            }
            if (response.getRank() == 1) {
                List<Video> videos = movie.getVideos();
                response.setVideos(videos);
                response.setInterest(movie.getLikeMovies().size());
            }
            weeklyResponses.add(response);

            rank++;
        }
        return weeklyResponses;
    }

    public List<ProposeMovieResponse> proposeMovie(LocalDate date, Long memberId) {

        HashMap<String, ProposeMovieResponse> map = proposeByDateOrLikeMovie(date, memberId);
        return map.values()
                .stream()
                .filter(i -> i.getMovies().size() > 0)
                .collect(Collectors.toList());
    }
//    public List<ProposeMovieResponse> proposeByNowDayMovie(LocalDate date) {
//        String showRange = getWeekOfDay(date);
//        List<JpaWeeklyBoxOffice> weeklyMovieByDate = weeklyBoxOfficeRepository.findByShowRange(showRange);
////        List<JpaWeeklyBoxOffice> weeklyMovieByDate1 = getWeeklyMovieByDateOrderByRanking(date);
//
//        HashMap<String, List<Movie>> map = new HashMap<>();
//
//        for (JpaWeeklyBoxOffice movie : weeklyMovieByDate) {
//            Optional<Movie> byMovieCd = movieRepository.findByMovieCd(movie.getMovieCd());
////            DefaultAssert.isOptionalPresent(byMovieCd);
//            if (byMovieCd.isPresent()) {
//                List<Keyword> keywords = movieDataService.searchKeyWord(byMovieCd.get());
//
//                for (Keyword keyword : keywords) {
//                    List<Movie> movies = movieRepository.findByKeyword(keyword.getName());
//                    if (!map.containsKey(keyword.getName())) {  // 키워드 중복 제거
//                        map.put(keyword.getName(), movies);
//                    }
//                }
//            }
//        }
//
//        return mapToProposeMovieResponseList(map);
//    }
//
//    public List<ProposeMovieResponse> proposeMovie(LocalDate date, Long memberId) {
//        List<LikeMovie> likeMovies = likeMovieRepository.findByMemberId(memberId);
//
//        if (likeMovies.size() >= 5) {
//            HashMap<String, List<Movie>> map = new HashMap<>();
//
//            for (LikeMovie likeMovie : likeMovies) {
//                Optional<Movie> findMovie = movieRepository.findById(likeMovie.getId());
//                DefaultAssert.isOptionalPresent(findMovie);
//
//                List<Keyword> keywords = movieDataService.searchKeyWord(findMovie.get());
//                for (Keyword keyword : keywords) {
//                    List<Movie> movies = movieRepository.findByKeyword(keyword.getName());
//                    if (!map.containsKey(keyword.getName())) {
//                        map.put(keyword.getName(), movies);
//                    }
//                }
//            }
//            return mapToProposeMovieResponseList(map);
//        }
//        else {
//            return proposeByNowDayMovie(date);
//        }
//    }
//
//    private static List<ProposeMovieResponse> mapToProposeMovieResponseList(HashMap<String, List<Movie>> map) {
//        List<ProposeMovieResponse> result = new ArrayList<>();
//        ArrayList<String> keyList = new ArrayList<>(map.keySet());
//        for (String s : keyList) {
//            if (!map.get(s).isEmpty()) {
//                result.add(ProposeMovieResponse.builder()
//                        .keyword(s)
//                        .movies(map.get(s))
//                        .build());
//            }
//        }
//        return result;
//    }


    private List<WeeklyRankingResponse> getWeeklyRankingResponses(Movie movie) {
        List<JpaWeeklyBoxOffice> weeklyList = weeklyBoxOfficeRepository.findByMovieCdOrderByYearWeekTime(movie.getMovieCd());
        List<WeeklyRankingResponse> weeklyRankingResponses = new ArrayList<>();
        for (JpaWeeklyBoxOffice w : weeklyList) {
            System.out.println("weeklyList.size() = " + weeklyList.size());
            System.out.println("w = " + w);
            WeeklyRankingResponse response = WeeklyRankingResponse.builder()
                    .rank(w.getRanking())
                    .rankInten(w.getRankInten())
                    .rankOldAndNew(w.getRankOldAndNew())
                    .build();
            String date = w.getShowRange().substring(0, 8);
            System.out.println("date = " + date);
            int year = Integer.parseInt(date.substring(0, 4));
            int month = Integer.parseInt(date.substring(4, 6));
            int day = Integer.parseInt(date.substring(6, 8));
            String currentWeekOfMonth = CalendarUtil.getCurrentWeekOfMonth(year,month,day);
            String[] split = currentWeekOfMonth.split(",");
            String week = year +"년 "+ split[0]+"월 "+split[1]+"주차";
            System.out.println("week = " + week);
            response.setWeek(week);
            weeklyRankingResponses.add(response);
        }
        return weeklyRankingResponses;
    }

    public void interest(Long movieId, Long memberId) {
        Optional<Movie> findMovie = movieRepository.findById(movieId);
        Optional<Member> findMember = memberRepository.findById(memberId);
        DefaultAssert.isOptionalPresent(findMovie);
        DefaultAssert.isOptionalPresent(findMember);
        Optional<LikeMovie> findLikeMovie = likeMovieRepository.findByMovieIdAndMemberId(movieId, memberId);
        if(findLikeMovie.isPresent()){
            likeMovieRepository.delete(findLikeMovie.get());
        }else{
            LikeMovie likeMovie = LikeMovie.builder()
                    .movie(findMovie.get())
                    .member(findMember.get()).build();
            likeMovieRepository.save(likeMovie);
        }
    }
    @Transactional(readOnly = true)
    public List<MovieSearchResponse> movieSearch(String movieNm) {
        List<Movie> movies = movieRepository.findTop5ByMovieNmStartingWith(movieNm);
        List<MovieSearchResponse> responseList = movies.stream().map(m -> MovieSearchResponse.builder()
                .id(m.getId())
                .posterPath(m.getPosterPath())
                .movieNm(m.getMovieNm())
                .openDt(m.getOpenDt())
                .build()
        ).collect(Collectors.toList());
        return responseList;
    }
    @Transactional(readOnly = true)
    public SearchDetailResponse detailSearch(String search){
        List<Movie> movies = movieRepository.findTop4ByMovieNmContaining(search);
        int movieCnt = movieRepository.countByMovieNmContaining(search);
        List<MovieDetailSearchDto> movieSearch = movies.stream().map(m -> MovieDetailSearchDto.builder()
                .id(m.getId())
                .posterPath(m.getPosterPath())
                .movieNm(m.getMovieNm())
                .openDt(m.getOpenDt())
                .nationNm(m.getNationNm())
                .build()
        ).collect(Collectors.toList());

        List<Credit> credits = actorRepository.findTop4ByActorNmContaining(search);
        int creditCnt = actorRepository.countByActorNmContaining(search);
        List<CreditDetailSearchDto> creditSearch = credits.stream().map(c -> CreditDetailSearchDto.builder()
                .id(c.getId())
                .actorNm(c.getActorNm())
                .creditCategory(c.getCreditCategory())
                .profileUrl(c.getProfile_path())
                .build()
        ).collect(Collectors.toList());

        return new SearchDetailResponse(movieCnt, creditCnt, movieSearch, creditSearch);
    }
    @Transactional(readOnly = true)
    public CustomPageImpl<MovieDetailSearchDto> movieDetailSearch(String search, Pageable pageable){
        Page<Movie> movies   = movieRepository.findPageByMovieNmContaining(search, pageable);
        List<MovieDetailSearchDto> movieSearch = movies.stream().map(m -> MovieDetailSearchDto.builder()
                .id(m.getId())
                .posterPath(m.getPosterPath())
                .movieNm(m.getMovieNm())
                .openDt(m.getOpenDt())
                .nationNm(m.getNationNm())
                .build()
        ).collect(Collectors.toList());
        return new CustomPageImpl<>(movieSearch, pageable, movies.getTotalElements());
    }

    @Transactional(readOnly = true)
    public CustomPageImpl<ComingMovieResponse> comingMovie(Pageable pageable){
        String openDt = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")); //* 현재 날짜 이후 조건 추가
        Page<Movie> movies = movieRepository.findComingMovieByPrdtStatNmAndOpenDt("개봉예정", Integer.valueOf(openDt), pageable);
        List<ComingMovieResponse> comingMovies = movies.stream().map(m -> ComingMovieResponse.builder()
                .id(m.getId())
                .movieNm(m.getMovieNm())
                .openDt(m.getOpenDt())
                .posterPath(m.getPosterPath())
                .lastDay((int)ChronoUnit.DAYS.between(LocalDate.now(), toLocalDate(m.getOpenDt())))
                .build()
        ).collect(Collectors.toList());
        return new CustomPageImpl<>(comingMovies, pageable, movies.getTotalElements());

    }

    private static LocalDate toLocalDate(int date) {
        LocalDate yyyyMMdd = LocalDate.parse(String.valueOf(date),
                DateTimeFormatter.ofPattern("yyyyMMdd"));
        return yyyyMMdd;
    }

    private String getWeekOfDay(LocalDate date) {
        int year = date.getYear();
        int week = date.get(WeekFields.ISO.weekOfYear());
        LocalDate startDate = LocalDate.of(year, 1, 1);
        TemporalField weekOfYear = WeekFields.of(Locale.KOREA).weekOfYear();
        int startWeek = startDate.get(weekOfYear);
        int targetWeek = startWeek + week - 1;

        LocalDate firstDayOfWeek = startDate.with(weekOfYear, targetWeek).plusDays(1);
        LocalDate lastDayOfWeek = firstDayOfWeek.plusDays(6);

        String showRange = firstDayOfWeek.format(DateTimeFormatter.ofPattern("YYYYMMdd")) + "~" + lastDayOfWeek.format(DateTimeFormatter.ofPattern("YYYYMMdd"));
        return showRange;
    }

    private HashMap<String, ProposeMovieResponse> proposeByDateOrLikeMovie(LocalDate date, Long memberId) {
        List<LikeMovie> likeMovies = likeMovieRepository.findByMemberId(memberId);
        HashMap<String, ProposeMovieResponse> map = new HashMap<>();

        if (likeMovies.size() >= 5) {
            for (LikeMovie likeMovie : likeMovies) {
                Movie movie = likeMovie.getMovie();
                saveMap(memberId, map, movie);
            }
        }
        else{
            String weekOfDay = getWeekOfDay(date);
            List<MovieWithWeeklyDao> byShowRange = querydslMovieRepository.findByShowRange(weekOfDay);
            for (MovieWithWeeklyDao movieWithWeeklyDao : byShowRange) {
                Movie movie = movieRepository.getById(movieWithWeeklyDao.getId());
                saveMap(memberId, map, movie);
            }
        }

        return map;
    }

    private HashMap<String, ProposeMovieResponse> proposeByDateWithLikeMovie(LocalDate date, Long memberId) {
        List<LikeMovie> likeMovies = likeMovieRepository.findByMemberId(memberId);
        HashMap<String, ProposeMovieResponse> map = new HashMap<>();

            for (LikeMovie likeMovie : likeMovies) {
                Movie movie = likeMovie.getMovie();
                saveMap(memberId, map, movie);
            }

            String weekOfDay = getWeekOfDay(date);
            List<MovieWithWeeklyDao> byShowRange = querydslMovieRepository.findByShowRange(weekOfDay);
            for (MovieWithWeeklyDao movieWithWeeklyDao : byShowRange) {
                Movie movie = movieRepository.getById(movieWithWeeklyDao.getId());
                saveMap(memberId, map, movie);
            }

        return map;
    }

    private void saveMap(Long memberId, HashMap<String, ProposeMovieResponse> map, Movie movie) {
        try {
            List<Keyword> keywords = movieDataService.searchKeyWord(movie);

            for (Keyword keyword : keywords) {
                if (!map.containsKey(keyword.getName())) {
                    List<ProposeMovieDao> list = new ArrayList<>();
                    List<Movie> findMovies = movieRepository.findByKeyword(keyword.getName());
                    for (Movie findMovie : findMovies) {
                        ProposeMovieDao build = ProposeMovieDao.builder()
                                .movieId(findMovie.getId())
                                .movieNm(findMovie.getMovieNm())
                                .showTm(findMovie.getShowTm())
                                .openDt(findMovie.getOpenDt())
                                .prdtStatNm(findMovie.getPrdtStatNm())
                                .watchGradeNm(findMovie.getWatchGradeNm())
                                .overview(findMovie.getOverview())
                                .posterPath(findMovie.getPosterPath())
                                .voteAverage(findMovie.getVoteAverage())
                                .interest(findMovie.getLikeMovies().size())
                                .build();
                        Optional<Member> optionalMember = memberRepository.findById(memberId);
                        if (optionalMember.isPresent()) {
                            Member member = optionalMember.get();
                            long count = movie.getLikeMovies().stream().filter(lm -> lm.getMember() == member).count();
                            build.setMyInterest(count > 0 ? true : false);
                        }

                        list.add(build);
                    }
                    map.put(keyword.getName(), new ProposeMovieResponse(keyword.getName(), list));
                }
            }
        } catch (Exception e) {
            log.info(e.getMessage());
            log.info("error movieId={}", movie.getId());
        }
    }

}
