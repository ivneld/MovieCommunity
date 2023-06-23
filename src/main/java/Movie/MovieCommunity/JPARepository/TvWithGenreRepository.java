package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.TvWithGenre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TvWithGenreRepository extends JpaRepository<TvWithGenre, Long> {
}
