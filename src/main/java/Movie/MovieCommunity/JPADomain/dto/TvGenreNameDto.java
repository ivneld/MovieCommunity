package Movie.MovieCommunity.JPADomain.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class TvGenreNameDto {

    private String genreName;

    public TvGenreNameDto(String genreName) {
        this.genreName = genreName;
    }
}
