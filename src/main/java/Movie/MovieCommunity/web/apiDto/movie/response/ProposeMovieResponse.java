package Movie.MovieCommunity.web.apiDto.movie.response;

import Movie.MovieCommunity.JPADomain.Movie;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class ProposeMovieResponse {

    private String keyword;
    private List<Movie> movies;

    @Builder
    public ProposeMovieResponse(String keyword, List<Movie> movies) {
        this.keyword = keyword;
        this.movies = movies;
    }
}
