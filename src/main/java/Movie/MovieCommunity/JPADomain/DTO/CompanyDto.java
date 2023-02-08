package Movie.MovieCommunity.JPADomain.DTO;

import lombok.Data;

@Data
public class CompanyDto {
    private String companyCd;//	문자열	참여 영화사 코드를 출력합니다.
    private String companyNm;//	문자열	참여 영화사명을 출력합니다.
    private String companyNmEn;//	문자열	참여 영화사명(영문)을 출력합니다.
    private String companyPartNm;//	문자열	참여 영화사 분야명을 출력합니다.
}
