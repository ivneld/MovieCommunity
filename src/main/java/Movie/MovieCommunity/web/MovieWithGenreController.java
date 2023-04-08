package Movie.MovieCommunity.web;

import Movie.MovieCommunity.JPADomain.dto.MovieFilterDto;
import Movie.MovieCommunity.JPARepository.MovieWithGenreRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/genre")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class MovieWithGenreController {

    @Autowired
    MovieWithGenreRepositoryCustom movieWithGenreRepositoryCustom;

    /**
     * 전체 데이터를 넘겨주고 프론트에서 genre_id 를 이용하여 필터링 할 수 있도록 함.
     */
    public List<MovieFilterDto> genrePage() {
        return movieWithGenreRepositoryCustom.movieWithGenre();
    }
}
