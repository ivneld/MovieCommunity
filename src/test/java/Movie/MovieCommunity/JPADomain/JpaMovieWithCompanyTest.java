package Movie.MovieCommunity.JPADomain;

import Movie.MovieCommunity.JPARepository.MovieRepository;
import Movie.MovieCommunity.JPARepository.MovieWithCompanyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
@Rollback(value = false)
class JpaMovieWithCompanyTest {

    @Autowired
    private MovieWithCompanyRepository movieWithCompanyRepository;
    @Test
    public void deleteTest(){
        Optional<JpaMovieWithCompany> byId = movieWithCompanyRepository.findById(15l);
        movieWithCompanyRepository.delete(byId.get());
    }

}