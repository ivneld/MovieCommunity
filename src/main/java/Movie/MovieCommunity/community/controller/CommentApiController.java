package Movie.MovieCommunity.community.controller;


import Movie.MovieCommunity.community.service.CommunityCommentService;
import Movie.MovieCommunity.community.dto.CommentDto;
import Movie.MovieCommunity.community.dto.UserDto;

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
public class CommentApiController {

    private final CommunityCommentService commentService;

    /* CREATE */
    @PostMapping("/posts/{id}/comments")
    public ResponseEntity<Long> save(@PathVariable Long id, @RequestBody CommentDto.Request dto,
                                UserDto.Response userSessionDto) {
        return ResponseEntity.ok(commentService.save(id, userSessionDto.getNickname(), dto));
    }

    /* READ */
    @GetMapping("/posts/{id}/comments")
    public List<CommentDto.Response> read(@PathVariable Long id) {
        return commentService.findAll(id);
    }

    /* UPDATE */
    @PutMapping({"/posts/{postsId}/comments/{id}"})
    public ResponseEntity<Long> update(@PathVariable Long postsId, @PathVariable Long id, @RequestBody CommentDto.Request dto) {
        commentService.update(postsId, id, dto);
        return ResponseEntity.ok(id);
    }

    /* DELETE */
    @DeleteMapping("/posts/{postsId}/comments/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long postsId, @PathVariable Long id) {
        commentService.delete(postsId, id);
        return ResponseEntity.ok(id);
    }
}
