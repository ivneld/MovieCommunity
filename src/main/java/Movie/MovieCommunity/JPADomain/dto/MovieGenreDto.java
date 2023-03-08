package Movie.MovieCommunity.JPADomain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MovieGenreDto {

    private Long movieId;
    private String movieNm;
    private Integer openDt;
    private Long audiAcc;

    private Long genreId;
    private String genreNm;

    @QueryProjection
    public MovieGenreDto(Long movieId, String movieNm, Integer openDt, Long audiAcc, Long genreId, String genreNm) {
        this.movieId = movieId;
        this.movieNm = movieNm;
        this.openDt = openDt;
        this.audiAcc = audiAcc;
        this.genreId = genreId;
        this.genreNm = genreNm;
    }
}
