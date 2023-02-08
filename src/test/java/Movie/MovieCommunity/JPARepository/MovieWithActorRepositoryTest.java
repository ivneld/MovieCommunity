package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.JpaMovie;
import Movie.MovieCommunity.JPADomain.JpaMovieWithActor;
import Movie.MovieCommunity.domain.MovieWithActor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional

class MovieWithActorRepositoryTest {
    @Autowired
    MovieRepository movieWithActorRepository;
    @Test
    public void find(){
        List<JpaMovie> byMovieId = movieWithActorRepository.findAll();
    }
}