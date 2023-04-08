package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.dto.MovieFilterDto;
import Movie.MovieCommunity.JPADomain.dto.MovieGenreDto;

import java.util.List;

public interface MovieWithGenreRepositoryCustom {

    List<MovieGenreDto> findAllMovieAndGenre();

    // 장르별 페이지
    List<MovieFilterDto> movieWithGenre();

}
