package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.JpaMovieWithActor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;


import java.util.List;

public interface MovieWithActorRepository extends JpaRepository<JpaMovieWithActor, Long> {
    @Query("select mwa from moviewithactor mwa where movie_id = :movieId")
    List<JpaMovieWithActor> findByMovieId(@Param("movieId") Long movieId);

    @Query("select mwa from moviewithactor mwa" +
            " join fetch mwa.actor a" +
            " where movie_id = :movieId")
    List<JpaMovieWithActor> findAllActor(@Param("movieId") Long movieId);
}
