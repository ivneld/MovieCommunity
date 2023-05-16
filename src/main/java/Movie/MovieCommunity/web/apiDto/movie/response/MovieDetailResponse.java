package Movie.MovieCommunity.web.apiDto.movie.response;

import lombok.Builder;
import lombok.Data;

@Data
public class MovieDetailResponse {
    private Long id;
    private String movieCd;
    private String movieNm;
    private Integer showTm;
    private Integer openDt;
    private String prdtStatNm;
    private String typeNm;
    private String nationNm;
    private String directorNm;
    private String auditNo;
    private String watchGradeNm;
    private int topScore;
    private Long salesAcc;
    private Long audiAcc;
    private int tmId;
    private String overview;
    private String backdropPath;
    private String posterPath;
    private float popularity;
    private float voteAverage;
    private int voteCount;
    private Integer collectionId;
    private String seriesName;
    private String collectionBackdropPath;
    private String collectionPosterPath;
    private String topComment;
    @Builder
    public MovieDetailResponse(Long id, String movieCd, String movieNm, Integer showTm, Integer openDt, String prdtStatNm, String typeNm, String nationNm, String directorNm, String auditNo, String watchGradeNm, int topScore, Long salesAcc, Long audiAcc, int tmId, String overview, String backdropPath, String posterPath, float popularity, float voteAverage, int voteCount, Integer collectionId, String seriesName, String collectionBackdropPath, String collectionPosterPath, String topComment) {
        this.id = id;
        this.movieCd = movieCd;
        this.movieNm = movieNm;
        this.showTm = showTm;
        this.openDt = openDt;
        this.prdtStatNm = prdtStatNm;
        this.typeNm = typeNm;
        this.nationNm = nationNm;
        this.directorNm = directorNm;
        this.auditNo = auditNo;
        this.watchGradeNm = watchGradeNm;
        this.topScore = topScore;
        this.salesAcc = salesAcc;
        this.audiAcc = audiAcc;
        this.tmId = tmId;
        this.overview = overview;
        this.backdropPath = backdropPath;
        this.posterPath = posterPath;
        this.popularity = popularity;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
        this.collectionId = collectionId;
        this.seriesName = seriesName;
        this.collectionBackdropPath = collectionBackdropPath;
        this.collectionPosterPath = collectionPosterPath;
        this.topComment = topComment;
    }

}
