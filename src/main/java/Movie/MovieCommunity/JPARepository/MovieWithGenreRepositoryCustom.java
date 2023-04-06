package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.dto.MovieGenreDto;

import java.util.List;

public interface MovieWithGenreRepositoryCustom {

    List<MovieGenreDto> findAllMovieAndGenre();
}
