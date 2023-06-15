package Movie.MovieCommunity.service;

import Movie.MovieCommunity.JPADomain.Genre;
import Movie.MovieCommunity.JPADomain.dto.MovieWithGenreCountDto;
import Movie.MovieCommunity.JPARepository.GenreRepository;
import Movie.MovieCommunity.JPARepository.MovieWithGenreRepositoryCustom;
import Movie.MovieCommunity.JPARepository.dao.MovieWithGenreCountDao;
import Movie.MovieCommunity.JPARepository.dao.MovieWithGenreDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MovieWithGenreService {
    private final MovieWithGenreRepositoryCustom movieWithGenreRepository;
    private final GenreRepository genreRepository;

    public List<MovieWithGenreCountDto> genreCount() {
        List<MovieWithGenreCountDao> list = movieWithGenreRepository.findTop100MovieWithGenre();

        Map<String, Integer> map = new HashMap<>();
        for (MovieWithGenreCountDao dao : list) {
            if (map.containsKey(dao.getGenreNm()))
                map.put(dao.getGenreNm(), map.get(dao.getGenreNm()) + 1);
            else
                map.put(dao.getGenreNm(), 1);
        }

        List<MovieWithGenreCountDto> result = new ArrayList<>();
        map.forEach((key, value) -> {
            result.add(new MovieWithGenreCountDto(key, value));
        });

        for (MovieWithGenreCountDto movieWithGenreCountDto : result) {
            Genre jpaGenre = genreRepository.findByGenreNm(movieWithGenreCountDto.getGenreNm()).get();
            movieWithGenreCountDto.setGenreId(jpaGenre.getId());
        }
        return result;
    }


}
