package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.LikeMovie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeMovieRepository extends JpaRepository<LikeMovie, Long> {
    Optional<LikeMovie> findByMovieIdAndMemberId(Long movieId, Long memberId);

    List<LikeMovie> findByMemberId(Long memberId);
}
