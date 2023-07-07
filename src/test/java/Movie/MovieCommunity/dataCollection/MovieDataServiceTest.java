package Movie.MovieCommunity.dataCollection;

import Movie.MovieCommunity.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@SpringBootTest
@Transactional
@Slf4j
public class MovieDataServiceTest {

    @Autowired
    MovieService movieService;

    @Test
    public void test () {
        LocalDate now = LocalDate.now();
<<<<<<< HEAD
<<<<<<< HEAD
        //movieService.proposeMovie(now);
=======
        movieService.proposeByNowDayMovie(now);
>>>>>>> 27da92d1f7e242f7fbbd367528cc258e5304f85f
=======
        movieService.proposeByNowDayMovie(now);
        //movieService.proposeMovie(now);
>>>>>>> d006a11885fde9cf4afa21177cc234ab4bb04dc1
    }
//    @Test
//    public void weeklyRankingTest() {
//        List<YearRankingResponse> list = movieService.weeklyRanking(2023, 1);
//        log.info("size={}",list.size());
//    }
}