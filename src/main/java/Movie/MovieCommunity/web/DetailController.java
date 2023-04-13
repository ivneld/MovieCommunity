package Movie.MovieCommunity.web;



import Movie.MovieCommunity.domain.ranking.Detail;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.*;

@Slf4j
@RestController
@RequestMapping
public class DetailController {

    // 상수 설정
    //   - 요청(Request) 요청 변수
    private final String REQUEST_URL = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json";
    private final String AUTH_KEY = "1d52608621856574e9228c92b7dfa738";


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

    // 영화코드 넘겨 정보 요청
    @GetMapping("/detail/{movieCd}")
    public JSONArray requestAPI(Model model, @PathVariable String movieCd) {
        // 변수설정
        //   - 하루전 닐찌
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        JSONArray array= new JSONArray();
        List<Detail> details= new ArrayList<>();
        Map<String, String> paramMap = new HashMap<String, String>();

        paramMap = paramMapSet(paramMap,movieCd);

        JSONArray total = extracted(model, details, paramMap);

        return total;
    }

    private Map<String, String> paramMapSet(Map<String, String> paramMap, String movieCd ) {
        paramMap.put("key"          , AUTH_KEY);                        // 발급받은 인증키
        paramMap.put("movieCd"     , movieCd);  // 조회하고자 하는 날짜
        return paramMap;
    }


    private JSONArray extracted(Model model, List<Detail> detail, Map<String, String> paramMap) {
        JSONObject responseBody = new JSONObject();
        JSONArray result = new JSONArray();
        try {
            // Request URL 연결 객체 생성
            URL requestURL = new URL(REQUEST_URL + "?" + makeQueryString(paramMap));
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


            try {
                responseBody = (JSONObject) json.parse(response.toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            responseBody= (JSONObject) responseBody.get("movieInfoResult");
            responseBody= (JSONObject) responseBody.get("movieInfo");
            result.add(responseBody.get("directors"));
            result.add(responseBody.get("nations"));
            result.add(responseBody.get("movieNm"));
            result.add(responseBody.get("actors"));
            result.add(responseBody.get("typeNm"));
            result.add(responseBody.get("genres"));
            result.add(responseBody.get("openDt"));
            result.add(responseBody.get("showTm"));
            result.add(responseBody.get("audits"));




            return result;

    } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}