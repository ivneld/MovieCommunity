package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.Tv;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TvRepository extends JpaRepository<Tv,Long> {

    Optional<Tv> findById(Long id);
    Optional<Tv> findByTvId(Long tvId);

    Optional<Tv> findByTvNm(String tvNm);

    Optional<Tv> findByTmId(Long tmId);
}
