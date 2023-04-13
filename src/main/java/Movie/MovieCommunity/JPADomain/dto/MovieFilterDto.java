package Movie.MovieCommunity.JPADomain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MovieFilterDto {

    private Long movieId;
    private Long genreId;
    private String movieNm;
    private Integer showTm;
    private Integer openDt;
    private String typeNm;
    private String nationNm;
    private String directorNm;
    private String auditNo;
    private String watchGradeNm;
    private Integer topScore;
    private Long salesAcc;
    private Long audiAcc;
    private String genreNm;

    @QueryProjection
    public MovieFilterDto(Long movieId, Long genreId, String movieNm, Integer showTm, Integer openDt, String typeNm, String nationNm, String directorNm, String auditNo, String watchGradeNm, Integer topScore, Long salesAcc, Long audiAcc, String genreNm) {
        this.movieId = movieId;
        this.genreId = genreId;
        this.movieNm = movieNm;
        this.showTm = showTm;
        this.openDt = openDt;
        this.typeNm = typeNm;
        this.nationNm = nationNm;
        this.directorNm = directorNm;
        this.auditNo = auditNo;
        this.watchGradeNm = watchGradeNm;
        this.topScore = topScore;
        this.salesAcc = salesAcc;
        this.audiAcc = audiAcc;
        this.genreNm = genreNm;
    }
}
