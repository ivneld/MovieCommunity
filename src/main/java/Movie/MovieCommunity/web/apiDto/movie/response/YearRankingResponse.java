package Movie.MovieCommunity.web.apiDto.movie.response;

import lombok.Builder;
import lombok.Data;

@Data
public class YearRankingResponse {
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
        private String topComment;
    @Builder
    public YearRankingResponse(int rank, Long id, String movieNm, Integer showTm, Integer openDt,String prdtStatNm, String watchGradeNm, String overview, String posterPath, float voteAverage, int interest) {
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
        this.interest = interest;
    }
}
