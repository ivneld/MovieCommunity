package Movie.MovieCommunity.JPADomain;

import Movie.MovieCommunity.JPADomain.dto.WeeklyBoxOfficeDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import java.time.LocalDate;

/*
boxofficeType	문자열	박스오피스 종류를 출력합니다.
showRange	문자열	대상 상영기간을 출력합니다.
yearWeekTime	문자열	조회일자에 해당하는 연도와 주차를 출력합니다.(YYYYIW)
rnum	문자열	순번을 출력합니다.
rank	문자열	해당일자의 박스오피스 순위를 출력합니다.
rankInten	문자열	전일대비 순위의 증감분을 출력합니다.
rankOldAndNew	문자열	랭킹에 신규진입여부를 출력합니다.
“OLD” : 기존 , “NEW” : 신규
movieCd	문자열	영화의 대표코드를 출력합니다.
movieNm	문자열	영화명(국문)을 출력합니다.
openDt	문자열	영화의 개봉일을 출력합니다.
salesAmt	문자열	해당일의 매출액을 출력합니다.
salesShare	문자열	해당일자 상영작의 매출총액 대비 해당 영화의 매출비율을 출력합니다.
salesInten	문자열	전일 대비 매출액 증감분을 출력합니다.
salesChange	문자열	전일 대비 매출액 증감 비율을 출력합니다.
salesAcc	문자열	누적매출액을 출력합니다.
audiCnt	문자열	해당일의 관객수를 출력합니다.
audiInten	문자열	전일 대비 관객수 증감분을 출력합니다.
audiChange	문자열	전일 대비 관객수 증감 비율을 출력합니다.
audiAcc	문자열	누적관객수를 출력합니다.
scrnCnt	문자열	해당일자에 상영한 스크린수를 출력합니다.
showCnt	문자열	해당일자에 상영된 횟수를 출력합니다.
 */
@Entity(name ="weeklyboxoffice")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="weeklyboxoffice")
@ToString
public class JpaWeeklyBoxOffice {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "weekly_box_office_id")
    private Long id;
    private String boxofficeType;
    private String showRange;
    private String yearWeekTime;
//    @NumberFormat
    private Integer rnum;
//    @NumberFormat
    private Integer ranking;
//    @NumberFormat
    private Integer rankInten;
    private String rankOldAndNew;


    private String movieCd;
    //movieNm	문자열	영화명(국문)을 출력합니다.
//    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate openDt;

    //salesAmt	문자열	해당일의 매출액을 출력합니다.
    //salesShare	문자열	해당일자 상영작의 매출총액 대비 해당 영화의 매출비율을 출력합니다.
   // salesInten	문자열	전일 대비 매출액 증감분을 출력합니다.
   // salesChange	문자열	전일 대비 매출액 증감 비율을 출력합니다.
//    @NumberFormat(pattern = "###,###")
    private Long salesAcc;
   // private Long audiCnt;
  //  audiInten	문자열	전일 대비 관객수 증감분을 출력합니다.
  //  audiChange	문자열	전일 대비 관객수 증감 비율을 출력합니다.
//    @NumberFormat(pattern = "###,###")
    private Long audiAcc;
/*    scrnCnt	문자열	해당일자에 상영한 스크린수를 출력합니다.
    showCnt	문자열	해당일자에 상영된 횟수를 출력합니다.*/


    public JpaWeeklyBoxOffice(WeeklyBoxOfficeDto weeklyBoxOfficeDto) {
        this.boxofficeType = weeklyBoxOfficeDto.getBoxofficeType();
        this.showRange = weeklyBoxOfficeDto.getShowRange();
        this.yearWeekTime = weeklyBoxOfficeDto.getYearWeekTime();
        this.rnum = weeklyBoxOfficeDto.getRnum();
        this.ranking = weeklyBoxOfficeDto.getRanking();
        this.rankInten = weeklyBoxOfficeDto.getRankInten();
        this.rankOldAndNew = weeklyBoxOfficeDto.getRankOldAndNew();
        this.movieCd = weeklyBoxOfficeDto.getMovieCd();
        this.openDt = weeklyBoxOfficeDto.getOpenDt();
        this.salesAcc = weeklyBoxOfficeDto.getSalesAcc();
        this.audiAcc = weeklyBoxOfficeDto.getAudiAcc();
    }
}
