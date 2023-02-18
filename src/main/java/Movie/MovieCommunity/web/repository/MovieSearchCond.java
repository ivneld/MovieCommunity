package Movie.MovieCommunity.web.repository;

import lombok.Data;

@Data
public class MovieSearchCond {

    private String movieNm;
    private Integer openDt;

    public MovieSearchCond() {

    }

    public MovieSearchCond(String movieNm, Integer openDt) {
        this.movieNm = movieNm;
        this.openDt = openDt;
    }
}