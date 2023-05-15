package Movie.MovieCommunity.web;

import Movie.MovieCommunity.annotation.CurrentMember;
import Movie.MovieCommunity.config.security.token.UserPrincipal;
import Movie.MovieCommunity.service.MovieService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Tag(name="movie", description="영화 api")
@RequiredArgsConstructor
@RestController
public class MovieApiController {
    private final MovieService movieService;

    @Operation(method = "get", summary = "연간 랭킹 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "연간 랭킹 조회 성공", content={@Content(mediaType = MediaType.APPLICATION_JSON_VALUE ,schema = @Schema(implementation = YearRankingResponse.class))})
    })
    @GetMapping("movie/year")
    public ResponseEntity<?> yearRanking(@Valid @RequestParam int openDt, @CurrentMember Long id){
        //Long id = userPrincipal.getId();
        System.out.println("id = " + id);
        List<YearRankingResponse> yearRankingResponses = movieService.yearRanking(openDt);
        return new ResponseEntity(yearRankingResponses, HttpStatus.OK);
    }
}
