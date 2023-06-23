package Movie.MovieCommunity.dataCollection;


import Movie.MovieCommunity.JPADomain.dto.TvDto;
import Movie.MovieCommunity.JPADomain.dto.TvGenreNameDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;


//https://api.themoviedb.org/3/discover/tv?api_key=e2d17df18876333027066f263663d8db&with_watch_providers=8
@Slf4j
@RestController
@RequestMapping
@Service
@RequiredArgsConstructor
public class TvDataService {

    private final String url="https://api.themoviedb.org/3/discover/tv";
    private final String tmdbKey="e2d17df18876333027066f263663d8db";

    private final String posterUrl="https://image.tmdb.org/t/p/w500";


    @GetMapping("/tv")
    public JSONArray requestAPI(Model model) {

        JSONArray array= new JSONArray();
        List<TvDto> NetflixTvInformation= new ArrayList<>();
        List<TvDto> DisneyPlusTvInformation= new ArrayList<>();
        List<TvDto> WavveTvInformation= new ArrayList<>();
        List<TvDto> WatchaTvInformation= new ArrayList<>();
        List<TvDto> AppleTvInformation= new ArrayList<>();

        LinkedHashMap<String, String> NetflixParamMap = new LinkedHashMap<String, String>();
        LinkedHashMap<String, String> DisneyPlusParamMap = new LinkedHashMap<String, String>();
        LinkedHashMap<String, String> WavveParamMap = new LinkedHashMap<String, String>();
        LinkedHashMap<String, String> WatchaParamMap = new LinkedHashMap<String, String>();
        LinkedHashMap<String, String> AppleParamMap = new LinkedHashMap<String, String>();

        NetflixParamMap =paramMapSet(NetflixParamMap,"8");
        DisneyPlusParamMap =paramMapSet(DisneyPlusParamMap,"337");
        WavveParamMap =paramMapSet(WavveParamMap,"356");
        WatchaParamMap =paramMapSet(WatchaParamMap,"97");
        AppleParamMap =paramMapSet(AppleParamMap,"350");

        List<TvDto> Netflix = extracted(model, NetflixTvInformation, NetflixParamMap, 8);
        List<TvDto> DisneyPlus = extracted(model, DisneyPlusTvInformation, DisneyPlusParamMap,337);
        List<TvDto> Wavve = extracted(model, WavveTvInformation, WavveParamMap,356);
        List<TvDto> Watcha = extracted(model, WatchaTvInformation, WatchaParamMap, 97);
        List<TvDto> Apple = extracted(model, AppleTvInformation, AppleParamMap,350);

        array.add(0, Netflix);
        array.add(1, DisneyPlus);
        array.add(2, Wavve);
        array.add(3, Watcha);
        array.add(4, Apple);

        return array;
    }

    @GetMapping("/tv/netflix")
    public JSONArray requestNeflixAPI(Model model) {
        JSONArray array= new JSONArray();
        List<TvDto> NetflixTvInformation= new ArrayList<>();
        LinkedHashMap<String, String> NetflixParamMap = new LinkedHashMap<String, String>();
        NetflixParamMap =paramMapSet(NetflixParamMap,"8");
        List<TvDto> Netflix = extracted(model, NetflixTvInformation, NetflixParamMap, 8);
        array.add(Netflix);
        return array;
    }

    @GetMapping("/tv/disneyPlus")
    public JSONArray requestdisneyPlusAPI(Model model) {
        JSONArray array= new JSONArray();
        List<TvDto> DisneyPlusTvInformation= new ArrayList<>();
        LinkedHashMap<String, String> DisneyPlusParamMap = new LinkedHashMap<String, String>();
        DisneyPlusParamMap =paramMapSet(DisneyPlusParamMap,"337");
        List<TvDto> DisneyPlus = extracted(model, DisneyPlusTvInformation, DisneyPlusParamMap,337);
        array.add(DisneyPlus);
        return array;
    }

