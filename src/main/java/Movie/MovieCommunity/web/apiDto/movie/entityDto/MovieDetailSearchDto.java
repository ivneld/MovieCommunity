package Movie.MovieCommunity.web.apiDto.movie.entityDto;

import lombok.Builder;
import lombok.Data;

@Data
public class MovieDetailSearchDto {
    private Long id;
    private String posterPath;
    private String movieNm;
    private Integer openDt;
    private String nationNm;
    @Builder
    public MovieDetailSearchDto(Long id, String posterPath, String movieNm, Integer openDt, String nationNm) {
        this.id = id;
        this.posterPath = posterPath;
        this.movieNm = movieNm;
        this.openDt = openDt;
        this.nationNm = nationNm;
    }
}
