package Movie.MovieCommunity.web.apiDto.movie.response;

import lombok.Builder;
import lombok.Data;

@Data
public class ComingMovieResponse {
    private Long id;
    private String movieNm;
    private Integer openDt;
    private String posterPath;
    private Integer lastDay;
    @Builder
    public ComingMovieResponse(Long id, String movieNm, Integer openDt, String posterPath, Integer lastDay) {
        this.id = id;
        this.movieNm = movieNm;
        this.openDt = openDt;
        this.posterPath = posterPath;
        this.lastDay = lastDay;
    }

}
