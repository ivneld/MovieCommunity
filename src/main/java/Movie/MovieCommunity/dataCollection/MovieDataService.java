package Movie.MovieCommunity.dataCollection;

import Movie.MovieCommunity.JPADomain.*;
import Movie.MovieCommunity.JPADomain.dto.*;
import Movie.MovieCommunity.JPARepository.*;
import Movie.MovieCommunity.JPARepository.MovieRepository;
import Movie.MovieCommunity.domain.*;

import Movie.MovieCommunity.web.form.CommentForm;
import com.querydsl.core.Tuple;
import kr.or.kobis.kobisopenapi.consumer.rest.KobisOpenAPIRestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class MovieDataService {

    private final GenreRepository genreRepository;
    private final ActorRepository actorRepository;

    private final MovieRepository movieRepository;
    private final CompanyRepository companyRepository;
    private final MovieWithGenreRepository movieWithGenreRepository;
    private final MovieWithActorRepository movieWithActorRepository;
    private final MovieWithCompanyRepository movieWithCompanyRepository;
    private final WeeklyBoxOfficeRepository weeklyBoxOfficeRepository;
    private final WeeklyBoxOfficeRepositoryCustom weeklyBoxOfficeRepositoryCustom;

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    @Autowired
    private final EntityManager em;

//    @Value("${movie-api-key}")
    private String key = "633a3302093ec75c112d1afac4eb1ba5";
    private String response;
//    private Company company = new Company();
//    private Genre genre = new Genre();
//    private Actor actor = new Actor();
    private KobisOpenAPIRestService service = new KobisOpenAPIRestService(key);
    private ThreadLocal<MovieDto> threadMovie = new ThreadLocal<>();
    private ThreadLocal<Long> threadTotCnt = new ThreadLocal<>();
    private ThreadLocal<LocalDate> threadStartDay = new ThreadLocal<>();

    private HashMap<String, EtcData> etcData = new HashMap<>();


    @PostConstruct
    public void Testing() throws Exception {

//        movieDataCollection("2022");
//        InitData();// movieDataCollection("2022") 실행 후 사용




//        yearWeeklyBoxOfficeData("20220101");
        //movieDetailData();
/*        MovieSearchCond cond = new MovieSearchCond(null, 20230201);
        List<Movie> list = movieRepository.findByFilter(cond);
        List<Movie> byPageNum = movieRepository.findByPageNum(list, 1);
        log.info("list={}", byPageNum);*/
//
//        setMovieCd("2022");
//        setMovieEtcData("2022");
//        log.info("data={}",etcData);
//        setMovieEtc();
    }

    /**
     *  movieDataCollection("2022") 실행 후 사용
     */
    @Transactional
    public void InitData(){

        Member member1 = new Member("email1@naver.com", "password", "nickname1", Authority.ROLE_USER);
        Member member2 = new Member("email2@naver.com", "password", "nickname2", Authority.ROLE_USER);
        Member member3 = new Member("email3@naver.com", "password", "nickname3", Authority.ROLE_USER);
        Member member4 = new Member("email4@naver.com", "password", "nickname4", Authority.ROLE_USER);

        Optional<JpaMovie> movie1 = movieRepository.findById(1l);
        Optional<JpaMovie> movie2 = movieRepository.findById(2l);
        Optional<JpaMovie> movie3 = movieRepository.findById(3l);
        Optional<JpaMovie> movie4 = movieRepository.findById(4l);

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        memberRepository.save(member4);

        for(int i = 0 ; i<20;i++){
            Board board = null;
            switch (i%4){
                case 1:
                    board = Board.builder()
                            .title("title" + i)
                            .content("content" + i)
                            .movie(movie1.get())
                            .member(member1)
                            .build();
                    break;
                case 2:
                    board = Board.builder()
                            .title("title"+i)
                            .content("content"+i)
                            .movie(movie2.get())
                            .member(member3)
                            .build();
                    break;
                case 3:
                    board = Board.builder()
                            .title("title"+i)
                            .content("content"+i)
                            .movie(movie3.get())
                            .member(member4)
                            .build();
                    break;
                default:
                    board = Board.builder()
                            .title("title"+i)
                            .content("content"+i)
                            .movie(movie4.get())
                            .member(member2)
                            .build();
                    break;
            }
            boardRepository.save(board);
        }

        for(int i = 0 ; i<20;i++) {
            Optional<Board> board = boardRepository.findById(1l);
            Comment comment = null;
            CommentForm commentForm = null;
            if (i<4){
                commentForm = new CommentForm("comment"+i, member1, board.get(), null);

            }else {
                if (i%2==0) {
                    Optional<Comment> parent = commentRepository.findById(1l);
                    commentForm = new CommentForm("comment" + i, member2, board.get(), parent.get());
                }else{
                    Optional<Comment> parent = commentRepository.findById(2l);
                    commentForm = new CommentForm("comment" + i, member2, board.get(), parent.get());
                }
            }
            comment = new Comment(commentForm);
            commentRepository.save(comment);


        }



    }
    /**
     *  movieDataCollection with movieDetailData
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void movieDataCollection(String openStartDt) throws Exception {
        Map<String, String> param = new HashMap<>();
        //param.put("curPage", "1");
        //param.put("itemPerPage","0");
        param.put("openStartDt", openStartDt);
        threadTotCnt.set(10L);
        for(int i=0; i< threadTotCnt.get()/10 + 1; i++){
            String curPage = String.valueOf(i+1);
            log.info("curPage = {}", curPage);
            param.put("curPage", curPage);
            response = service.getMovieList(true, param);
            JSONParser jsonParser = new JSONParser();
            Object parse = jsonParser.parse(response);
            JSONObject ps = (JSONObject) parse;
            JSONObject movieListResult = (JSONObject)ps.get("movieListResult");
            //Long totCnt = (Long)movieListResult.get("totCnt");
            if (i==0)
                threadTotCnt.set((Long)movieListResult.get("totCnt"));
            JSONArray movieList = (JSONArray)movieListResult.get("movieList");

/*        log.info("list = {}",totCnt);
        log.info("list = {}",movieListResult);*/



            for(int j=0;j< movieList.size();j++){
                JSONObject movieData = (JSONObject)movieList.get(j);
                String movieCd = (String)movieData.get("movieCd");
                //String movieNm = (String)movieData.get("movieNm");
                movieDetailData(movieCd);
                //log.info("movieList = {}", movieList);
            }

            //log.info("cnt = {}", ps);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void movieDetailData(String movieCd) throws Exception {

        List<String> keyNames = new ArrayList<>();

        // 임의로 ID 생성
        //movie.setId(1L);
        response = service.getMovieInfo(true, movieCd);

        JSONParser jsonParser = new JSONParser();
        Object parse = jsonParser.parse(response);
        JSONObject ps = (JSONObject) parse;
        JSONObject movieInfoResult = (JSONObject)ps.get("movieInfoResult");
        //log.info("{}",movieInfoResult);
        JSONObject movieInfo = (JSONObject)movieInfoResult.get("movieInfo");
        //String movieCd = (String) movieInfo.get("movieCd");
        String movieNm = (String) movieInfo.get("movieNm");

        Integer showTm = Integer.valueOf((String) movieInfo.get("showTm"));
        Integer openDt = Integer.valueOf((String) movieInfo.get("openDt"));
        String prdtStatNm = (String) movieInfo.get("prdtStatNm");
        String typeNm = (String) movieInfo.get("typeNm");
        MovieDto movieDto = new MovieDto();
        movieDto.setMovieCd(movieCd);
        movieDto.setMovieNm(movieNm);
        movieDto.setShowTm(showTm);
        movieDto.setOpenDt(openDt);
        movieDto.setPrdtStatNm(prdtStatNm);
        movieDto.setTypeNm(typeNm);
        threadMovie.set(movieDto);
        JpaMovie movie = new JpaMovie(movieDto);

        JpaMovie savedMovie = movieRepository.save(movie);
        keyNames.add("nationNm");
        JSONArrayExtracted(movieInfo,"nations", keyNames, null, savedMovie);
        //Company company = new Company();

        // 임의로 ID 생성
/*        genre.setId(1L);
        actor.setId(1L);
        company.setId(1L);*/

        keyNames.add("genreNm");
        JSONArrayExtracted(movieInfo,"genres", keyNames, new GenreDto(), savedMovie);

        keyNames.add("peopleNm");
        JSONArrayExtracted(movieInfo,"directors", keyNames, null, savedMovie);

        keyNames.add("peopleNm");
        keyNames.add("cast");
        JSONArrayExtracted(movieInfo,"actors", keyNames, new ActorDto(), savedMovie);

        keyNames.add("companyCd");
        keyNames.add("companyNm");
        keyNames.add("companyNmEn");
        keyNames.add("companyPartNm");
        JSONArrayExtracted(movieInfo,"companys", keyNames, new CompanyDto(), savedMovie);

        keyNames.add("auditNo");
        keyNames.add("watchGradeNm");
        JSONArrayExtracted(movieInfo,"audits", keyNames, null, savedMovie);


        log.info("movie = {}",movie);
        threadMovie.remove();
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public void JSONArrayExtracted(JSONObject havingJsonArray, String arrayName, List<String> keyNames, Object domain, JpaMovie movie) {
        MovieDto movieDto = threadMovie.get();
        MovieWithCompany movieWithCompany = new MovieWithCompany();

        MovieWithGenre movieWithGenre = new MovieWithGenre();
        MovieWithActor movieWithActor = new MovieWithActor();
        JSONArray array = (JSONArray) havingJsonArray.get(arrayName);
        CompanyDto companyDto = null;
        JpaActor actor = null;
        String cast =null;
        JpaMovieWithActor jpaMovieWithActor = null;
        for(int i=0;i<array.size();i++)
        {
            if (domain instanceof CompanyDto) {
                companyDto = (CompanyDto) domain;
            }
             else if(domain instanceof ActorDto){
                 jpaMovieWithActor = new JpaMovieWithActor();
                jpaMovieWithActor.setMovie(movie);
            }


            JSONObject g = (JSONObject)array.get(i);
            for(int j=0;j<keyNames.size();j++)
            {
                String data = (String) g.get(keyNames.get(j));
                if (domain != null) {
                    if (domain instanceof GenreDto) {
                        JpaMovieWithGenre jpaMovieWithGenre;
                        JpaGenre genre = new JpaGenre(data);
                        Optional<JpaGenre> genreNm = genreRepository.findByGenreNm(data); // 장르가 있으면 패스, 없으면 저장
                        if (genreNm.isPresent()) {
                            jpaMovieWithGenre = new JpaMovieWithGenre(movie, genreNm.get());
                        } else{
                            JpaGenre savedGenre = genreRepository.save(genre);
                            jpaMovieWithGenre = new JpaMovieWithGenre(movie, savedGenre);
                        }
                        //movie.addMovieWithGenre(jpaMovieWithGenre);
                        movieWithGenreRepository.save(jpaMovieWithGenre);

                    }
                    else if (domain instanceof ActorDto) {
                        if (keyNames.get(j).equals("peopleNm")) {
                             actor = new JpaActor(data);
                            Optional<JpaActor> actorNm = actorRepository.findByActorNm(data); // 장르가 있으면 패스, 없으면 저장
                            if (actorNm.isPresent()) {
                                jpaMovieWithActor.setActor(actorNm.get());
                            } else{
                                JpaActor savedActor = actorRepository.save(actor);
                                jpaMovieWithActor.setActor(savedActor);
                            }
                        }
                        else if (keyNames.get(j).equals("cast")) {
                            cast = data;
                            jpaMovieWithActor.setCast(data);
                        }

                    } else if (domain instanceof CompanyDto) {

                        if (keyNames.get(j).equals("companyCd"))
                            companyDto.setCompanyCd(data);
                        if (keyNames.get(j).equals("companyNmEn"))
                            companyDto.setCompanyNmEn(data);
                        if (keyNames.get(j).equals("companyPartNm"))
                            companyDto.setCompanyPartNm(data);
                        if (keyNames.get(j).equals("companyNm"))
                            companyDto.setCompanyNm(data);
                    }
                }
                else if (keyNames.get(j).equals("nationNm")){
                    movieDto.setNationNm(data);
                }
                else if (keyNames.get(j).equals("auditNo"))
                    movieDto.setAuditNo(data);
                else if (keyNames.get(j).equals("watchGradeNm"))
                    movieDto.setWatchGradeNm(data);
                else if (keyNames.get(j).equals("peopleNm"))
                    movieDto.setDirectorNm(data);
                //log.info("{}",data);

            }
            if (domain instanceof ActorDto){

                jpaMovieWithActor.updateData(movie,cast);
                movieWithActorRepository.save(jpaMovieWithActor);

            }
            else if (domain instanceof CompanyDto) {
                JpaCompany jpaCompany = new JpaCompany(companyDto);
                companyRepository.save(jpaCompany);
                JpaMovieWithCompany jpaMovieWithCompany = new JpaMovieWithCompany(movie, jpaCompany);

                movieWithCompanyRepository.save(jpaMovieWithCompany);
            }

        }

        movie.updateData(movieDto);
        movieRepository.save(movie);


        keyNames.clear();
    }

    @Transactional
    public void yearWeeklyBoxOfficeData(String targetDt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate startDate = LocalDate.parse("20220101", formatter);
/*        LocalDate nextDate = startDate.plusDays(7);
        LocalDate localDate = startDate.plusWeeks(1);
        int compare = startDate.compareTo(nextDate);
        log.info("start = {}", startDate);
        log.info("next = {}", nextDate);
        log.info("compareTo = {}", localDate);*/


        LocalDate date = LocalDate.parse(targetDt, formatter);
        int nextYear = date.getYear() + 1;
        threadStartDay.set(date);
        Map<String, String> param = new HashMap<>();
        param.put("targetDt", targetDt);
        param.put("weekGb", "0");
        int targetYear = date.getYear();

        while (threadStartDay.get().getYear() != nextYear) {
            WeeklyBoxOfficeDto weeklyBoxOfficeDto = new WeeklyBoxOfficeDto();
//            WeeklyBoxOffice weeklyBoxOffice = new WeeklyBoxOffice();

            try {
                response = service.getWeeklyBoxOffice(true, param);
                JSONParser jsonParser = new JSONParser();
                Object parse = jsonParser.parse(response);
                JSONObject ps = (JSONObject) parse;
                JSONObject boxOfficeResult = (JSONObject) ps.get("boxOfficeResult");
                log.info("ps = {}", boxOfficeResult);

                String boxofficeType = (String) boxOfficeResult.get("boxofficeType");
                String showRange = (String) boxOfficeResult.get("showRange");
                String stringDay = showRange.substring(0, 8);
                LocalDate dateDay = LocalDate.parse(stringDay, formatter);
                threadStartDay.set(dateDay);
                String nextTargetDt = dateDay.plusWeeks(1).format(formatter);
                //String stringNTDt = String.valueOf(nextTargetDt);
                log.info("stringNTDt = {}", nextTargetDt);
                param.put("targetDt", nextTargetDt);

                String yearWeekTime = (String) boxOfficeResult.get("yearWeekTime");
                weeklyBoxOfficeDto.setBoxofficeType(boxofficeType);
                weeklyBoxOfficeDto.setShowRange(showRange);
                weeklyBoxOfficeDto.setYearWeekTime(yearWeekTime);
                JSONArray weeklyBoxOfficeList = (JSONArray) boxOfficeResult.get("weeklyBoxOfficeList");
                // log.info("{}", weeklyBoxOfficeList);
                for (int i = 0; i < weeklyBoxOfficeList.size(); i++) {
                    JSONObject weekly = (JSONObject) weeklyBoxOfficeList.get(i);

                    Integer rnum = Integer.valueOf((String) weekly.get("rnum"));
                    Integer ranking = Integer.valueOf((String) weekly.get("rank"));
                    Integer rankInten = Integer.valueOf((String) weekly.get("rankInten"));
                    String rankOldAndNew = (String) weekly.get("rankOldAndNew");
                    String movieCd = (String) weekly.get("movieCd");
                    LocalDate openDt = LocalDate.parse((String) weekly.get("openDt"));
                    Long salesAcc = Long.valueOf((String) weekly.get("salesAcc"));
                    Long audiAcc = Long.valueOf((String) weekly.get("audiAcc"));


                    weeklyBoxOfficeDto.setRnum(rnum);
                    weeklyBoxOfficeDto.setRanking(ranking);
                    weeklyBoxOfficeDto.setRankInten(rankInten);
                    weeklyBoxOfficeDto.setRankOldAndNew(rankOldAndNew);
                    weeklyBoxOfficeDto.setMovieCd(movieCd);
                    weeklyBoxOfficeDto.setOpenDt(openDt);
                    weeklyBoxOfficeDto.setSalesAcc(salesAcc);
                    weeklyBoxOfficeDto.setAudiAcc(audiAcc);
//                    Optional<JpaMovie> movie = movieRepository.findByMovieCd(movieCd);


                    JpaWeeklyBoxOffice jpaWeeklyBoxOffice = new JpaWeeklyBoxOffice(weeklyBoxOfficeDto);

                    weeklyBoxOfficeRepository.save(jpaWeeklyBoxOffice);
                    log.info("weeklyBoxOffice = {}", jpaWeeklyBoxOffice);

                }

            } catch (Exception e) {
                log.info(e.getMessage());
            }

        }
        threadStartDay.remove();
    }


//    public void setMovieEtc() {
//        List<JpaWeeklyBoxOffice> weeklyBoxOffices = weeklyBoxOfficeRepository.findAll();
//
//        for (JpaWeeklyBoxOffice weeklyBoxOffice : weeklyBoxOffices) {
//
//            if(movieRepository.findByMovieCd(weeklyBoxOffice.getMovieCd()).isPresent()) {
//                JpaMovie movie = movieRepository.findByMovieCd(weeklyBoxOffice.getMovieCd()).get();
//
//                if (movie.getSalesAcc() == null || movie.getSalesAcc() < weeklyBoxOffice.getSalesAcc()) {
//                    movie.setSalesAcc(weeklyBoxOffice.getSalesAcc());
//                }
//                if (movie.getAudiAcc() == null || movie.getAudiAcc() < weeklyBoxOffice.getAudiAcc()) {
//                    movie.setAudiAcc(weeklyBoxOffice.getAudiAcc());
//                }
//                if (weeklyBoxOffice.getRanking() <= 10) {
//                    movie.setTopScore(movie.getTopScore() + (11 - weeklyBoxOffice.getRanking()));
//
//                    List<JpaMovieWithActor> allActor = movieWithActorRepository.findAllActor(movie.getId());
//                    allActor.stream().forEach(actor -> actor.getActor().setTopMovieCnt(actor.getActor().getTopMovieCnt() + 1));
//                }
//
//                movieRepository.save(movie);
//                log.info("movie={}", movie);
//            }
//        }
//    }

    // update
    public void setMovieEtcV2() {
        List<Tuple> tuples = weeklyBoxOfficeRepositoryCustom.movieWithWeekly();
        Tuple tuple1 = tuples.get(0);
        tuple1.get(QJpaMovie.jpaMovie.movieCd);
    }
    public void setTopMovieCnt() {
        List<JpaWeeklyBoxOffice> weeklyBoxOffices = weeklyBoxOfficeRepository.findByRankingLessThan(11);

        weeklyBoxOffices.stream().forEach(weeklyBoxOffice -> {
            if (movieRepository.findByMovieCd(weeklyBoxOffice.getMovieCd()).isPresent()) {
                JpaMovie movie = movieRepository.findByMovieCd(weeklyBoxOffice.getMovieCd()).get();

                List<JpaMovieWithActor> movieWithActors = movieWithActorRepository.findAllActor(movie.getId());
                movieWithActors.stream().forEach(movieWithActor -> {
                    JpaActor actor = movieWithActor.getActor();
                    actor.plusTopMovieCnt();


                    log.info("actor id={}", actor.getId());
                    log.info("-> cnt={}", actor.getTopMovieCnt());
                });
            }
        });

    }
}
