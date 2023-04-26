package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.LikeMovie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeMovieRepository extends JpaRepository<LikeMovie, Long> {
}
