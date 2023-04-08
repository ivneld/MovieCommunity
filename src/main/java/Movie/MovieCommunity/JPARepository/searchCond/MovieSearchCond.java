package Movie.MovieCommunity.JPARepository.searchCond;

import lombok.Data;

@Data
public class MovieSearchCond {
    private String movieNm;
    private Integer openDt;
    private String prdtStatNm;
    private Integer showTm;
    private String typeNm;
    private String nationNm;
    private String directorNm;
    private String watchGradeNm;
    private Integer topScore;
}
