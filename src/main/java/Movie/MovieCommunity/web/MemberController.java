package Movie.MovieCommunity.web;

import Movie.MovieCommunity.config.security.token.CurrentUser;
import Movie.MovieCommunity.config.security.token.UserPrincipal;
import Movie.MovieCommunity.service.MemberService;
import Movie.MovieCommunity.service.MovieService;
import Movie.MovieCommunity.util.CustomPageImpl;
import Movie.MovieCommunity.util.CustomPageRequest;
import Movie.MovieCommunity.web.apiDto.movie.entityDto.LikeGenreDto;
import Movie.MovieCommunity.web.apiDto.movie.response.MovieLikeGenreResponse;
import Movie.MovieCommunity.web.apiDto.movie.response.MovieLikeResponse;
import Movie.MovieCommunity.web.apiDto.movie.response.YearRankingResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Tag(name="member", description="사용자 api")
@RequiredArgsConstructor
@RestController
@RequestMapping("/mypage")
public class MemberController {
    private final MemberService memberService;
    @Operation(method = "get", summary = "관심 영화 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "관심 영화 조회 성공", useReturnTypeSchema = true)
    })
    @GetMapping("/{memberId}/movie")
    public ResponseEntity<CustomPageImpl<MovieLikeResponse>> findLikeMovie(CustomPageRequest pageRequest,@PathVariable Long memberId , @CurrentUser UserPrincipal member){
        if(memberId != member.getId()){
            throw new RuntimeException("권한이 없습니다.");
        }
        PageRequest of = pageRequest.of("id");
        Pageable pageable = (Pageable) of;
        CustomPageImpl<MovieLikeResponse> response = memberService.findLikeMovie(pageable, memberId);
        return new ResponseEntity(response, HttpStatus.OK);
    }
    @Operation(method = "get", summary = "관심 영화 장르 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "관심 영화 장르 조회 성공", content={@Content(mediaType = MediaType.APPLICATION_JSON_VALUE ,schema = @Schema(implementation = LikeGenreDto.class))})
    })
    @GetMapping("/{memberId}/genre")
    public ResponseEntity<List<LikeGenreDto>> findLikeMovieGenre( @PathVariable Long memberId , @CurrentUser UserPrincipal member){
        if(memberId != member.getId()){
            throw new RuntimeException("권한이 없습니다.");
        }
        List<LikeGenreDto> response = memberService.findLikeMovieGenre(memberId);
        return new ResponseEntity(response, HttpStatus.OK);
    }
}
