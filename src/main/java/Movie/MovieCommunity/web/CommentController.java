package Movie.MovieCommunity.web;

import Movie.MovieCommunity.annotation.CurrentMember;
import Movie.MovieCommunity.config.security.token.CurrentUser;
import Movie.MovieCommunity.config.security.token.UserPrincipal;
import Movie.MovieCommunity.service.CommentService;
import Movie.MovieCommunity.web.apiDto.comment.CommentAPIRequest;
import Movie.MovieCommunity.web.apiDto.comment.CommentDeleteAPIRequest;
import Movie.MovieCommunity.web.apiDto.comment.CommentLikeApiRequest;
import Movie.MovieCommunity.web.apiDto.comment.CommentUpdateAPIRequest;
import Movie.MovieCommunity.web.form.CommentForm;
import Movie.MovieCommunity.web.response.CommentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * movieWithgenre table
 * movie : movie_id ,movie_nm, open_dt
 * genre : genre_id, genre_nm
 */
@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("http://localhost:3000")
@Tag(name="comment", description = "댓글 API")
public class CommentController {
    private final CommentService commentService;

    // movie id need
    @Operation(method = "get", summary = "더보기 누르기 전 8개 댓글만")
    @ApiResponses(value=
    @ApiResponse(responseCode = "200", description = "댓글 조회 성공", content={@Content(mediaType = "application/json")})
    )
    @GetMapping("/{movie_id}")
    public List<CommentResponse> top8Comments(@PathVariable("movie_id") Long movieId, @CurrentUser UserPrincipal member) {
        return commentService.top8CommentList(movieId, member.getId());
    }

    @Operation(method = "get", summary = "더보기 누른 후 모든 댓글")
    @ApiResponses(value=
    @ApiResponse(responseCode = "200", description = "댓글 조회 성공", content={@Content(mediaType = "application/json")})
    )
    @GetMapping("/more/{movie_id}")
    public List<CommentResponse> allComments(@PathVariable("movie_id") Long movieId, @CurrentUser UserPrincipal member) {
        return commentService.commentList(movieId, member.getId());
    }

//    @Operation(method = "get", summary = "댓글 좋아요 버튼 클릭")
//    @ApiResponses(value=
//    @ApiResponse(responseCode = "202", description = "댓글 좋아요 성공 처리 후 좋아요 수 반환", content={@Content(mediaType = "application/json")})
//    )
//    @GetMapping("/like/{comment_id}")
//    @ResponseStatus(HttpStatus.ACCEPTED)
//    public Integer like(@PathVariable("comment_id") Long commentId, @CurrentUser UserPrincipal member) {
//        return commentService.plusLike(commentId, member.getId());
//    }


    @Operation(method = "put", summary = "댓글 좋아요 버튼 클릭")
    @ApiResponses(value =
    @ApiResponse(responseCode = "202", description = "댓글 좋아요 수를 받아서 DB수정 후 반환", content = {@Content(mediaType = "application/json")})
    )
    @PutMapping("/like/update")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Integer like(@Valid @RequestBody CommentLikeApiRequest commentLikeApiRequest, @CurrentUser UserPrincipal member) {
        return commentService.updateLike(commentLikeApiRequest, member.getId());
    }

    @Operation(method = "post", summary = "댓글 생성")
    @ApiResponses(value=
        @ApiResponse(responseCode = "201", description = "댓글 생성 성공", content={@Content(mediaType = "application/json")})
    )
    @PostMapping("new")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@Valid @RequestBody CommentAPIRequest commentRequest, @CurrentUser UserPrincipal member){
//        String email = userPrincipal.getEmail();
        CommentForm response = commentService.write(commentRequest, member.getId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @Operation(method="put", summary = "댓글 수정")
    @ApiResponses(value=
        @ApiResponse(responseCode = "200", description = "댓글 수정 성공", content={@Content(mediaType = "application/json")})
    )
    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody CommentUpdateAPIRequest commentUpdateAPIRequest, @CurrentUser UserPrincipal member){
        Boolean checkUpdate = commentService.update(commentUpdateAPIRequest, member.getId());
        if (!checkUpdate){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(method="delete", summary = "댓글 삭제")
    @ApiResponses(value=
        @ApiResponse(responseCode = "200", description = "댓글 삭제 성공", content = {@Content(mediaType = "application/json")})
    )
    @DeleteMapping
    public ResponseEntity<?> delete(@Valid @RequestBody CommentDeleteAPIRequest commentDeleteAPIRequest, @CurrentUser UserPrincipal member){
        commentService.delete(commentDeleteAPIRequest, member.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}






















