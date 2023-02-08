package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.JpaMovieWithActor;
import Movie.MovieCommunity.domain.MovieWithActor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;


import java.util.List;

public interface MovieWithActorRepository extends JpaRepository<JpaMovieWithActor, Long> {
//<<<<<<< HEAD
//    @Query("select mwa from moviewithactor mwa")
//    List<JpaMovieWithActor> findByMovieId(Long movieId);
//=======
//
//>>>>>>> 9eb4d5a89a1fea16b0f441594c856e644cd70f23
}
