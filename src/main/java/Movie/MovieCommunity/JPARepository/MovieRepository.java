package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.JpaMovie;
import Movie.MovieCommunity.JPADomain.dto.MovieWeekly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<JpaMovie, Long> {
    Optional<JpaMovie> findByMovieCd(String movieCd);



    @Query("select m from movie m")
    List<JpaMovie> findList();
}
