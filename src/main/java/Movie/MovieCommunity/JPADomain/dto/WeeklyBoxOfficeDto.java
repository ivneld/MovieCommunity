package Movie.MovieCommunity.JPADomain.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class WeeklyBoxOfficeDto {

    private String boxofficeType;
    private String showRange;
    private String yearWeekTime;
    private Integer rnum;
    private Integer ranking;
    private Integer rankInten;
    private String rankOldAndNew;
    private String movieCd;
    //movieNm	문자열	영화명(국문)을 출력합니다.
    private LocalDate openDt;

    //salesAmt	문자열	해당일의 매출액을 출력합니다.
    //salesShare	문자열	해당일자 상영작의 매출총액 대비 해당 영화의 매출비율을 출력합니다.
    // salesInten	문자열	전일 대비 매출액 증감분을 출력합니다.
    // salesChange	문자열	전일 대비 매출액 증감 비율을 출력합니다.
    private Long salesAcc;
    // private Long audiCnt;
    //  audiInten	문자열	전일 대비 관객수 증감분을 출력합니다.
    //  audiChange	문자열	전일 대비 관객수 증감 비율을 출력합니다.
    private Long audiAcc;
/*    scrnCnt	문자열	해당일자에 상영한 스크린수를 출력합니다.
    showCnt	문자열	해당일자에 상영된 횟수를 출력합니다.*/
}
