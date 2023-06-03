package Movie.MovieCommunity.web;

import Movie.MovieCommunity.JPADomain.CreditCategory;
import Movie.MovieCommunity.JPADomain.Member;
import Movie.MovieCommunity.annotation.CurrentMember;
import Movie.MovieCommunity.config.security.token.CurrentUser;
import Movie.MovieCommunity.config.security.token.UserPrincipal;
import Movie.MovieCommunity.service.MovieService;
import Movie.MovieCommunity.util.CalendarUtil;
import Movie.MovieCommunity.web.apiDto.movie.response.MovieDetailResponse;
import Movie.MovieCommunity.web.apiDto.movie.response.WeeklyRankingResponse;
import Movie.MovieCommunity.web.apiDto.movie.response.YearRankingResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Tag(name="movie", description="영화 api")
@RequiredArgsConstructor
@RestController
@RequestMapping("/movie")
public class MovieApiController {
    private final MovieService movieService;

    @Operation(method = "get", summary = "연간 랭킹 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "연간 랭킹 조회 성공", content={@Content(mediaType = MediaType.APPLICATION_JSON_VALUE ,schema = @Schema(implementation = YearRankingResponse.class))})
    })
    @GetMapping("/year")
    public ResponseEntity<?> yearRanking(@Valid @RequestParam int openDt, @CurrentUser UserPrincipal member){
        List<YearRankingResponse> yearRankingResponses = movieService.yearRanking(openDt, member.getId());
        return new ResponseEntity(yearRankingResponses, HttpStatus.OK);
    }
    @Operation(method = "get", summary = "영화 상세 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "영화 상세 조회 성공", content={@Content(mediaType = MediaType.APPLICATION_JSON_VALUE ,schema = @Schema(implementation = MovieDetailResponse.class))})
    })
    @GetMapping("/{movieId}")
    public ResponseEntity<?> detail(@PathVariable(name="movieId") Long movieId, @CurrentUser UserPrincipal member){
        MovieDetailResponse movieDetailResponse= movieService.movieDetail(movieId, member.getId());
        return new ResponseEntity<>(movieDetailResponse, HttpStatus.OK);
    }
    @Operation(method = "get", summary = "영화 관심 등록,삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "영화 관심 등록,삭제 성공", content={@Content(mediaType = MediaType.APPLICATION_JSON_VALUE )})
    })
    @PostMapping("/{movieId}/interest")
    public ResponseEntity<?> interest(@PathVariable(name="movieId") Long movieId, @CurrentUser UserPrincipal member){
        movieService.interest(movieId, member.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
