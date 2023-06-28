package Movie.MovieCommunity.service;

import Movie.MovieCommunity.JPADomain.*;
import Movie.MovieCommunity.JPARepository.*;
import Movie.MovieCommunity.JPADomain.Comment;
import Movie.MovieCommunity.JPADomain.JpaWeeklyBoxOffice;
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
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;

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


    public List<YearRankingResponse> weeklyRanking(LocalDate date) {
        List<JpaWeeklyBoxOffice> weeklyBoxOffices = getWeeklyMovieByDateOrderByRanking(date);

        List<YearRankingResponse> result = new ArrayList<>();

        for (JpaWeeklyBoxOffice weeklyBoxOffice : weeklyBoxOffices) {
            if (movieRepository.findByMovieCd(weeklyBoxOffice.getMovieCd()).isPresent()) {
                Movie movie = movieRepository.findByMovieCd(weeklyBoxOffice.getMovieCd()).get();

                result.add(YearRankingResponse.builder()
                        .rank(weeklyBoxOffice.getRanking())
                        .id(movie.getId())
                        .movieNm(movie.getMovieNm())
                        .showTm(movie.getShowTm())
                        .openDt(movie.getOpenDt())
                        .prdtStatNm(movie.getPrdtStatNm())
                        .watchGradeNm(movie.getWatchGradeNm())
                        .overview(movie.getOverview())
                        .posterPath(movie.getPosterPath())
                        .voteAverage(movie.getVoteAverage())
                        .interest(movie.getLikeMovies().size())
                        .build());
            }
        }
        return result;
    }

    /**
     * date 기준 주간 랭킹 영화들의 keyword 를 제목, 줄거리에 포함하고 있는 영화들을 반환
     *
     * key : keyword(String)
     * value : Movie List
     * @param date
     * @return
     */
    public List<ProposeMovieResponse> proposeMovie(LocalDate date) {
        List<JpaWeeklyBoxOffice> weeklyMovieByDate = getWeeklyMovieByDateOrderByRanking(date);

        HashMap<String, List<Movie>> map = new HashMap<>();

        for (JpaWeeklyBoxOffice movie : weeklyMovieByDate) {
            Optional<Movie> byMovieCd = movieRepository.findByMovieCd(movie.getMovieCd());
            DefaultAssert.isOptionalPresent(byMovieCd);
            List<Keyword> keywords = movieDataService.searchKeyWord(byMovieCd.get());

            for (Keyword keyword : keywords) {
                List<Movie> movies = movieRepository.findByKeyword(keyword.getName());
                if (!map.containsKey(keyword.getName())) {
                    map.put(keyword.getName(), movies);
                }
            }
        }

        return mapToProposeMovieResponseList(map);
    }

    private static List<ProposeMovieResponse> mapToProposeMovieResponseList(HashMap<String, List<Movie>> map) {
        List<ProposeMovieResponse> result = new ArrayList<>();
        ArrayList<String> keyList = new ArrayList<>(map.keySet());
        for (String s : keyList) {
            if (!map.get(s).isEmpty()) {
                result.add(ProposeMovieResponse.builder()
                        .keyword(s)
                        .movies(map.get(s))
                        .build());
            }
        }
        return result;
    }


    private List<JpaWeeklyBoxOffice> getWeeklyMovieByDateOrderByRanking(LocalDate date) {
        int year = date.getYear();
        int week = date.get(WeekFields.ISO.weekOfYear());

        LocalDate startDate = LocalDate.of(year, 1, 1);
        TemporalField weekOfYear = WeekFields.of(Locale.KOREA).weekOfYear();

        int startWeek = startDate.get(weekOfYear);
        int targetWeek = startWeek + week - 1;

        LocalDate firstDayOfWeek = startDate.with(weekOfYear, targetWeek);
        LocalDate lastDayOfWeek = firstDayOfWeek.plusDays(6);

        List<JpaWeeklyBoxOffice> weeklyBoxOffices = weeklyBoxOfficeRepository.findByOpenDtBetweenOrderByRankingAsc(firstDayOfWeek, lastDayOfWeek);
        return weeklyBoxOffices;
    }


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
        List<MovieDetailSearchDto> movieSearch = movies.stream().map(m -> MovieDetailSearchDto.builder()
                .id(m.getId())
                .posterPath(m.getPosterPath())
                .movieNm(m.getMovieNm())
                .openDt(m.getOpenDt())
                .nationNm(m.getNationNm())
                .build()
        ).collect(Collectors.toList());

        List<Credit> credits = actorRepository.findTop4ByActorNmContaining(search);
        List<CreditDetailSearchDto> creditSearch = credits.stream().map(c -> CreditDetailSearchDto.builder()
                .id(c.getId())
                .actorNm(c.getActorNm())
                .creditCategory(c.getCreditCategory())
                .profileUrl(c.getProfile_path())
                .build()
        ).collect(Collectors.toList());

        return new SearchDetailResponse(movieSearch, creditSearch);
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
}
