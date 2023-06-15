package Movie.MovieCommunity.naverApi;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
@Service
@RequiredArgsConstructor
public class ApiExamTranslateNmt {
    private final ApiExamDetectLangs apiExamDetectLangs;
    @Value("${naver.client}")
    private String clientId;
    @Value("${naver.clientSecret}")
    private String clientSecret;



    public String translation(String query) throws ParseException {
        String source = apiExamDetectLangs.detect(query);
        System.out.println("source = " + source);
//        String clientId = "YOUR_CLIENT_ID";//애플리케이션 클라이언트 아이디값";
//        String clientSecret = "YOUR_CLIENT_SECRET";//애플리케이션 클라이언트 시크릿값";
        String text;
        String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
        try {
            text = URLEncoder.encode(query, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("인코딩 실패", e);
        }

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);

        String response = post(apiURL, requestHeaders,source,"ko", text);

        System.out.println(response);

        JSONParser jsonParser = new JSONParser();
        Object parse = jsonParser.parse(response);
        JSONObject ps = (JSONObject) parse;

        JSONObject message = (JSONObject)ps.get("message");
        if (message != null) {
            JSONObject result = (JSONObject) message.get("result");
            String translatedText = (String) result.get("translatedText");
            return translatedText;
        }else{
            throw new RuntimeException("번역 불가능");
        }
    }

    private static String post(String apiUrl, Map<String, String> requestHeaders,String source, String target, String text){
        HttpURLConnection con = connect(apiUrl);
        String postParams = "source="+source+"&target="+target+"&text=" + text; //원본언어: 한국어 (ko) -> 목적언어: 영어 (en)
        try {
            con.setRequestMethod("POST");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            con.setDoOutput(true);
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postParams.getBytes());
                wr.flush();
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 응답
                return readBody(con.getInputStream());
            } else {  // 에러 응답
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }
}
