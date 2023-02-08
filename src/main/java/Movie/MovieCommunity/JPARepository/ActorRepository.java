package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.JpaActor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActorRepository extends JpaRepository<JpaActor, Long> {
    Optional<JpaActor> findByActorNm(String actorNm);
}
