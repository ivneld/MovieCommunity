package Movie.MovieCommunity.web.apiDto.movie.response;

import lombok.Builder;
import lombok.Data;

@Data
public class WeeklyRankingResponse {
    private Integer rank;
    private Integer rankInten;
    private String rankOldAndNew;
    private String week;
    @Builder
    public WeeklyRankingResponse(Integer rank, Integer rankInten, String rankOldAndNew, String week) {
        this.rank = rank;
        this.rankInten = rankInten;
        this.rankOldAndNew = rankOldAndNew;
        this.week = week;
    }
}
