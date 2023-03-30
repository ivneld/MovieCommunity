package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.JpaMovie;
import Movie.MovieCommunity.JPARepository.searchCond.MovieSearchCond;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<JpaMovie, Long> {
    Optional<JpaMovie> findByMovieCd(String movieCd);
//    Page<JpaMovie> findByMovieSearchCond(MovieSearchCond searchCond);
    @Query("select m from movie m")
    List<JpaMovie> findList();
}