    @GetMapping("/tv/waave")
    public JSONArray requestWaaveAPI(Model model) {
        JSONArray array= new JSONArray();
        List<TvDto> WavveTvInformation= new ArrayList<>();
        LinkedHashMap<String, String> WavveParamMap = new LinkedHashMap<String, String>();
        WavveParamMap =paramMapSet(WavveParamMap,"356");
        List<TvDto> Wavve = extracted(model, WavveTvInformation, WavveParamMap,356);
        array.add(Wavve);
        return array;
    }

    @GetMapping("/tv/watcha")
    public JSONArray requestWatchaAPI(Model model) {
        JSONArray array= new JSONArray();
        List<TvDto> WatchaTvInformation= new ArrayList<>();
        LinkedHashMap<String, String> WatchaParamMap = new LinkedHashMap<String, String>();
        WatchaParamMap =paramMapSet(WatchaParamMap,"97");
        List<TvDto> Watcha = extracted(model, WatchaTvInformation, WatchaParamMap, 97);
        array.add( Watcha);
        return array;
    }

    @GetMapping("/tv/appleTv")
    public JSONArray requestAppleAPIR(Model model){
        JSONArray array= new JSONArray();
        List<TvDto> AppleTvInformation= new ArrayList<>();
        LinkedHashMap<String, String> AppleParamMap = new LinkedHashMap<String, String>();
        AppleParamMap =paramMapSet(AppleParamMap,"350");
        List<TvDto> Apple = extracted(model, AppleTvInformation, AppleParamMap,350);
        array.add(Apple);
        return array;
    }

    public String makeQueryString(Map<String, String> paramMap) {
        final StringBuilder sb = new StringBuilder();

        paramMap.entrySet().forEach(( entry )->{
            if( sb.length() > 0 ) {
                sb.append('&');
            }
            sb.append(entry.getKey()).append('=').append(entry.getValue());
        });

        return sb.toString();
    }


    private LinkedHashMap<String, String> paramMapSet(LinkedHashMap<String, String> paramMap, String watch_provider ) {
        paramMap.put("api_key", tmdbKey);                        // 발급받은 인증키
        paramMap.put("language", "ko-KR");                       // 조회하고자 하는 날짜
        paramMap.put("page","1");
        paramMap.put("sort_by","popularity.desc");
        paramMap.put("timezone","Korea");
        paramMap.put("with_watch_providers",watch_provider);
        paramMap.put("watch_region","KR");
        paramMap.put("with_watch_monetization_types","flatrate");
        return paramMap;
    }


    /**
     * 실시간 GET 방식으로 박스오피스 목록 출력
     * */
    private List<TvDto> extracted(Model model, List<TvDto> tv, Map<String, String> paramMap, Integer watch_provider ) {
        try {
            // Request URL 연결 객체 생성
            URL requestURL = new URL(url + "?" + makeQueryString(paramMap));
            log.info("requesturl={}", requestURL);
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
            JSONArray tvList = (JSONArray) responseBody.get("results");

            Iterator<JSONObject> iter = tvList.iterator();
            while(iter.hasNext()) {
                JSONObject tvInfo = iter.next();
                List<TvGenreNameDto> tvGenreNameDtos= new ArrayList<>();
                List<Long> ids = (List<Long>) tvInfo.get("genre_ids");
                for (Long id : ids) {
                    TvGenreNameDto tvGenreNameDto=new TvGenreNameDto(TvGenreNameTranslate(id));
                    tvGenreNameDtos.add(tvGenreNameDto);
                }

                tv.add(new TvDto((Long) tvInfo.get("id"), (String) tvInfo.get("name"), tvInfo.get("origin_country").toString(), (String) tvInfo.get("original_language"), (String) tvInfo.get("overview"), Double.parseDouble(String.valueOf(tvInfo.get("popularity"))), posterUrl+(String) tvInfo.get("poster_path"),posterUrl+(String) tvInfo.get("backdrop_path"), (String) tvInfo.get("first_air_date"),   watch_provider, tvGenreNameDtos, Double.parseDouble(String.valueOf(tvInfo.get("vote_average"))), Integer.parseInt(String.valueOf(tvInfo.get("vote_count"))) ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tv;
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
}