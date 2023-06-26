package Movie.MovieCommunity.community.controller;


import Movie.MovieCommunity.JPADomain.dto.TvDto;
import Movie.MovieCommunity.community.domain.Posts;
import Movie.MovieCommunity.community.service.PostsService;
import Movie.MovieCommunity.community.dto.PostsDto;
import Movie.MovieCommunity.community.dto.UserDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST API Controller
 */
@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
@Tag(name="community post", description = "커뮤니티 게시글 API")
public class PostsApiController {

    private final PostsService postsService;

    /* CREATE */
    @Operation(method = "post", summary = "커뮤니티 게시글 생성")
    @PostMapping("/posts")
    public ResponseEntity save(@RequestBody PostsDto.Request dto,  UserDto.Response user) {
        return ResponseEntity.ok(postsService.save(dto, user.getNickname()));
    }

    /* READ */
    @Operation(method = "get", summary = "커뮤니티 게시글 조회")
    @GetMapping("/posts/{id}")
    public ResponseEntity read(@PathVariable Long id) {
        return ResponseEntity.ok(postsService.findById(id));
    }

    /* UPDATE */
    @Operation(method = "put", summary = "커뮤니티 게시글 수정")
    @PutMapping("/posts/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody PostsDto.Request dto) {
        postsService.update(id, dto);
        return ResponseEntity.ok(id);
    }

    /* DELETE */
    @Operation(method = "delete", summary = "커뮤니티 게시글 삭제")
    @DeleteMapping("/posts/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        postsService.delete(id);
        return ResponseEntity.ok(id);
    }
}
