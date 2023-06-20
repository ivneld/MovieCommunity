package Movie.MovieCommunity.web.apiDto.movie.response;

import lombok.Builder;
import lombok.Data;

@Data
public class MovieSearchResponse {
    private Long id;
    private String posterPath;
    private String movieNm;
    private Integer openDt;
    @Builder
    public MovieSearchResponse(Long id, String posterPath, String movieNm, Integer openDt) {
        this.id = id;
        this.posterPath = posterPath;
        this.movieNm = movieNm;
        this.openDt = openDt;
    }
}
