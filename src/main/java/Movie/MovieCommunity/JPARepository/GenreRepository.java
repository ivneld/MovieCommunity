package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.JpaGenre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<JpaGenre, Long> {
    Optional<JpaGenre> findByGenreNm(String genreNm);
}
