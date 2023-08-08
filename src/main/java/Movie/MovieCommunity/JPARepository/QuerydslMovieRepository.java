package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPARepository.dao.MovieDao;
import Movie.MovieCommunity.JPARepository.dao.MovieWithWeeklyDao;
import Movie.MovieCommunity.JPARepository.searchCond.MovieSearchCond;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QuerydslMovieRepository {
    Page<MovieDao> findByMovieCond(MovieSearchCond movieSearchCond, Pageable pageable);

    List<MovieWithWeeklyDao> findByShowRange(String showRange);
}
