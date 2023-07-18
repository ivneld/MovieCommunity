package Movie.MovieCommunity;

import Movie.MovieCommunity.JPADomain.*;
import Movie.MovieCommunity.JPADomain.dto.TvGenreDto;
import Movie.MovieCommunity.JPARepository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;


@Component
@Slf4j           //최신순으로 정렬후 데이터가 있으면 갱신 멈추기
@RequiredArgsConstructor
public class InitOttDB {

    private final InitService initService;


    public void init() throws Exception {

        initService.providerInit();
        log.info("provider_init");
        initService.genreInit();
        log.info("genre_init");
        initService.tvInit();
        log.info("tv_init");

    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;
        private final String genreUrl="https://api.themoviedb.org/3/genre/tv/list";
        private final String tmdbKey="e2d17df18876333027066f263663d8db";

        private final String logoUrl="https://image.tmdb.org/t/p/w500";

        private final String tvUrl="https://api.themoviedb.org/3/discover/tv";
        private final String posterUrl="https://image.tmdb.org/t/p/w500";

        private final TvGenreRepository tvGenreRepository;
        private final TvRepository tvRepository;
        private final TvProviderRepository tvProviderRepository;
        private final TvWithProviderRepository tvWithProviderRepository;
        private final TvWithGenreRepository tvWithGenreRepository;
        private ThreadLocal<TvGenreDto> threadTv = new ThreadLocal<>();



        public void genreInit(){
            if(tvGenreRepository.findByGenreTmId(16L).isPresent()){
                log.info("Genre already exists ={}",tvGenreRepository.findByGenreTmId(16L).isPresent());
                return;
            }

            //https://api.themoviedb.org/3/genre/tv/list?api_key=e2d17df18876333027066f263663d8db
            System.out.println("hello");
            try {
                URL genreURL = new URL(genreUrl + "?" +"api_key="+tmdbKey+"&language=ko");
                log.info("genreURL = {}", genreURL);
                HttpURLConnection conn = (HttpURLConnection) genreURL.openConnection();

                // GET 방식으로 요청
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // 응답(Response) 구조 작성
                //   - Stream -> JSONObject
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                String readline = null;
                StringBuffer response = new StringBuffer();
                while ((readline = br.readLine()) != null) {
                    response.append(readline);
                }
                JSONParser json = new JSONParser();

                // JSON 객체로  변환
                JSONObject responseBody = new JSONObject();
                try {
                    responseBody= (JSONObject) json.parse(response.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // 박스오피스 목록 출력
                JSONArray genres = (JSONArray) responseBody.get("genres");

                Iterator<JSONObject> iter = genres.iterator();

                while(iter.hasNext()) {
                    JSONObject genre = iter.next();
                    TvGenreDto tvGenreDto =new TvGenreDto();

                    tvGenreDto.setId((Long) genre.get("id"));

                    tvGenreDto.setGenreNm((String) genre.get("name"));
                    threadTv.set(tvGenreDto);

                    TvGenre tvGenre = new TvGenre((Long) genre.get("id"),(String) genre.get("name"));
                    tvGenreRepository.save(tvGenre);
//
//                JpaTvGenre jpaTvGenre = new JpaTvGenre(tvGenreDto);
//                JpaTvGenre saved = tvGenreRepository.save(jpaTvGenre);

                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }


        public void providerInit(){
            if(tvProviderRepository.findByProviderTmId(8L).isPresent()){
                log.info("Provider already exists={}",tvProviderRepository.findByProviderTmId(8L).isPresent());
                return;
            }
            TvProvider tvProvider1 = new TvProvider(8L,"Netflix",logoUrl + "/t2yyOv40HZeVlLjYsCsPHnWLk4W.jpg");
            TvProvider tvProvider2 = new TvProvider(337L,"Disney Plus",logoUrl + "/7rwgEs15tFwyR9NPQ5vpzxTj19Q.jpg");
            TvProvider tvProvider3 = new TvProvider(356L,"wavve",logoUrl + "/2ioan5BX5L9tz4fIGU93blTeFhv.jpg");
            TvProvider tvProvider4 = new TvProvider(97L,"Watcha",logoUrl + "/vXXZx0aWQtDv2klvObNugm4dQMN.jpg");
            TvProvider tvProvider5 = new TvProvider(350L,"Apple TV Plus",logoUrl + "/6uhKBfmtzFqOcLousHwZuzcrScK.jpg");
            tvProviderRepository.save(tvProvider1);
            tvProviderRepository.save(tvProvider2);
            tvProviderRepository.save(tvProvider3);
            tvProviderRepository.save(tvProvider4);
            tvProviderRepository.save(tvProvider5);
        }


        public void tvInit(){
            JSONArray array= new JSONArray();

            LinkedHashMap<String, String> NetflixParamMap = new LinkedHashMap<String, String>();
            LinkedHashMap<String, String> DisneyPlusParamMap = new LinkedHashMap<String, String>();
            LinkedHashMap<String, String> WavveParamMap = new LinkedHashMap<String, String>();
            LinkedHashMap<String, String> WatchaParamMap = new LinkedHashMap<String, String>();
            LinkedHashMap<String, String> AppleParamMap = new LinkedHashMap<String, String>();

            NetflixParamMap =paramMapSet(NetflixParamMap,"1","8");
            DisneyPlusParamMap =paramMapSet(DisneyPlusParamMap, "1","337");
            WavveParamMap =paramMapSet(WavveParamMap,"1","356");
            WatchaParamMap =paramMapSet(WatchaParamMap,"1","97");
            AppleParamMap =paramMapSet(AppleParamMap,"1","350");

            Long NetflixPages = (Long) extracted(NetflixParamMap);
            Long DisneyPlusPages = (Long) extracted(DisneyPlusParamMap);
            Long WavvePages = (Long) extracted(WavveParamMap);
            Long WatchaPages = (Long) extracted(WatchaParamMap);
            Long ApplePages = (Long) extracted(AppleParamMap);

            saveTvContents(NetflixPages,8);
            log.info("save=1");
            saveTvContents(DisneyPlusPages,337);
            log.info("save=2");
            saveTvContents(WavvePages,356);
            log.info("save=3");
            saveTvContents(WatchaPages,97);
            log.info("save=4");
            saveTvContents(ApplePages,350);

        }

        private void saveTvContents(Long pages, Integer watch_provider) {
            LinkedHashMap<String, String> paramMap = new LinkedHashMap<String, String>();

            Long currentPage = 1L;
            while(currentPage<=pages){
                paramMap =paramMapSet(paramMap, String.valueOf(currentPage), String.valueOf(watch_provider));
                currentPage+=1;
                if(extractTv(paramMap,watch_provider)==TRUE){
                    return;
                }
            }
        }

        private LinkedHashMap<String, String> paramMapSet(LinkedHashMap<String, String> paramMap, String page, String watch_provider ) {
            paramMap.put("api_key", tmdbKey);                        // 발급받은 인증키
            paramMap.put("language", "ko-KR");
            paramMap.put("page", page);
            paramMap.put("sort_by","primary_release_date.desc");
            paramMap.put("with_watch_providers",watch_provider);
            paramMap.put("watch_region","KR");
            return paramMap;
        }

        private Object extracted( Map<String, String> paramMap) {
            try {
                // Request URL 연결 객체 생성
                URL requestURL = new URL(tvUrl + "?" + makeQueryString(paramMap));
                HttpURLConnection conn = (HttpURLConnection) requestURL.openConnection();


                // GET 방식으로 요청
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // 응답(Response) 구조 작성
                //   - Stream -> JSONObject
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                String readline = null;
                StringBuffer response = new StringBuffer();
                while ((readline = br.readLine()) != null) {
                    response.append(readline);
                }
                JSONParser json = new JSONParser();

                // JSON 객체로  변환
                JSONObject responseBody = new JSONObject();
                try {
                    responseBody= (JSONObject) json.parse(response.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // 박스오피스 목록 출력
                Object totalPages = responseBody.get("total_pages");

                return totalPages;

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private String makeQueryString(Map<String, String> paramMap) {
            final StringBuilder sb = new StringBuilder();
            paramMap.entrySet().forEach(( entry )->{
                if( sb.length() > 0 ) {
                    sb.append('&');
                }
                sb.append(entry.getKey()).append('=').append(entry.getValue());
            });
            return sb.toString();
        }

        private boolean extractTv(Map<String, String> paramMap, Integer watch_provider ) {
            try {
                // Request URL 연결 객체 생성
                URL requestURL = new URL(tvUrl + "?" + makeQueryString(paramMap));
                HttpURLConnection conn = (HttpURLConnection) requestURL.openConnection();

                // GET 방식으로 요청
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // 응답(Response) 구조 작성
                //   - Stream -> JSONObject
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                String readline = null;
                StringBuffer response = new StringBuffer();
                while ((readline = br.readLine()) != null) {
                    response.append(readline);
                }
                JSONParser json = new JSONParser();

                // JSON 객체로  변환
                JSONObject responseBody = new JSONObject();
                try {
                    responseBody = (JSONObject) json.parse(response.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // 박스오피스 목록 출력
                JSONArray tvList = (JSONArray) responseBody.get("results");


                Iterator<JSONObject> iter = tvList.iterator();

                while (iter.hasNext()) {
                    JSONObject tvInfo = iter.next();
                    Long tmId = ((Long) tvInfo.get("id"));
                    if (tvRepository.findByTmId(tmId).isPresent() && tmId != null) {
                        return TRUE;
                    } else {
                        List<Long> ids = (List<Long>) tvInfo.get("genre_ids");
                        Tv tv = new Tv(((Long) tvInfo.get("id")), (String) tvInfo.get("name"), tvInfo.get("origin_country").toString(), (String) tvInfo.get("original_language"), (String) tvInfo.get("overview"), Double.parseDouble(String.valueOf(tvInfo.get("popularity"))), posterUrl + (String) tvInfo.get("poster_path"), posterUrl + (String) tvInfo.get("backdrop_path"), (String) tvInfo.get("first_air_date"));
                        tvRepository.save(tv);
                        if (ids.isEmpty()) {
                            continue;
                        } else {

                            TvGenre tvGenre1 = tvGenreRepository.findByGenreTmId(ids.get(0)).get();
                            tvWithGenreInit(tv, tvGenre1);
                        }
                        TvProvider tvProvider1 = tvProviderRepository.findByProviderTmId(Long.valueOf(watch_provider)).get();
                        log.info("jpaTv={}", tv);
                        tvWIthProviderInit(tv, tvProvider1);
                    }
                }
            } catch(IOException e){
                e.printStackTrace();
            }

            return FALSE;
        }

        public String TvGenreNameTranslate(Long id){
            if(id == 10759){
                return "Action & Adventure";
            }else if(id == 16){
                return "애니메이션";
            }else if(id == 35){
                return "코미디";
            }else if(id == 80){
                return "범죄";
            }else if(id == 99){
                return "다큐멘터리";
            }else if(id == 18){
                return "드라마";
            }else if(id == 10751){
                return "가족";
            }else if(id == 10762){
                return "Kids";
            }else if(id == 9648){
                return "미스터리";
            }else if(id == 10763){
                return "News";
            }else if(id == 10765){
                return "Sci-Fi & Fantasy";
            }else if(id == 10766){
                return "Soap";
            }else if(id == 10767){
                return "Talk";
            }else if(id == 10768){
                return "War & Politics";
            }else{
                return "서부";
            }
        }


        public void tvWithGenreInit(Tv tv, TvGenre tvGenre) {
            TvWithGenre tvWithGenre= new TvWithGenre(tv,tvGenre);
            tvWithGenreRepository.save(tvWithGenre);
            log.info("tvwithgenre save");
        }

        public void tvWIthProviderInit(Tv tv, TvProvider tvProvider) {
            TvWithProvider tvWithProvider= new TvWithProvider(tv, tvProvider);
            tvWithProviderRepository.save(tvWithProvider);
        }
    }


}

