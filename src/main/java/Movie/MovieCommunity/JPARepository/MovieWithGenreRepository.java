package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.JpaMovieWithGenre;
import Movie.MovieCommunity.web.apiDto.movie.entityDto.LikeGenreDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieWithGenreRepository extends JpaRepository<JpaMovieWithGenre, Long> {
    @Query("select new Movie.MovieCommunity.web.apiDto.movie.entityDto.LikeGenreDto(mg.genre.genreNm, count(mg.id))" +
            " from JpaMovieWithGenre mg join mg.genre g join mg.movie m where m.id in :movieIds group by g.id order by count(mg.id) desc ")
    List<LikeGenreDto> findByLikeGenreCnt(List<Long> movieIds);

}
