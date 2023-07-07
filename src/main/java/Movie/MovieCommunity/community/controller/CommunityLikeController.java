package Movie.MovieCommunity.community.controller;

import Movie.MovieCommunity.community.dto.HeartRequestDTO;
import Movie.MovieCommunity.community.dto.LikeRequestDto;
import Movie.MovieCommunity.community.response.ResponseDto;
import Movie.MovieCommunity.community.service.HeartService;
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
    @PostMapping
    public ResponseEntity insert(@RequestBody @Valid HeartRequestDTO heartRequestDTO) throws Exception {
        heartService.insert(heartRequestDTO);
        return ResponseEntity.ok(null);
    }

    @Operation(method = "put", summary = "게시글 좋아요 삭제")
    @DeleteMapping
    public ResponseEntity delete(@RequestBody @Valid HeartRequestDTO heartRequestDTO) {
        heartService.delete(heartRequestDTO);
        return ResponseEntity.ok(null);
    }

    /**
     * 댓글, 대댓글 좋아요 컨트롤러
     * */

    @Operation(method = "post", summary = "게시글 댓글 좋아요 추가")
    @RequestMapping(value = "/api/auth/comment/like", method = RequestMethod.POST)
    public ResponseDto<?> doCommentLike(@RequestBody LikeRequestDto requestDto, HttpServletRequest request) {
        return heartService.doCommentLike(requestDto, request);
    }

    @Operation(method = "delete", summary = "게시글 댓글 좋아요 삭제")
    @RequestMapping(value = "/api/auth/comment/like/{id}", method = RequestMethod.DELETE)
    public ResponseDto<?> cancelCommentLike(@PathVariable Long id, HttpServletRequest request) {
        return heartService.cancelCommentLike(id, request);
    }

    @Operation(method = "post", summary = "게시글 대댓글 좋아요 추가")
    @RequestMapping(value = "/api/auth/subcomment/like", method = RequestMethod.POST)
    public ResponseDto<?> subCommentLike(@RequestBody LikeRequestDto requestDto, HttpServletRequest request) {
        return heartService.subCommentLike(requestDto, request);
    }

    @Operation(method = "delete", summary = "게시글 대댓글 좋아요 삭제")
    @RequestMapping(value = "/api/auth/subcomment/like/{id}", method = RequestMethod.DELETE)
    public ResponseDto<?> subCommentLike(@PathVariable Long id, HttpServletRequest request) {
        return heartService.cancelSubCommentLike(id, request);
    }

}