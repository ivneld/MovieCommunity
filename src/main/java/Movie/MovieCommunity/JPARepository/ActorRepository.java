package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.Credit;
import Movie.MovieCommunity.JPADomain.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ActorRepository extends JpaRepository<Credit, Long> {
    Optional<Credit> findByActorNm(String actorNm);
    List<Credit> findTop4ByActorNmContaining(String actorNm);

    @Query(value = "select c from Credit c where c.actorNm like %:actorNm%",
            countQuery = "select count(c) from Credit c where c.actorNm like %:actorNm%"
    )
    Page<Credit> findPageByMovieNmContaining(@Param("actorNm")String actorNm, Pageable pageable);
}
