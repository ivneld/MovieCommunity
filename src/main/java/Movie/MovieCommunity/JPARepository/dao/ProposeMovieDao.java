package Movie.MovieCommunity.JPARepository.dao;

import lombok.Builder;
import lombok.Data;

@Data
public class ProposeMovieDao {
    private Long movieId;
    private String movieNm;
    private Integer showTm;
    private Integer openDt;
    private String prdtStatNm;
    private String watchGradeNm;
    private String overview;
    private String posterPath;
    private float voteAverage;
    private Integer interest;
    private boolean myInterest;

    @Builder
    public ProposeMovieDao(Long movieId, String movieNm, Integer showTm, Integer openDt, String prdtStatNm, String watchGradeNm, String overview, String posterPath, float voteAverage, Integer interest) {
        this.movieId = movieId;
        this.movieNm = movieNm;
        this.showTm = showTm;
        this.openDt = openDt;
        this.prdtStatNm = prdtStatNm;
        this.watchGradeNm = watchGradeNm;
        this.overview = overview;
        this.posterPath = posterPath;
        this.voteAverage = voteAverage;
        this.interest = interest;
    }
}
