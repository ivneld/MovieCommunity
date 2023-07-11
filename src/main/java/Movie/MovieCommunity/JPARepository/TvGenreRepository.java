package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.TvGenre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TvGenreRepository extends JpaRepository<TvGenre, Long> {
    Optional<TvGenre> findByGenreNm(String genreNm);
    Optional<TvGenre> findByGenreTmId(Long genreTmId);

}
