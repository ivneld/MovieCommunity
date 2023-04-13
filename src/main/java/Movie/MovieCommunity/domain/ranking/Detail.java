package Movie.MovieCommunity.domain.ranking;

import lombok.Data;
import org.json.simple.JSONObject;

@Data
public class Detail {
    private String movieCd;    //영화코드
    private String movieNm;    // 영화명
    private String prdtYear;   // 제작연도
    private String showTm;   // 상영시간
    private String nationNm;   // 제작국가명
    private String genreNm;   // 장르명
    private String peopleNm;   // 감독명
    private String actors;   // 배우
    private String audits;   // 심의정보

    public Detail(){

    }

    public Detail(String movieCd, String movieNm, String prdtYear, String showTm, String nationNm, String genreNm, String peopleNm, String actors, String audits) {
        this.movieCd = movieCd;
        this.movieNm = movieNm;
        this.prdtYear = prdtYear;
        this.showTm = showTm;
        this.nationNm = nationNm;
        this.genreNm = genreNm;
        this.peopleNm = peopleNm;
        this.actors = actors;
        this.audits = audits;
    }
}
