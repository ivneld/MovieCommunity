package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.JpaMovieWithActor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieWithActorRepository extends JpaRepository<JpaMovieWithActor, Long> {
}
