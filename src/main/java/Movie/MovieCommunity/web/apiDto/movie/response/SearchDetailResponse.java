package Movie.MovieCommunity.web.apiDto.movie.response;

import Movie.MovieCommunity.web.apiDto.credit.CreditDetailSearchDto;
import Movie.MovieCommunity.web.apiDto.movie.entityDto.MovieDetailSearchDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SearchDetailResponse {
    List<MovieDetailSearchDto> movies = new ArrayList<>();
    List<CreditDetailSearchDto> credits;

    public SearchDetailResponse(List<MovieDetailSearchDto> movies, List<CreditDetailSearchDto> credits) {
        this.movies = movies;
        this.credits = credits;
    }
}
