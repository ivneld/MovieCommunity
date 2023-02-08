package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.DTO.MovieDto;
import Movie.MovieCommunity.JPADomain.JpaMovie;
import Movie.MovieCommunity.JPADomain.JpaMovieWithActor;
import Movie.MovieCommunity.domain.Movie;
import Movie.MovieCommunity.domain.MovieWithActor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
@Rollback(value = false)
class MovieRepositoryTest {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MovieWithActorRepository movieWithActorRepository;
    @Autowired
    private EntityManager em;
    @Test
    public void selectOne(){
em.flush();
em.clear();
////        Optional<JpaMovie> byId = movieRepository.findById(140l);
//        MovieDto movieDto = new MovieDto();
////        JpaMovie jpaMovie2 = byId.get();
////        jpaMovie2.setMovieCd("BBBBBBBBBBBBBB");
//        JpaMovie jpaMovie = new JpaMovie(movieDto);
//
//        movieDto.setNationNm("1234");
//        movieDto.setMovieNm("Name");
//        JpaMovie save = movieRepository.save(jpaMovie);
//        movieDto.setNationNm("5432");
//        jpaMovie.updateData(movieDto);


    }


}