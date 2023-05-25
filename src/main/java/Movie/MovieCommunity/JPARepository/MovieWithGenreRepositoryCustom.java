package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.JpaMovie;
import Movie.MovieCommunity.JPADomain.dto.MovieGenreDto;
import Movie.MovieCommunity.JPARepository.dao.MovieWithGenreCountDao;
import Movie.MovieCommunity.JPARepository.dao.MovieWithGenreDao;

import java.util.List;

public interface MovieWithGenreRepositoryCustom {

    List<MovieGenreDto> findAllMovieAndGenre();

    List<MovieWithGenreCountDao> findTop100MovieWithGenre();

    List<MovieWithGenreDao> findMovieByGenre(Long genreId);
}
