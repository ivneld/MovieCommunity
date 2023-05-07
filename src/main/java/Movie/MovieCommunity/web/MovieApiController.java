package Movie.MovieCommunity.web;

import Movie.MovieCommunity.service.MovieService;
import Movie.MovieCommunity.web.apiDto.movie.YearRankingResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MovieApiController {
    private final MovieService movieService;

    @Operation(method = "get", summary = "연간 랭킹 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "연간 랭킹 조회 성공", content={@Content(mediaType = "application/json")})
    })
    @GetMapping("movie/year")
    public ResponseEntity<?> yearRanking(@RequestParam int openDt){
        List<YearRankingResponse> yearRankingResponses = movieService.yearRanking(openDt);
        return new ResponseEntity(yearRankingResponses, HttpStatus.OK);
    }
}
