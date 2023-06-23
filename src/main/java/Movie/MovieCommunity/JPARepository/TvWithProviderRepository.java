package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.Tv;
import Movie.MovieCommunity.JPADomain.TvWithProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TvWithProviderRepository extends JpaRepository<TvWithProvider, Long> {


    Optional<TvWithProvider> findByTv(Tv tv);

}