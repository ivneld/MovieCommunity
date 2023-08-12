package Movie.MovieCommunity.web;

import Movie.MovieCommunity.config.security.token.CurrentUser;
import Movie.MovieCommunity.config.security.token.UserPrincipal;
import Movie.MovieCommunity.service.MovieService;
import Movie.MovieCommunity.web.apiDto.movie.response.*;
import Movie.MovieCommunity.util.CustomPageImpl;
import Movie.MovieCommunity.util.CustomPageRequest;
import Movie.MovieCommunity.web.apiDto.movie.entityDto.MovieDetailSearchDto;
import Movie.MovieCommunity.web.dto.WeeklyTestDto;
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
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Tag(name="movie", description="영화 api")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/movie")
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

    @Operation(method = "get", summary = "이번주 영화 랭킹")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "오늘 날짜 기준으로 주간 랭킹 조회", content={@Content(mediaType = MediaType.APPLICATION_JSON_VALUE ,schema = @Schema(implementation = YearRankingResponse.class))})
    })
    @GetMapping("/weekly")
    public List<WeeklyResponse> weeklyRankingThisWeek(@CurrentUser UserPrincipal member) {
        LocalDate date = LocalDate.now();
//        LocalDate date1 = LocalDate.of(2023, 5, 1);
        return movieService.weeklyRankingByDate(date, member.getId());
    }

    @Operation(method = "post", summary = "이번주 영화 랭킹 테스트")
    @ApiResponse(responseCode = "200", description = "원하는 날짜의 주간 랭킹으로 테스트", content = {@Content(mediaType = "application/json")})
    @PostMapping("/weeklytest")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<WeeklyResponse> weeklyRankingThisWeekTest(@Valid @RequestBody WeeklyTestDto dto, @CurrentUser UserPrincipal member) {
        LocalDate date = LocalDate.of(dto.getYear(), dto.getMonth(), dto.getDay());
        return movieService.weeklyRankingByDate(date, member.getId());
    }

    @Operation(method = "post", summary = "이번주 영화 랭킹 테스트 (4번 맴버로 테스트) 토큰 X")
    @ApiResponse(responseCode = "200", description = "원하는 날짜의 주간 랭킹으로 테스트", content = {@Content(mediaType = "application/json")})
    @PostMapping("/weeklytest/member4")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<WeeklyResponse> weeklyRankingThisWeekTest1(@Valid @RequestBody WeeklyTestDto dto) {
        LocalDate date = LocalDate.of(dto.getYear(), dto.getMonth(), dto.getDay());
        return movieService.weeklyRankingByDate(date, 4L);
    }

    @Operation(method = "Get", summary = "영화 추천")
    @ApiResponse(responseCode = "200", description = "좋아요 한 영화가 5개 미만일 경우 해당 날짜의 주간랭킹 영화들의 키워드로 추천", content = {@Content(mediaType = "application/json")})
    @GetMapping("/propose")
    public List<ProposeMovieResponse> proposeMovie(@CurrentUser UserPrincipal member) {
        LocalDate date = LocalDate.now();
        return movieService.proposeMovie(date, member.getId());
    }

    @Operation(method = "post", summary = "영화 추천 테스트")
    @ApiResponse(responseCode = "200", description = "좋아요 한 영화가 5개 미만일 경우 해당 날짜의 주간랭킹 영화들의 키워드로 추천, 원하는 날짜로 테스트", content = {@Content(mediaType = "application/json")})
    @PostMapping("/proposetest")
    public List<ProposeMovieResponse> proposeMovieTest(@Valid @RequestBody WeeklyTestDto dto, @CurrentUser UserPrincipal member) {
        LocalDate date = LocalDate.of(dto.getYear(), dto.getMonth(), dto.getDay());
        return movieService.proposeMovie(date, member.getId());
    }

    @Operation(method = "post", summary = "영화 추천 테스트 (like movie 가 5개 이상인 맴버)")
    @ApiResponse(responseCode = "200", description = "member_id = 4 인 회원을 통해 테스트", content = {@Content(mediaType = "application/json")})
    @PostMapping("/proposetest/member4")
    public List<ProposeMovieResponse> proposeMovieTest1(@Valid @RequestBody WeeklyTestDto dto) {
        LocalDate date = LocalDate.of(dto.getYear(), dto.getMonth(), dto.getDay());
        return movieService.proposeMovie(date, 4L);
    }


    @Operation(method = "get", summary = "개봉예정 영화 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "개봉예정 영화 조회 성공", useReturnTypeSchema = true)
    })
    @GetMapping("/coming")
    public ResponseEntity<CustomPageImpl<ComingMovieResponse>> findComingMovie(CustomPageRequest pageRequest) {

        PageRequest of = pageRequest.of(Sort.Direction.ASC, "openDt");
        Pageable pageable = (Pageable) of;
        CustomPageImpl<ComingMovieResponse> response = movieService.comingMovie(pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
