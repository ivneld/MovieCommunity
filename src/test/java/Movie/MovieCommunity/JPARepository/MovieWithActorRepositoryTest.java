package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.MovieWithCredit;
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
        List<MovieWithCredit> mwaList = movieWithActorRepository.findAll();
        for (MovieWithCredit movieWithCredit : mwaList) {
            System.out.println("jpaMovieWithActor = " + movieWithCredit);
        }
    }

    @Test
    public void QueryFindAll(){
        List<MovieWithCredit> mwaList = em.createQuery("select mwa from moviewithactor mwa", MovieWithCredit.class).getResultList();
        for (MovieWithCredit movieWithCredit : mwaList) {
            System.out.println("jpaMovieWithActor = " + movieWithCredit);
        }

    }
}