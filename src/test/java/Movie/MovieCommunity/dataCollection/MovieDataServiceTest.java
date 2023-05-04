package Movie.MovieCommunity.dataCollection;

import Movie.MovieCommunity.JPADomain.JpaActor;
import Movie.MovieCommunity.JPADomain.JpaMovie;
import Movie.MovieCommunity.JPADomain.JpaMovieWithActor;
import Movie.MovieCommunity.JPADomain.JpaWeeklyBoxOffice;
import Movie.MovieCommunity.JPARepository.*;
import com.querydsl.core.Tuple;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
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

    @Autowired
    WeeklyBoxOfficeRepositoryCustom weeklyBoxOfficeRepositoryCustom;

    @Test
    public void test() {
        List<JpaMovieWithActor> movieWithActors = movieWithActorRepository.findByMovieId(1L);

        for (JpaMovieWithActor movieWithActor : movieWithActors) {
            log.info("movieWithActor={}", movieWithActor);
        }

    }
}