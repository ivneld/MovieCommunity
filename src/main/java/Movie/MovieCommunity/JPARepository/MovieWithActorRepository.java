package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.JpaMovieWithActor;
import Movie.MovieCommunity.domain.MovieWithActor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieWithActorRepository extends JpaRepository<JpaMovieWithActor, Long> {
//    @Query("select mwa moviewithactor mwa")
//    List<JpaMovieWithActor> findByMovieId();
}
