package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.JpaMovie;
import Movie.MovieCommunity.JPADomain.dto.MovieDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

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
        JpaMovie jpaMovie = new JpaMovie(movieDto);
        JpaMovie jpaMovie1 = new JpaMovie((movieDto));
        JpaMovie jpaMovie2 = new JpaMovie((movieDto));
        JpaMovie jpaMovie3 = new JpaMovie((movieDto));
        JpaMovie jpaMovie4 = new JpaMovie((movieDto));


        JpaMovie save = movieRepository.save(jpaMovie);
        movieRepository.save(jpaMovie1);
        movieRepository.save(jpaMovie2);
        movieRepository.save(jpaMovie3);
        movieRepository.save(jpaMovie4);



        movieDto.setDirectorNm("5432");
        jpaMovie1.updateData(movieDto);
        jpaMovie2.updateData(movieDto);
        jpaMovie3.updateData(movieDto);
        jpaMovie4.updateData(movieDto);


//        System.out.println("===============movieDto = " + movieDto);
        save.updateData(movieDto);


    }
    @Test
    public void movieList(){
        List<JpaMovie> list = movieRepository.findList();
        for (JpaMovie jpaMovie : list) {
            System.out.println("jpaMovie = " + jpaMovie);
        }
    }




}