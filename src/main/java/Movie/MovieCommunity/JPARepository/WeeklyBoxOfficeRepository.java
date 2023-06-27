package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.JpaWeeklyBoxOffice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WeeklyBoxOfficeRepository extends JpaRepository<JpaWeeklyBoxOffice, Long> {

    List<JpaWeeklyBoxOffice> findByRankingLessThan(Integer ranking);
    List<JpaWeeklyBoxOffice> findByOpenDtBetween(LocalDate startDt,LocalDate endDt);

    List<JpaWeeklyBoxOffice> findByOpenDtBetweenOrderByRankingAsc(LocalDate startDt, LocalDate endDt);
    List<JpaWeeklyBoxOffice> findByMovieCdOrderByYearWeekTime(String movieCd);
    @Query("select wb from weeklyboxoffice wb where wb.id = (select max(swb.id) from weeklyboxoffice swb)")
    Optional<JpaWeeklyBoxOffice> findLastByWeeklyId();
}
