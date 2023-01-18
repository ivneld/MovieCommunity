package Movie.MovieCommunity.domain;

import lombok.Data;

@Data
public class Company {
    private Long id;
    private String companyCd;//	문자열	참여 영화사 코드를 출력합니다.
    private String companyNm;//	문자열	참여 영화사명을 출력합니다.
    private String companyNmEn;//	문자열	참여 영화사명(영문)을 출력합니다.
    private String companyPartNm;//	문자열	참여 영화사 분야명을 출력합니다.
}
