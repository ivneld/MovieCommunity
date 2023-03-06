package Movie.MovieCommunity.dataCollection;

import Movie.MovieCommunity.JPADomain.JpaActor;
import Movie.MovieCommunity.JPADomain.JpaMovie;
import Movie.MovieCommunity.JPADomain.JpaMovieWithActor;
import Movie.MovieCommunity.JPADomain.JpaWeeklyBoxOffice;
import Movie.MovieCommunity.JPARepository.ActorRepository;
import Movie.MovieCommunity.JPARepository.MovieRepository;
import Movie.MovieCommunity.JPARepository.MovieWithActorRepository;
import Movie.MovieCommunity.JPARepository.WeeklyBoxOfficeRepository;
import Movie.MovieCommunity.domain.WeeklyBoxOffice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class MovieDataServiceTest {

    @Autowired MovieRepository movieRepository;

    @Autowired ActorRepository actorRepository;
    @Autowired MovieWithActorRepository movieWithActorRepository;

    @Autowired WeeklyBoxOfficeRepository weeklyBoxOfficeRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    public void actorTopCntTest() {
        List<JpaMovieWithActor> allActor = movieWithActorRepository.findAllActor(1904L);

        for (JpaMovieWithActor jpaMovieWithActor : allActor) {
            JpaActor actor = jpaMovieWithActor.getActor();

        }
    }

    @Test
    public void test() {
        List<JpaWeeklyBoxOffice> weeklyBoxOffices = weeklyBoxOfficeRepository.findByRankingLessThan(11);

        weeklyBoxOffices.stream().forEach(weeklyBoxOffice -> {
            if (movieRepository.findByMovieCd(weeklyBoxOffice.getMovieCd()).isPresent()) {
                log.info("hihi");
            }

        });
    }

    private void setActorCnt(JpaMovie jpaMovie) {
        List<JpaMovieWithActor> movieWithActors = movieWithActorRepository.findAllActor(jpaMovie.getId());
        movieWithActors.stream().forEach(movieWithActor -> {
            JpaActor actor = movieWithActor.getActor();
//            actor.setTopMovieCnt(actor.getTopMovieCnt() + 1);
        });
    }

    @Test
    @Rollback(value = false)
    public void cacheTest() {
        List<JpaWeeklyBoxOffice> weeklyBoxOffices = weeklyBoxOfficeRepository.findByRankingLessThan(11).subList(0, 30);

        weeklyBoxOffices.stream().forEach(weeklyBoxOffice -> {
            if (movieRepository.findByMovieCd(weeklyBoxOffice.getMovieCd()).isPresent()) {
                JpaMovie movie = movieRepository.findByMovieCd(weeklyBoxOffice.getMovieCd()).get();

                List<JpaMovieWithActor> movieWithActors = movieWithActorRepository.findAllActor(movie.getId());
                movieWithActors.stream().forEach(movieWithActor -> {
                    JpaActor actor = movieWithActor.getActor();
                    actor.plusTopMovieCnt();


                    log.info("actor id={}", actor.getId());
                    log.info("-> cnt={}", actor.getTopMovieCnt());
                });
            }
        });
    }
}