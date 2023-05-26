package Movie.MovieCommunity.JPARepository.dao;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MovieWithGenreCountDao {
    private Long genreId;
    private String genreNm;
    private Long movieId;
    private float popularity;

    @QueryProjection
    public MovieWithGenreCountDao(Long genreId, String genreNm, Long movieId, float popularity) {
        this.genreId = genreId;
        this.genreNm = genreNm;
        this.movieId = movieId;
        this.popularity = popularity;
    }
}
