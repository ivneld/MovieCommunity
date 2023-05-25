package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    Optional<Movie> findByMovieCd(String movieCd);

    @Query(value = "select * from movie m where m.open_dt between :startDt and :endDt order by m.popularity desc limit 10", nativeQuery = true)
    List<Movie> findYearRankingByOpenDt(@Param("startDt") int startDt, @Param("endDt") int endDt);

//    @Query("select m from movie m join fetch m.comments c join fetch m.like_movie lm")
//    List<JpaMovie> findQueryYearRankingByOpenDt();
    @Query("select m from movie m")
    List<Movie> findList();
    Optional<Movie> findByTmId(@Param("tmId") int tmId);

}
