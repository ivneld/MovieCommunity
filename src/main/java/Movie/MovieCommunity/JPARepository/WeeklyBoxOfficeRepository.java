package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.JpaWeeklyBoxOffice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeeklyBoxOfficeRepository extends JpaRepository<JpaWeeklyBoxOffice, Long> {
}
