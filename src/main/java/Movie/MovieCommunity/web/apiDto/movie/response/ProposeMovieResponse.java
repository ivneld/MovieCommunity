package Movie.MovieCommunity.web.apiDto.movie.response;

import Movie.MovieCommunity.JPADomain.Movie;
import Movie.MovieCommunity.JPARepository.dao.ProposeMovieDao;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProposeMovieResponse {

    private String keyword;
    private List<ProposeMovieDao> movies = new ArrayList<>();

    public ProposeMovieResponse(String keyword, List<ProposeMovieDao> movies) {
        this.keyword = keyword;
        this.movies = movies;
    }
}
