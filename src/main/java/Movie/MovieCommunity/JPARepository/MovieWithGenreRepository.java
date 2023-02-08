package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.JpaMovieWithGenre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieWithGenreRepository extends JpaRepository<JpaMovieWithGenre, Long> {
}
