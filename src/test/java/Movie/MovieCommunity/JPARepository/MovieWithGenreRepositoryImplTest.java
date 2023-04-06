package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.dto.MovieGenreDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class MovieWithGenreRepositoryImplTest {

    @Autowired
    MovieWithGenreRepositoryCustom movieWithGenreRepositoryCustom;

    @Test
    public void findAll() {
        List<MovieGenreDto> result = movieWithGenreRepositoryCustom.findAllMovieAndGenre();

        for (MovieGenreDto movieGenreDto : result) {
            log.info("movieGenreDto={}", movieGenreDto);
        }
    }
}