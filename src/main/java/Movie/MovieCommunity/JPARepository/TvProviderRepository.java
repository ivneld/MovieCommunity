package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.TvProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TvProviderRepository extends JpaRepository<TvProvider,Long> {
    Optional<TvProvider> findByProviderTmId(Long providerId);
}
