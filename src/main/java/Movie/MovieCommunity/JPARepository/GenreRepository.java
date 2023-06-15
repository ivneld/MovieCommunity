package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    Optional<Genre> findByGenreNm(String genreNm);
}
