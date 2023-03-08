package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.JpaWeeklyBoxOffice;
import com.querydsl.core.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import static Movie.MovieCommunity.JPADomain.QJpaMovie.jpaMovie;
import static Movie.MovieCommunity.JPADomain.QJpaWeeklyBoxOffice.jpaWeeklyBoxOffice;

public interface WeeklyBoxOfficeRepository extends JpaRepository<JpaWeeklyBoxOffice, Long> {

    List<JpaWeeklyBoxOffice> findByRankingLessThan(Integer ranking);

}
