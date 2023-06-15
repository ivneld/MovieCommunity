package Movie.MovieCommunity.web.apiDto.movie.response;

import lombok.Builder;
import lombok.Data;

@Data
public class MovieLikeResponse {
    private Long id;
    private int tmId;
    private String posterPath;
    private String movieNm;
    private Integer openDt;
    @Builder
    public MovieLikeResponse(Long id, int tmId, String posterPath, String movieNm, Integer openDt) {
        this.id = id;
        this.tmId = tmId;
        this.posterPath = posterPath;
        this.movieNm = movieNm;
        this.openDt = openDt;
    }
}
