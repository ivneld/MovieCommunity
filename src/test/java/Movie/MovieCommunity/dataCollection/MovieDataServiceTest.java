package Movie.MovieCommunity.dataCollection;

import Movie.MovieCommunity.JPADomain.MovieWithCredit;
import Movie.MovieCommunity.JPARepository.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
@Slf4j
class MovieDataServiceTest {

    @Autowired MovieRepository movieRepository;

    @Autowired ActorRepository actorRepository;
    @Autowired MovieWithActorRepository movieWithActorRepository;

    @Autowired WeeklyBoxOfficeRepository weeklyBoxOfficeRepository;

//    @Autowired
//    WeeklyBoxOfficeRepositoryCustom weeklyBoxOfficeRepositoryCustom;

    @Test
    public void test() {
        List<MovieWithCredit> movieWithActors = movieWithActorRepository.findByMovieId(1L);

        for (MovieWithCredit movieWithActor : movieWithActors) {
            log.info("movieWithActor={}", movieWithActor);
        }

    }
}