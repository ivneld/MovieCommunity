package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.JpaMovieWithActor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
@Transactional
class MovieWithActorRepositoryTest {
    @Autowired
    EntityManager em;
    @Autowired
    MovieWithActorRepository movieWithActorRepository;
    @Test
    public void find(){
        List<JpaMovieWithActor> mwaList = movieWithActorRepository.findAll();
        for (JpaMovieWithActor jpaMovieWithActor : mwaList) {
            System.out.println("jpaMovieWithActor = " + jpaMovieWithActor);
        }
    }

    @Test
    public void QueryFindAll(){
        List<JpaMovieWithActor> mwaList = em.createQuery("select mwa from moviewithactor mwa", JpaMovieWithActor.class).getResultList();
        for (JpaMovieWithActor jpaMovieWithActor : mwaList) {
            System.out.println("jpaMovieWithActor = " + jpaMovieWithActor);
        }

    }
}