package Movie.MovieCommunity.JPADomain;

import Movie.MovieCommunity.JPADomain.DTO.CompanyDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "company")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JpaCompany {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Long id;
    private String companyCd;//	문자열	참여 영화사 코드를 출력합니다.

    public JpaCompany(CompanyDto companyDto) {
        this.companyCd = companyDto.getCompanyCd();
        this.companyNm = companyDto.getCompanyNm();
        this.companyNmEn = companyDto.getCompanyNmEn();
        this.companyPartNm = companyDto.getCompanyPartNm();
    }

    private String companyNm;//	문자열	참여 영화사명을 출력합니다.
    private String companyNmEn;//	문자열	참여 영화사명(영문)을 출력합니다.
    private String companyPartNm;//	문자열	참여 영화사 분야명을 출력합니다.
//    public void updateData(CompanyDto companyDto){
//        this.companyCd = companyDto.getCompanyCd();
//        this.companyNm = companyDto.getCompanyNm();
//        this.companyNmEn = companyDto.getCompanyNmEn();
//        this.companyPartNm = companyDto.getCompanyPartNm();
//    }
}
