package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.Credit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActorRepository extends JpaRepository<Credit, Long> {
    Optional<Credit> findByActorNm(String actorNm);
}
