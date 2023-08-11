package Movie.MovieCommunity.JPARepository.dao;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MovieWithWeeklyDao {

    private int rank;
    private Long id;
    private String movieNm;
    private Integer showTm;
    private Integer openDt;
    private String prdtStatNm;
    private String watchGradeNm;
    private String overview;
    private String posterPath;
    private float voteAverage;


    @QueryProjection
    public MovieWithWeeklyDao(int rank, Long id, String movieNm, Integer showTm, Integer openDt, String prdtStatNm, String watchGradeNm, String overview, String posterPath, float voteAverage) {
        this.rank = rank;
        this.id = id;
        this.movieNm = movieNm;
        this.showTm = showTm;
        this.openDt = openDt;
        this.prdtStatNm = prdtStatNm;
        this.watchGradeNm = watchGradeNm;
        this.overview = overview;
        this.posterPath = posterPath;
        this.voteAverage = voteAverage;
    }
}
