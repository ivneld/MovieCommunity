package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.JpaWeeklyBoxOffice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeeklyBoxOfficeRepository extends JpaRepository<JpaWeeklyBoxOffice, Long> {

    List<JpaWeeklyBoxOffice> findByRankingLessThan(Integer ranking);

}
