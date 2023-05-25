package Movie.MovieCommunity.dataCollection;

import Movie.MovieCommunity.JPADomain.JpaWeeklyBoxOffice;
import Movie.MovieCommunity.JPARepository.WeeklyBoxOfficeRepository;
import Movie.MovieCommunity.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
@Slf4j
public class MovieDataServiceTest {

    @Autowired
    MovieService movieService;

    @Test
    public void test () {

    }
//    @Test
//    public void weeklyRankingTest() {
//        List<YearRankingResponse> list = movieService.weeklyRanking(2023, 1);
//        log.info("size={}",list.size());
//    }
}