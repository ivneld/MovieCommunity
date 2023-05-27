package Movie.MovieCommunity.JPADomain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MovieWithGenreCountDto {
    private Long genreId;
    private String genreNm;
    private Integer count;

    public MovieWithGenreCountDto(String genreNm, Integer count) {
        this.genreNm = genreNm;
        this.count = count;
    }
}
