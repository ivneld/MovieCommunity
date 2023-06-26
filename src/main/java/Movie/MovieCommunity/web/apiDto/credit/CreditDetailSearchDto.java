package Movie.MovieCommunity.web.apiDto.credit;

import Movie.MovieCommunity.JPADomain.CreditCategory;
import lombok.Builder;
import lombok.Data;

@Data
public class CreditDetailSearchDto {
    private Long id;
    private String actorNm;
    private CreditCategory creditCategory;
    private String profileUrl;
    @Builder
    public CreditDetailSearchDto(Long id, String actorNm, CreditCategory creditCategory, String profileUrl) {
        this.id = id;
        this.actorNm = actorNm;
        this.creditCategory = creditCategory;
        this.profileUrl = profileUrl;
    }
}
