package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.JpaActor;
import Movie.MovieCommunity.JPADomain.JpaMovie;
import Movie.MovieCommunity.JPADomain.JpaMovieWithActor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@SpringBootTest
@Transactional
@Slf4j
class MovieWithActorRepositoryTest {
    @Autowired
    MovieWithActorRepository movieWithActorRepository;
    @Autowired
    MovieRepository movieRepository;
    @Autowired
    ActorRepository actorRepository;

    @Test
    public void find(){

        JpaMovie jpaMovie = movieRepository.findByMovieCd("20239955").get();
        List<JpaMovieWithActor> byMovieId = movieWithActorRepository.findByMovieId(jpaMovie.getId());

        for (JpaMovieWithActor jpaMovieWithActor : byMovieId) {
            JpaActor jpaActor = actorRepository.findById(jpaMovieWithActor.getActor().getId()).get();
            log.info("actor={}", jpaActor);
        }
    }

    @Test
    public void test() {
        List<JpaMovieWithActor> allActor = movieWithActorRepository.findAllActor(1L);

        for (JpaMovieWithActor actor : allActor) {
            log.info("actor={}", actor.getActor());
        }
    }
}