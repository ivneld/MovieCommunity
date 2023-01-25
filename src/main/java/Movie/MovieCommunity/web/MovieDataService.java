package Movie.MovieCommunity.web;

import Movie.MovieCommunity.domain.*;
import Movie.MovieCommunity.repository.*;
import kr.or.kobis.kobisopenapi.consumer.rest.KobisOpenAPIRestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class MovieDataService {
    private final JdbcTemplateActorRepository jdbcTemplateActorRepository;
    private final JdbcTemplateCompanyRepository jdbcTemplateCompanyRepository;
    private final JdbcTemplateGenreRepository jdbcTemplateGenreRepository;
    private final JdbcTemplateMovieWithActorRepository jdbcTemplateMovieWithActorRepository;
    private final JdbcTemplateMovieWithCompanyRepository jdbcTemplateMovieWithCompanyRepository;
    private final JdbcTemplateMovieWithGenreRepository jdbcTemplateMovieWithGenreRepository;
    private final MovieRepository movieRepository;
    private final JdbcTemplateWeeklyBoxOfficeRepository jdbcTemplateWeeklyBoxOfficeRepository;

    private final String key = "1d52608621856574e9228c92b7dfa738";
    private String response;
    private Company company = new Company();
    private Genre genre = new Genre();
    private Actor actor = new Actor();
    private KobisOpenAPIRestService service = new KobisOpenAPIRestService(key);
    private ThreadLocal<Movie> threadMovie = new ThreadLocal<>();
    private ThreadLocal<Long> threadTotCnt = new ThreadLocal<>();
    private ThreadLocal<LocalDate> threadStartDay = new ThreadLocal<>();

    private HashMap<String, EtcData> etcData = new HashMap<>();

    @PostConstruct
    @Transactional
    public void Testing() throws Exception {
/*        Movie movie = new Movie();
        movieRepository.save(movie);*/
//        movieDataCollection("2022");
//        yearWeeklyBoxOfficeData("20220101");
        //movieDetailData();
/*        MovieSearchCond cond = new MovieSearchCond(null, 20230201);
        List<Movie> list = movieRepository.findByFilter(cond);
        List<Movie> byPageNum = movieRepository.findByPageNum(list, 1);
        log.info("list={}", byPageNum);*/

        setMovieCd("2022");
        setMovieEtcData("2022");
//        log.info("data={}",etcData);
    }

    @Transactional
    private void movieDataCollection(String openStartDt) throws Exception {
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

    private void movieDetailData(String movieCd) throws Exception {

        List<String> keyNames = new ArrayList<>();
        threadMovie.set(new Movie());
        Movie movie = threadMovie.get();
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

        movie.setMovieCd(movieCd);
        movie.setMovieNm(movieNm);
        movie.setShowTm(showTm);
        movie.setOpenDt(openDt);
        movie.setPrdtStatNm(prdtStatNm);
        movie.setTypeNm(typeNm);
        movieRepository.save(movie);
        keyNames.add("nationNm");
        JSONArrayExtracted(movieInfo,"nations", keyNames, null);
        //Company company = new Company();

        // 임의로 ID 생성
/*        genre.setId(1L);
        actor.setId(1L);
        company.setId(1L);*/

        keyNames.add("genreNm");
        JSONArrayExtracted(movieInfo,"genres", keyNames, genre);

        keyNames.add("peopleNm");
        JSONArrayExtracted(movieInfo,"directors", keyNames, null);

        keyNames.add("peopleNm");
        keyNames.add("cast");
        JSONArrayExtracted(movieInfo,"actors", keyNames, actor);

        keyNames.add("companyCd");
        keyNames.add("companyNm");
        keyNames.add("companyNmEn");
        keyNames.add("companyPartNm");
        JSONArrayExtracted(movieInfo,"companys", keyNames, company);

        keyNames.add("auditNo");
        keyNames.add("watchGradeNm");
        JSONArrayExtracted(movieInfo,"audits", keyNames, null);

        movieRepository.update(movie);
        log.info("movie = {}",movie);
        threadMovie.remove();
    }

    private void JSONArrayExtracted(JSONObject havingJsonArray, String arrayName, List<String> keyNames, Object domain) {
        Movie movie = threadMovie.get();
        MovieWithCompany movieWithCompany = new MovieWithCompany();
        MovieWithGenre movieWithGenre = new MovieWithGenre();
        MovieWithActor movieWithActor = new MovieWithActor();
        JSONArray array = (JSONArray) havingJsonArray.get(arrayName);



        for(int i=0;i<array.size();i++)
        {
            if (domain instanceof Company) {
                company = jdbcTemplateCompanyRepository.save(company);
            } else if(domain instanceof Actor){
                actor = jdbcTemplateActorRepository.save(actor);
            }
            else if (domain instanceof Genre){
                genre = jdbcTemplateGenreRepository.save(genre);
            }

            JSONObject g = (JSONObject)array.get(i);
            for(int j=0;j<keyNames.size();j++)
            {
                String data = (String) g.get(keyNames.get(j));
                if (domain != null) {
                    if (domain instanceof Genre) {
                        genre.setGenreNm(data);
                        movieWithGenre.setMovieId(movie.getId());
                        movieWithGenre.setGenreId(genre.getId());
                    }
                    else if (domain instanceof Actor) {

                        movieWithActor.setMovieId(movie.getId());
                        if (keyNames.get(j).equals("peopleNm")) {
                            movieWithActor.setActorId(actor.getId());
                            actor.setActorNm(data);
                        }
                        else if (keyNames.get(j).equals("cast")) {
                            movieWithActor.setCast(data);
                        }



                    } else if (domain instanceof Company) {

                        if (keyNames.get(j).equals("companyCd"))
                            company.setCompanyCd(data);
                        if (keyNames.get(j).equals("companyNmEn"))
                            company.setCompanyNmEn(data);
                        if (keyNames.get(j).equals("companyPartNm"))
                            company.setCompanyPartNm(data);
                        if (keyNames.get(j).equals("companyNm"))
                            company.setCompanyNm(data);
                        //log.info("Company = {}", company);
                        

                        movieWithCompany.setMovieId(movie.getId());
                        movieWithCompany.setCompanyId(company.getId());
                    }
                }
                else if (keyNames.get(j).equals("nationNm"))
                    movie.setNationNm(data);
                else if (keyNames.get(j).equals("auditNo"))
                    movie.setAuditNo(data);
                else if (keyNames.get(j).equals("watchGradeNm"))
                    movie.setWatchGradeNm(data);
                else if (keyNames.get(j).equals("peopleNm"))
                    movie.setDirectorNm(data);
                //log.info("{}",data);

            }
            if (domain instanceof Genre) {
                jdbcTemplateGenreRepository.update(genre);
                jdbcTemplateMovieWithGenreRepository.save(movieWithGenre);
            }else if (domain instanceof  Actor){
                jdbcTemplateActorRepository.update(actor);
                jdbcTemplateMovieWithActorRepository.save(movieWithActor);
            }
            else if (domain instanceof Company) {
                jdbcTemplateCompanyRepository.update(company);
                jdbcTemplateMovieWithCompanyRepository.save(movieWithCompany);
            }

        }


        if (domain != null){

/*        else if (domain instanceof Genre) {
            jdbcTemplateMovieWithGenreRepository.save(movieWithGenre);
        }*/}

        keyNames.clear();
    }

    @Transactional
    private void yearWeeklyBoxOfficeData(String targetDt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate startDate = LocalDate.parse("20220101", formatter);
/*        LocalDate nextDate = startDate.plusDays(7);
        LocalDate localDate = startDate.plusWeeks(1);
        int compare = startDate.compareTo(nextDate);
        log.info("start = {}", startDate);
        log.info("next = {}", nextDate);
        log.info("compareTo = {}", localDate);*/


        LocalDate date = LocalDate.parse(targetDt, formatter);
        int nextYear = date.getYear()+1;
        threadStartDay.set(date);
        Map<String, String> param = new HashMap<>();
        param.put("targetDt", targetDt);
        param.put("weekGb","0");
        int targetYear = date.getYear();

        while(threadStartDay.get().getYear() != nextYear){
            WeeklyBoxOffice weeklyBoxOffice = new WeeklyBoxOffice();

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
                weeklyBoxOffice.setBoxofficeType(boxofficeType);
                weeklyBoxOffice.setShowRange(showRange);
                weeklyBoxOffice.setYearWeekTime(yearWeekTime);
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

                    weeklyBoxOffice.setRnum(rnum);
                    weeklyBoxOffice.setRanking(ranking);
                    weeklyBoxOffice.setRankInten(rankInten);
                    weeklyBoxOffice.setRankOldAndNew(rankOldAndNew);
                    weeklyBoxOffice.setMovieCd(movieCd);
                    weeklyBoxOffice.setOpenDt(openDt);
                    weeklyBoxOffice.setSalesAcc(salesAcc);
                    weeklyBoxOffice.setAudiAcc(audiAcc);
                    Optional<Movie> movie = movieRepository.findByMovieCd(movieCd);
                    jdbcTemplateWeeklyBoxOfficeRepository.save(weeklyBoxOffice);
                    log.info("weeklyBoxOffice = {}",weeklyBoxOffice);
                    //log.info("{}",movie);

                }

            } catch (Exception e) {
                log.info(e.getMessage());
            }

        }
        threadStartDay.remove();

    }

    /**
     * movie table 에서 targetDt의 년도와 일치하는 movieCd 저장
     * weeklyBoxOffice table에서 movieCd와 일치하는 데이터들을 내림차순 정렬 후 가장 앞의 값을 저장
     */
    private void setMovieCd(String targetDt) {
        List<Movie> movies = movieRepository.selectAll();

        for (int i = 0; i < movies.size(); i++) {
            String year = "";
            if (movies.get(i).getAuditNo() != null) {
                year = movies.get(i).getAuditNo().substring(0, 4);
            }

            if (targetDt.equals(year)) {
                EtcData data = new EtcData();
                data.setAudi_acc(0L);
                data.setSales_acc(0L);
                data.setTop_movie_cnt(0);
                data.setTop_score(0);
                etcData.put(movies.get(i).getMovieCd(), data);

            }
        }
    }


    /**
     *  movieCd -> movie (movie table)
     *  movie -> actor_id (movie with actor table)
     *  actor_id -> set top_movie (actor table)
     * @param targetDt
     */
    private void setMovieEtcData(String targetDt){
        List<WeeklyBoxOffice> list = jdbcTemplateWeeklyBoxOfficeRepository.selectAll();

        for (int i = 0; i < list.size(); i++) {
            String movieCd = list.get(i).getMovieCd();
            Long salesAcc = list.get(i).getSalesAcc();
            Long audiAcc = list.get(i).getAudiAcc();
            Integer rank = list.get(i).getRanking();


            EtcData etc = etcData.get(movieCd);


            if(etc != null) {

                if (rank <= 10) {
                    etc.setTop_score(etc.getTop_score() + 11 - rank);
                    etc.setTop_movie_cnt(etc.getTop_movie_cnt() + 1);

                    if (etc.getSales_acc().intValue() == 0) {
                        Movie movie = movieRepository.findByMovieCd(movieCd).get();
                        List<Actor> actors = jdbcTemplateMovieWithActorRepository.findActorByMovie(movie);

                        for (Actor actor : actors) {
                            jdbcTemplateActorRepository.increaseCnt(actor);
                        }
                    }


                if (salesAcc.intValue() > etc.getSales_acc().intValue()) {
                    etc.setSales_acc(salesAcc);
                }
                if (audiAcc.intValue() > etc.getAudi_acc().intValue()) {
                    etc.setAudi_acc(audiAcc);
                }

                }

                etcData.put(movieCd, etc);


                log.info("movieCd={}, etc={}",movieCd, etcData.get(movieCd));

                movieRepository.setEtcData(movieCd, etcData.get(movieCd));
            }

        }
    }


}
