package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.JpaMovieWithActor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;


import java.util.List;

public interface MovieWithActorRepository extends JpaRepository<JpaMovieWithActor, Long> {
    List<JpaMovieWithActor> findByMovieId(Long movieId);

}
