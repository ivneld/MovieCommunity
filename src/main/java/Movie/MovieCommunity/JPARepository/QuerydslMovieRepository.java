package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.dto.MovieDto;
import Movie.MovieCommunity.JPARepository.dao.MovieDao;
import Movie.MovieCommunity.JPARepository.searchCond.MovieSearchCond;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuerydslMovieRepository {
    Page<MovieDao> findByMovieCond(MovieSearchCond movieSearchCond, Pageable pageable);
}
