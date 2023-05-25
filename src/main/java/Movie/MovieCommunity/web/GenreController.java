package Movie.MovieCommunity.web;

import Movie.MovieCommunity.JPADomain.dto.MovieWithGenreCountDto;
import Movie.MovieCommunity.JPARepository.MovieWithGenreRepositoryCustom;
import Movie.MovieCommunity.JPARepository.dao.MovieWithGenreDao;
import Movie.MovieCommunity.service.MovieWithGenreService;
import Movie.MovieCommunity.web.apiDto.movie.YearRankingResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "genre", description = "영화 장르 api")
@RestController
@RequestMapping("/genre")
@RequiredArgsConstructor
public class GenreController {

    private final MovieWithGenreService movieWithGenreService;
    private final MovieWithGenreRepositoryCustom movieWithGenreRepositoryCustom;

    @Operation(method = "get", summary = "popularity 기준 Top100 영화들의 장르별 개수")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "장르별 개수 조회 성공", content={@Content(mediaType = "application/json",schema = @Schema(implementation = MovieWithGenreCountDto.class))})
    })
    @GetMapping
    public List<MovieWithGenreCountDto> responseGenreCount() {
        return movieWithGenreService.genreCount();
    }

    @Operation(method = "get", summary = "장르별 영화 목록 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "장르별 영화 조회 성공", content={@Content(mediaType = "application/json",schema = @Schema(implementation = MovieWithGenreDao.class))})
    })
    @GetMapping("/{genre_id}")
    public List<MovieWithGenreDao> findMovieByGenreId(@PathVariable("genre_id") Long genreId) {
        return movieWithGenreRepositoryCustom.findMovieByGenre(genreId);
    }
}
