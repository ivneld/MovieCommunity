package Movie.MovieCommunity.JPADomain;

import Movie.MovieCommunity.JPADomain.dto.CompanyDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Table(name = "company")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JpaCompany {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Long id;

    @Column(nullable = false, columnDefinition = "VARCHAR(45) DEFAULT 'default_value'")
    private String companyCd;//	문자열	참여 영화사 코드를 출력합니다.

    @Column(nullable = false, columnDefinition = "VARCHAR(45) DEFAULT 'default_value'")
    private String companyNm;//	문자열	참여 영화사명을 출력합니다.
    @Column(nullable = false, columnDefinition = "VARCHAR(45) DEFAULT 'default_value'")
    private String companyNmEn;//	문자열	참여 영화사명(영문)을 출력합니다.
    @Column(nullable = false, columnDefinition = "VARCHAR(45) DEFAULT 'default_value'")
    private String companyPartNm;//	문자열	참여 영화사 분야명을 출력합니다.
//    public void updateData(CompanyDto companyDto){
//        this.companyCd = companyDto.getCompanyCd();
//        this.companyNm = companyDto.getCompanyNm();
//        this.companyNmEn = companyDto.getCompanyNmEn();
//        this.companyPartNm = companyDto.getCompanyPartNm();
//    }
public JpaCompany(CompanyDto companyDto) {
    this.companyCd = companyDto.getCompanyCd();
    this.companyNm = companyDto.getCompanyNm();
    this.companyNmEn = companyDto.getCompanyNmEn();
    this.companyPartNm = companyDto.getCompanyPartNm();
}
}
