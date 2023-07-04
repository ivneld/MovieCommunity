package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query(value = "select m from movie m join fetch m.likeMovies lm join fetch lm.member mem where mem.id = :memberId",
            countQuery = "select count(m) from movie m join m.likeMovies lm join lm.member mem where mem.id = :memberId"
    )
    Page<Movie> findByLikeMovie(Pageable pageable, Long memberId);


    @Query(value = "select m from movie m join fetch m.likeMovies lm join fetch lm.member mem where mem.id = :memberId")
    List<Movie> findByLikeMovieList(Long memberId);
    List<Movie> findTop5ByMovieNmStartingWith(String movieNm);

    @Query("select m from movie m where movie_nm like %:keyword% or m.overview like %:keyword%")
    List<Movie> findByKeyword(String keyword);
    List<Movie> findTop4ByMovieNmContaining(String movieNm);
    int countByMovieNmContaining(String movieNm);
    @Query(value = "select m from movie m where m.movieNm like %:movieNm%",
            countQuery = "select count(m) from movie m where m.movieNm like %:movieNm%"
    )
    Page<Movie> findPageByMovieNmContaining(@Param("movieNm")String movieNm, Pageable pageable);

    @Query(value = "select m from movie m where m.prdtStatNm = :prdtStatNm and m.openDt > :openDt",
    countQuery = "select count(m) from movie m where m.prdtStatNm = :prdtStatNm and m.openDt > :openDt")
    Page<Movie> findComingMovieByPrdtStatNmAndOpenDt(@Param("prdtStatNm")String prdtStatNm, @Param("openDt")Integer openDt, Pageable pageable);
}
