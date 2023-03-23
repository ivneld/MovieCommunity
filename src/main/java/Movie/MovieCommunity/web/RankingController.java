package Movie.MovieCommunity.web;



import Movie.MovieCommunity.domain.ranking.daily.DailyBoxOffice;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Controller
@RequestMapping
public class RankingController {

    // 상수 설정
    //   - 요청(Request) 요청 변수
    private final String REQUEST_URL = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json";
    private final String AUTH_KEY = "1d52608621856574e9228c92b7dfa738";

    //   - 일자 포맷
    private final SimpleDateFormat DATE_FMT = new SimpleDateFormat("yyyyMMdd");

    // Map -> QueryString
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

    // 전체 영화 API요청
    @GetMapping
    public String requestAPI(Model model) {
        // 변수설정
        //   - 하루전 닐찌
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        List<DailyBoxOffice> dailyBoxOffices= new ArrayList<>();

        cal.add(Calendar.DATE, -1);
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap = paramMapSet(cal, paramMap,"10");

        model.addAttribute("dailyBoxOffices",extracted(model, dailyBoxOffices, paramMap));

        return  "/basic/movieRanking";
    }


    // 외국 영화 API요청
    @GetMapping("/basic/foreignMovieRanking")
    public String requestAPIForeign(Model model) {
        // 변수설정
        //   - 하루전 닐찌
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        List<DailyBoxOffice> ForeignDailyBoxOffices= new ArrayList<>();
        cal.add(Calendar.DATE, -1);


        Map<String, String> foreignParamMap = new HashMap<String, String>();

        foreignParamMap = paramMapSet(cal, foreignParamMap,"10");
        foreignParamMap.put("repNationCd" , "F");
        model.addAttribute("foreignDailyBoxOffices",extracted(model, ForeignDailyBoxOffices, foreignParamMap));

        return "/basic/foreignMovieRanking";
    }

    // API요청
    @GetMapping("basic/koreaMovieRanking")
    public String requestAPIKorea(Model model) {
        // 변수설정
        //   - 하루전 닐찌
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        List<DailyBoxOffice> KoreaDailyBoxOffices= new ArrayList<>();
        cal.add(Calendar.DATE, -1);


        Map<String, String> koreaParamMap = new HashMap<String, String>();
        koreaParamMap = paramMapSet(cal, koreaParamMap,"10");
        koreaParamMap.put("repNationCd" , "K");                             // K:한국영화, F:외국영화, Default:전체
        model.addAttribute("koreaDailyBoxOffices",extracted(model, KoreaDailyBoxOffices, koreaParamMap));

        return "basic/koreaMovieRanking";
    }

    private Map<String, String> paramMapSet(Calendar cal, Map<String, String> paramMap, String itemPerPage ) {
        paramMap.put("key"          , AUTH_KEY);                        // 발급받은 인증키
        paramMap.put("targetDt"     , DATE_FMT.format(cal.getTime()));  // 조회하고자 하는 날짜
        paramMap.put("itemPerPage"  , itemPerPage);                            // 결과 ROW 의 개수( 최대 10개 )

        return paramMap;
    }


    private  List<DailyBoxOffice> extracted(Model model, List<DailyBoxOffice> dailyBoxOffices, Map<String, String> paramMap) {
        try {
            // Request URL 연결 객체 생성
            URL requestURL = new URL(REQUEST_URL+"?"+makeQueryString(paramMap));
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

            // 데이터 추출
            JSONObject boxOfficeResult = (JSONObject) responseBody.get("boxOfficeResult");

            // 박스오피스 주제 출력
            String boxofficeType = (String) boxOfficeResult.get("boxofficeType");
            model.addAttribute("boxofficeType",(String) boxOfficeResult.get("boxofficeType"));
            System.out.println(boxofficeType);

            // 박스오피스 목록 출력
            JSONArray dailyBoxOfficeList = (JSONArray) boxOfficeResult.get("dailyBoxOfficeList");
            Iterator<Object> iter = dailyBoxOfficeList.iterator();
            while(iter.hasNext()) {
                JSONObject boxOffice = (JSONObject) iter.next();

                dailyBoxOffices.add(new DailyBoxOffice(String.valueOf(boxOffice.get("movieNm")), Integer.parseInt(String.valueOf( boxOffice.get("rank"))), Integer.parseInt(String.valueOf(boxOffice.get("rankInten"))),  Integer.parseInt(String.valueOf(boxOffice.get("rnum")))));

//                model.addAttribute("rankInten",boxOffice.get("rankInten"));
//                System.out.printf("  %s - %s\n", boxOffice.get("rnum"), boxOffice.get("movieNm"));
//                System.out.printf("  %s \n", boxOffice.get("openDt"));
//                System.out.printf("  %s \n", boxOffice.get("rankInten"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dailyBoxOffices;
    }
}