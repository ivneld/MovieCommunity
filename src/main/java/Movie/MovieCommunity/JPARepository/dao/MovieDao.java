package Movie.MovieCommunity.JPARepository.dao;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class MovieDao {
    private String movieCd;
    private String movieNm;
    //    movieNmEn	문자열	영화명(영문)을 출력합니다.
//    movieNmOg	문자열	영화명(원문)을 출력합니다.
//    prdtYear	문자열	제작연도를 출력합니다.
    private Integer showTm;
    private Integer openDt;
    private String prdtStatNm;
    private String typeNm;
    //     nations	문자열	제작국가를 나타냅니다.
    private String nationNm;
    // private Long genreId;
//    private String directors;
    private String directorNm;
    @QueryProjection
    public MovieDao(String movieCd, String movieNm, Integer showTm, Integer openDt, String prdtStatNm, String typeNm, String nationNm, String directorNm, String watchGradeNm, Integer topScore) {
        this.movieCd = movieCd;
        this.movieNm = movieNm;
        this.showTm = showTm;
        this.openDt = openDt;
        this.prdtStatNm = prdtStatNm;
        this.typeNm = typeNm;
        this.nationNm = nationNm;
        this.directorNm = directorNm;
        this.watchGradeNm = watchGradeNm;
        this.topScore = topScore;
    }




    //    peopleNmEn	문자열	감독명(영문)을 출력합니다.
//    actors	문자열	배우를 나타냅니다.
    //private String actorNm;
//    peopleNmEn	문자열	배우명(영문)을 출력합니다.
    //private String cast;//	문자열	배역명을 출력합니다.
//    castEn	문자열	배역명(영문)을 출력합니다.
//    showTypes	문자열	상영형태 구분을 나타냅니다.
//    showTypeGroupNm	문자열	상영형태 구분을 출력합니다.
//    showTypeNm	문자열	상영형태명을 출력합니다.
//    private String audits;// 	문자열	심의정보를 나타냅니다.
    private String watchGradeNm;//	문자열	관람등급 명칭을 출력합니다.
    private Integer topScore;
}
