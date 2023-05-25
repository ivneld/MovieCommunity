package Movie.MovieCommunity.web.apiDto.movie.entityDto;

import Movie.MovieCommunity.JPADomain.CreditCategory;
import lombok.Builder;
import lombok.Data;

@Data
public class CreditDto {
    private Long id;
    private String name;
    private String image;
    private CreditCategory creditCategory;

    @Builder
    public CreditDto(Long id, String name, String image, CreditCategory creditCategory) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.creditCategory = creditCategory;
    }
}
