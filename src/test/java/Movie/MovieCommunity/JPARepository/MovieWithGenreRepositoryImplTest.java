package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.dto.MovieGenreDto;
import Movie.MovieCommunity.JPADomain.dto.MovieWithGenreCountDto;
import Movie.MovieCommunity.JPARepository.dao.MovieWithGenreCountDao;
import Movie.MovieCommunity.service.MovieWithGenreService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
@Slf4j
class MovieWithGenreRepositoryImplTest {

    @Autowired
    MovieWithGenreRepositoryCustom movieWithGenreRepositoryCustom;
    @Autowired
    MovieWithGenreService movieWithGenreService;

    @Test
    public void findAll() {
        List<MovieGenreDto> result = movieWithGenreRepositoryCustom.findAllMovieAndGenre();

        for (MovieGenreDto movieGenreDto : result) {
            log.info("movieGenreDto={}", movieGenreDto);
        }
    }

    @Test
    public void top100Test() {
        List<MovieWithGenreCountDao> list = movieWithGenreRepositoryCustom.findTop100MovieWithGenre();
        for (MovieWithGenreCountDao movieWithGenreCountDao : list) {
            log.info("result={}, {}", movieWithGenreCountDao.getGenreNm(), movieWithGenreCountDao.getPopularity());
        }
    }

    @Test
    public void serviceTest() {
        List<MovieWithGenreCountDto> result = movieWithGenreService.genreCount();
        for (MovieWithGenreCountDto dto : result) {
            log.info("result={}, {}", dto.getGenreNm(), dto.getCount());
        }
    }
}