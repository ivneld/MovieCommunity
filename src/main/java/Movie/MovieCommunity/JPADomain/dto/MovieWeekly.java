package Movie.MovieCommunity.JPADomain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class MovieWeekly{
    private String movieCd;
    private String movieNm;
//    private Integer showTm;
//    private Integer openDt;


    public MovieWeekly(String movieCd, String movieNm, Long salesAcc) {
        this.movieCd = movieCd;
        this.movieNm = movieNm;
        this.salesAcc = salesAcc;
    }

    //    private String prdtStatNm;
//    private String typeNm;
//    private String nationNm;
//    private String directorNm;
//    private String auditNo;//	문자열	심의번호를 출력합니다.
//    private String watchGradeNm;//	문자열	관람등급 명칭을 출력합니다.
//
//
//    private String boxofficeType;
//    private String showRange;
//    private String yearWeekTime;
//    private Integer rnum;
//    private Integer ranking;
//    private Integer rankInten;
//    private String rankOldAndNew;
    private Long salesAcc;
//    private Long audiAcc;
}
