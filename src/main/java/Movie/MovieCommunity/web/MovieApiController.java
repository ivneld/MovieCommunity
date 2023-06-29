package Movie.MovieCommunity.web;

import Movie.MovieCommunity.config.security.token.CurrentUser;
import Movie.MovieCommunity.config.security.token.UserPrincipal;
import Movie.MovieCommunity.service.MovieService;
import Movie.MovieCommunity.util.CustomPageImpl;
import Movie.MovieCommunity.util.CustomPageRequest;
import Movie.MovieCommunity.web.apiDto.movie.entityDto.MovieDetailSearchDto;
import Movie.MovieCommunity.web.apiDto.movie.response.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        List<YearRankingResponse> yearRankingResponses = null;
        if (member != null){
             yearRankingResponses = movieService.yearRanking(openDt, member.getId());
        }else{
             yearRankingResponses = movieService.yearRanking(openDt, null);
        }
        return new ResponseEntity(yearRankingResponses, HttpStatus.OK);
    }
    @Operation(method = "get", summary = "영화 상세 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "영화 상세 조회 성공", content={@Content(mediaType = MediaType.APPLICATION_JSON_VALUE ,schema = @Schema(implementation = MovieDetailResponse.class))})
    })
    @GetMapping("/{movieId}")
    public ResponseEntity<?> detail(@PathVariable(name="movieId") Long movieId, @CurrentUser UserPrincipal member){
        MovieDetailResponse movieDetailResponse = null;
        if (member != null){
            movieDetailResponse= movieService.movieDetail(movieId, member.getId());}
        else{
            movieDetailResponse= movieService.movieDetail(movieId, null);
        }
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

    @Operation(method = "get", summary = "영화 검색")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "영화 검색 성공", useReturnTypeSchema = true)
    })
    @GetMapping("/search")
    public ResponseEntity<List<MovieSearchResponse>> search(@RequestParam String movieNm){
        List<MovieSearchResponse> response = movieService.movieSearch(movieNm);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(method = "get", summary = "상세 검색")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "상세 검색 성공", useReturnTypeSchema = true)
    })
    @GetMapping("/search/detail")
    public ResponseEntity<SearchDetailResponse> detailSearch(@RequestParam String search){
        SearchDetailResponse response = movieService.detailSearch(search);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(method = "get", summary = "영화 상세 검색")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "영화 상세 검색 성공", useReturnTypeSchema = true)
    })
    @GetMapping("/search/moviedetail")
    public ResponseEntity<CustomPageImpl<MovieDetailSearchDto>> movieDetailSearch(CustomPageRequest pageRequest, @RequestParam String search){
        PageRequest of = pageRequest.of("popularity");
        Pageable pageable = (Pageable) of;
        CustomPageImpl<MovieDetailSearchDto> response = movieService.movieDetailSearch(search, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(method = "get", summary = "개봉예정 영화 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "개봉예정 영화 조회 성공", useReturnTypeSchema = true)
    })
    @GetMapping("/coming")
    public ResponseEntity<CustomPageImpl<ComingMovieResponse>> findComingMovie(CustomPageRequest pageRequest){
        PageRequest of = pageRequest.of(Sort.Direction.ASC, "openDt");
        Pageable pageable = (Pageable) of;
        CustomPageImpl<ComingMovieResponse> response = movieService.comingMovie(pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
