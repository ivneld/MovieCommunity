package Movie.MovieCommunity.domain;

import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

/*
movieCd	문자열	영화코드를 출력합니다.
movieNm	문자열	영화명(국문)을 출력합니다.
movieNmEn	문자열	영화명(영문)을 출력합니다.
movieNmOg	문자열	영화명(원문)을 출력합니다.
prdtYear	문자열	제작연도를 출력합니다.
showTm	문자열	상영시간을 출력합니다.
openDt	문자열	개봉연도를 출력합니다.
prdtStatNm	문자열	제작상태명을 출력합니다.
typeNm	문자열	영화유형명을 출력합니다.
nations	문자열	제작국가를 나타냅니다.
nationNm	문자열	제작국가명을 출력합니다.
genreNm	문자열	장르명을 출력합니다.
directors	문자열	감독을 나타냅니다.
peopleNm	문자열	감독명을 출력합니다.
peopleNmEn	문자열	감독명(영문)을 출력합니다.
actors	문자열	배우를 나타냅니다.
peopleNm	문자열	배우명을 출력합니다.
peopleNmEn	문자열	배우명(영문)을 출력합니다.
cast	문자열	배역명을 출력합니다.
castEn	문자열	배역명(영문)을 출력합니다.
showTypes	문자열	상영형태 구분을 나타냅니다.
showTypeGroupNm	문자열	상영형태 구분을 출력합니다.
showTypeNm	문자열	상영형태명을 출력합니다.
audits	문자열	심의정보를 나타냅니다.
auditNo	문자열	심의번호를 출력합니다.
watchGradeNm	문자열	관람등급 명칭을 출력합니다.
companys	문자열	참여 영화사를 나타냅니다.
companyCd	문자열	참여 영화사 코드를 출력합니다.
companyNm	문자열	참여 영화사명을 출력합니다.
companyNmEn	문자열	참여 영화사명(영문)을 출력합니다.
companyPartNm	문자열	참여 영화사 분야명을 출력합니다.
staffs	문자열	스텝을 나타냅니다.
peopleNm	문자열	스텝명을 출력합니다.
peopleNmEn	문자열	스텝명(영문)을 출력합니다.
staffRoleNm	문자열	스텝역할명을 출력합니다.
 */
@Data
public class Movie {
    private Long id;
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
    private String auditNo;//	문자열	심의번호를 출력합니다.
    private String watchGradeNm;//	문자열	관람등급 명칭을 출력합니다.
//    companys	문자열	참여 영화사를 나타냅니다.
    //private Long companyId;
//    private String companyCd;//	문자열	참여 영화사 코드를 출력합니다.
//    private String companyNm;//	문자열	참여 영화사명을 출력합니다.
//    private String companyNmEn;//	문자열	참여 영화사명(영문)을 출력합니다.
//    private String companyPartNm;//	문자열	참여 영화사 분야명을 출력합니다.
    private int topScore;
//    staffs	문자열	스텝을 나타냅니다.
//    peopleNm	문자열	스텝명을 출력합니다.
//    peopleNmEn	문자열	스텝명(영문)을 출력합니다.
//    staffRoleNm	문자열	스텝역할명을 출력합니다.
    @NumberFormat(pattern = "###,###")
    private Long salesAcc;
        /*    private Long audiCnt;
            audiInten	문자열	전일 대비 관객수 증감분을 출력합니다.
            audiChange	문자열	전일 대비 관객수 증감 비율을 출력합니다.*/
    @NumberFormat(pattern = "###,###")
    private Long audiAcc;
}
