package Movie.MovieCommunity.web.apiDto.movie.response;

import Movie.MovieCommunity.JPADomain.Video;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class WeeklyResponse {
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
    private int interest;
    private boolean myInterest;
    private List<Video> videos = new ArrayList<>();

    @Builder
    public WeeklyResponse(int rank, Long id, String movieNm, Integer showTm, Integer openDt, String prdtStatNm, String watchGradeNm, String overview, String posterPath, float voteAverage) {
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
