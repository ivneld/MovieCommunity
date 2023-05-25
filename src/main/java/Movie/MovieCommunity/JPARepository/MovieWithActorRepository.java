package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.MovieWithCredit;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface MovieWithActorRepository extends JpaRepository<MovieWithCredit, Long> {
    List<MovieWithCredit> findByMovieId(Long movieId);

}
