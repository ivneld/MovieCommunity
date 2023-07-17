package Movie.MovieCommunity.community.controller;

import Movie.MovieCommunity.community.dto.HeartRequestDTO;
import Movie.MovieCommunity.community.dto.LikeRequestDto;
import Movie.MovieCommunity.community.response.ResponseDto;
import Movie.MovieCommunity.community.service.HeartService;
import Movie.MovieCommunity.config.security.token.CurrentUser;
import Movie.MovieCommunity.config.security.token.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/heart")
@Tag(name="community heart", description = "커뮤니티 게시글 좋아요 API")
public class CommunityLikeController {

    private final HeartService heartService;

    @Operation(method = "post", summary = "게시글 좋아요 추가")
    @PostMapping("/{postId}")
    public ResponseEntity insert(@PathVariable Long postId, @CurrentUser UserPrincipal member) throws Exception {
        HeartRequestDTO heartRequestDTO = new HeartRequestDTO(member.getId(),postId);
        heartService.insert(heartRequestDTO);
        return ResponseEntity.ok(null);
    }

    @Operation(method = "put", summary = "게시글 좋아요 삭제")
    @DeleteMapping("/{postId}")
    public ResponseEntity delete(@PathVariable Long postId, @CurrentUser UserPrincipal member) {
        HeartRequestDTO heartRequestDTO = new HeartRequestDTO(member.getId(),postId);
        heartService.delete(heartRequestDTO);
        return ResponseEntity.ok(null);
    }

    /**
     * 댓글, 대댓글 좋아요 컨트롤러
     * */

    @Operation(method = "post", summary = "게시글 댓글 좋아요 추가")
    @RequestMapping(value = "/api/auth/comment/like", method = RequestMethod.POST)
    public ResponseDto<?> doCommentLike(@RequestBody LikeRequestDto requestDto,  @CurrentUser UserPrincipal member) {
        return heartService.doCommentLike(requestDto, member);
    }

    @Operation(method = "delete", summary = "게시글 댓글 좋아요 삭제")
    @RequestMapping(value = "/api/auth/comment/like/{commentId}", method = RequestMethod.DELETE)
    public ResponseDto<?> cancelCommentLike(@PathVariable Long commentId,  @CurrentUser UserPrincipal member) {
        return heartService.cancelCommentLike(commentId, member);
    }

    @Operation(method = "post", summary = "게시글 대댓글 좋아요 추가")
    @RequestMapping(value = "/api/auth/subcomment/like", method = RequestMethod.POST)
    public ResponseDto<?> subCommentLike(@RequestBody LikeRequestDto requestDto, @CurrentUser UserPrincipal member) {
        return heartService.subCommentLike(requestDto, member);
    }

    @Operation(method = "delete", summary = "게시글 대댓글 좋아요 삭제")
    @RequestMapping(value = "/api/auth/subcomment/like/{subCommentid}", method = RequestMethod.DELETE)
    public ResponseDto<?> subCommentLike(@PathVariable Long subCommentid, @CurrentUser UserPrincipal member) {
        return heartService.cancelSubCommentLike(subCommentid, member);
    }

}