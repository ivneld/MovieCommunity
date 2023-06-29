package Movie.MovieCommunity.web.apiDto.movie.response;

import Movie.MovieCommunity.web.apiDto.credit.CreditDetailSearchDto;
import Movie.MovieCommunity.web.apiDto.movie.entityDto.MovieDetailSearchDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SearchDetailResponse {
    private int movieCnt;
    private int creditCnt;
    private List<MovieDetailSearchDto> movies;
    private List<CreditDetailSearchDto> credits;

    public SearchDetailResponse(int movieCnt, int creditCnt, List<MovieDetailSearchDto> movies, List<CreditDetailSearchDto> credits) {
        this.movieCnt = movieCnt;
        this.creditCnt = creditCnt;
        this.movies = movies;
        this.credits = credits;
    }

}
