package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.JpaMovie;
import Movie.MovieCommunity.JPADomain.dto.MovieDto;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static Movie.MovieCommunity.JPADomain.QJpaMovie.jpaMovie;
import static Movie.MovieCommunity.JPADomain.QJpaWeeklyBoxOffice.jpaWeeklyBoxOffice;

@SpringBootTest
@Transactional
@Slf4j
class MovieRepositoryTest {
//    public MovieRepositoryTest(EntityManager em) {
//        this.em = em;
//        this.queryFactory = new QueryFactory(em);
//    }

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieWithActorRepository movieWithActorRepository;
    @Autowired
    private EntityManager em;

    JPAQueryFactory queryFactory;

    @BeforeEach
    public void before() {
        queryFactory = new JPAQueryFactory(em);
    }

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
    public void movieWeeklyTest(){
        List<Tuple> list = queryFactory.select(jpaMovie.movieCd, jpaWeeklyBoxOffice.salesAcc)
                .from(jpaMovie)
                .leftJoin(jpaWeeklyBoxOffice).on(jpaMovie.movieCd.eq(jpaWeeklyBoxOffice.movieCd))
                .fetch();

        for (Tuple tuple : list) {
            log.info("tuple={}", tuple);
        }
    }


}