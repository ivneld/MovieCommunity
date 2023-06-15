package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.Movie;
import Movie.MovieCommunity.JPADomain.dto.MovieDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

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
//        Optional<JpaMovie> byId = movieRepository.findById(140l);
        MovieDto movieDto = new MovieDto();
        movieDto.setNationNm("1234");
        movieDto.setMovieNm("Name");
//        JpaMovie jpaMovie2 = byId.get();
//        jpaMovie2.setMovieCd("BBBBBBBBBBBBBB");
        Movie movie = new Movie(movieDto);
        Movie movie1 = new Movie((movieDto));
        Movie movie2 = new Movie((movieDto));
        Movie movie3 = new Movie((movieDto));
        Movie movie4 = new Movie((movieDto));


        Movie save = movieRepository.save(movie);
        movieRepository.save(movie1);
        movieRepository.save(movie2);
        movieRepository.save(movie3);
        movieRepository.save(movie4);



        movieDto.setDirectorNm("5432");
        movie1.updateData(movieDto);
        movie2.updateData(movieDto);
        movie3.updateData(movieDto);
        movie4.updateData(movieDto);


//        System.out.println("===============movieDto = " + movieDto);
        save.updateData(movieDto);


    }

    @Test
    public void deleteTest(){
        Optional<Movie> findMovie = movieRepository.findById(8l);
        Movie movie = findMovie.get();
        movieRepository.delete(movie);
    }

}