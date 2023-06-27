package Movie.MovieCommunity.community.controller;


import Movie.MovieCommunity.community.service.CommunityCommentService;
import Movie.MovieCommunity.community.dto.CommentDto;
import Movie.MovieCommunity.community.dto.UserDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST API Controller
 */
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
@Tag(name="community comment", description = "커뮤니티 댓글 API")
public class CommentApiController {

    private final CommunityCommentService commentService;

    /* CREATE */
    @Operation(method = "post", summary = "커뮤니티 댓글 생성")
    @PostMapping("/posts/{id}/comments")
    public ResponseEntity<Long> save(@PathVariable Long id, @RequestBody CommentDto.Requestparam requestparam,
                                UserDto.Response userSessionDto) {
        CommentDto.Request dto= new CommentDto.Request();
        dto.setComment(requestparam.getComment());

        return ResponseEntity.ok(commentService.save(id, userSessionDto.getNickname(), dto));
    }

    /* READ */
    @Operation(method = "get", summary = "커뮤니티 댓글 조회")
    @GetMapping("/posts/{id}/comments")
    public List<CommentDto.Response> read(@PathVariable Long id) {
        return commentService.findAll(id);
    }

    /* UPDATE */
    @Operation(method = "put", summary = "커뮤니티 댓글 수정")
    @PutMapping({"/posts/{postsId}/comments/{id}"})
    public ResponseEntity<Long> update(@PathVariable Long postsId, @PathVariable Long id, @RequestBody CommentDto.Requestparam requestparam) {
        CommentDto.Request dto= new CommentDto.Request();
        dto.setComment(requestparam.getComment());
        commentService.update(postsId, id, dto);
        return ResponseEntity.ok(id);
    }

    /* DELETE */
    @Operation(method = "delete", summary = "커뮤니티 댓글 삭제")
    @DeleteMapping("/posts/{postsId}/comments/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long postsId, @PathVariable Long id) {
        commentService.delete(postsId, id);
        return ResponseEntity.ok(id);
    }
}
