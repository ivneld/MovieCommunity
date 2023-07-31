package Movie.MovieCommunity.community.controller;

import Movie.MovieCommunity.community.dto.HeartRequestDto.CommentHeartRequestDTO;
import Movie.MovieCommunity.community.dto.HeartRequestDto.HeartRequestDTO;
import Movie.MovieCommunity.community.dto.HeartRequestDto.SubCommentHeartRequestDTO;
import Movie.MovieCommunity.community.service.HeartService;
import Movie.MovieCommunity.config.security.token.CurrentUser;
import Movie.MovieCommunity.config.security.token.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/heart")
@Tag(name="community heart", description = "커뮤니티 게시글 좋아요 API")
public class CommunityLikeController {

    private final HeartService heartService;

    @Operation(method="get", summary = "게시글 좋아요 여부")
    @GetMapping("/{postId}")
    public ResponseEntity heartCheck(@PathVariable Long postId, @CurrentUser UserPrincipal member) throws Exception {
        if (null == member) {
            throw new NotFoundException("로그인이 필요합니다.");
        }
        boolean check = heartService.check(postId, member.getId());
        return ResponseEntity.ok(check);
    }

    @Operation(method = "post", summary = "게시글 좋아요 추가")
    @PostMapping("/{postId}")
    public ResponseEntity insert(@PathVariable Long postId, @CurrentUser UserPrincipal member) throws Exception {
        HeartRequestDTO heartRequestDTO = new HeartRequestDTO(member.getId(),postId);
        heartService.insert(heartRequestDTO);
        return ResponseEntity.ok(null);
    }

    @Operation(method = "delete", summary = "게시글 좋아요 삭제")
    @DeleteMapping("/{postId}")
    public ResponseEntity delete(@PathVariable Long postId, @CurrentUser UserPrincipal member) {
        HeartRequestDTO heartRequestDTO = new HeartRequestDTO(member.getId(),postId);
        heartService.delete(heartRequestDTO);
        return ResponseEntity.ok(null);
    }

    /**
     * 댓글 좋아요 컨트롤러
     * */
    @Operation(method="get", summary = "댓글 좋아요 여부")
    @GetMapping("/comment/{commentId}")
    public ResponseEntity heartCommentCheck(@PathVariable Long commentId, @CurrentUser UserPrincipal member) throws Exception {
        if (null == member) {
            throw new NotFoundException("로그인이 필요합니다.");
        }
        boolean check = heartService.commentCheck(commentId, member.getId());
        return ResponseEntity.ok(check);
    }

    @Operation(method = "post", summary = "댓글 좋아요 추가")
    @PostMapping("/comment/{commentId}")
    public ResponseEntity commentHeartInsert(@PathVariable Long commentId, @CurrentUser UserPrincipal member) throws Exception {
        CommentHeartRequestDTO heartRequestDTO = new CommentHeartRequestDTO(member.getId(),commentId);
        heartService.CommentHeartInsert(heartRequestDTO);
        return ResponseEntity.ok(null);
    }

    @Operation(method = "delete", summary = "댓글 좋아요 삭제")
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity commentHeartDelete(@PathVariable Long commentId, @CurrentUser UserPrincipal member) {
        CommentHeartRequestDTO heartRequestDTO = new CommentHeartRequestDTO(member.getId(),commentId);
        heartService.CommentHeartDelete(heartRequestDTO);
        return ResponseEntity.ok(null);
    }
    /**
     * 대 댓글 좋아요 컨트롤러
     * */

    @Operation(method="get", summary = "대댓글 좋아요 여부")
    @GetMapping("subComment/{subCommentId}")
    public ResponseEntity SubCommentHeartCheck(@PathVariable Long subCommentId, @CurrentUser UserPrincipal member) throws Exception {
        if (null == member) {
            throw new NotFoundException("로그인이 필요합니다.");
        }
        boolean check = heartService.subCommentLikeCheck(subCommentId, member.getId());
        return ResponseEntity.ok(check);
    }

    @Operation(method = "post", summary = "게시글 좋아요 추가")
    @PostMapping("subComment/{subCommentId}")
    public ResponseEntity SubCommentHeartInsert(@PathVariable Long subCommentId, @CurrentUser UserPrincipal member) throws Exception {
        SubCommentHeartRequestDTO heartRequestDTO = new SubCommentHeartRequestDTO(member.getId(),subCommentId);
        heartService.subcommentLikeInsert(heartRequestDTO);
        return ResponseEntity.ok(null);
    }

    @Operation(method = "delete", summary = "게시글 좋아요 삭제")
    @DeleteMapping("subComment/{subCommentId}")
    public ResponseEntity SubCommentHeartDelete(@PathVariable Long subCommentId, @CurrentUser UserPrincipal member) {
        SubCommentHeartRequestDTO heartRequestDTO = new SubCommentHeartRequestDTO(member.getId(),subCommentId);
        heartService.subCommentLikeDelete(heartRequestDTO);
        return ResponseEntity.ok(null);
    }

}