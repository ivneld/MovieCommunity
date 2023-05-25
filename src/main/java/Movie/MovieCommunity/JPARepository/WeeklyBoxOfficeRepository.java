package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.JpaWeeklyBoxOffice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface WeeklyBoxOfficeRepository extends JpaRepository<JpaWeeklyBoxOffice, Long> {

    List<JpaWeeklyBoxOffice> findByRankingLessThan(Integer ranking);
    List<JpaWeeklyBoxOffice> findByOpenDtBetween(LocalDate startDt,LocalDate endDt);
}
