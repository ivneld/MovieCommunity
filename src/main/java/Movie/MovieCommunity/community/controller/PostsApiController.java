package Movie.MovieCommunity.community.controller;


import Movie.MovieCommunity.JPADomain.Member;
import Movie.MovieCommunity.JPADomain.Movie;
import Movie.MovieCommunity.JPADomain.dto.MovieDto;
import Movie.MovieCommunity.JPADomain.dto.TvDto;
import Movie.MovieCommunity.JPARepository.MemberRepository;
import Movie.MovieCommunity.JPARepository.MovieRepository;
import Movie.MovieCommunity.awsS3.domain.entity.GalleryEntity;
import Movie.MovieCommunity.awsS3.domain.repository.GalleryRepository;
import Movie.MovieCommunity.community.domain.Posts;
import Movie.MovieCommunity.community.dto.GalleryDto;
import Movie.MovieCommunity.community.repository.PostsRepository;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST API Controller
 */
@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
@Slf4j
@Tag(name="community post", description = "커뮤니티 게시글 API")
public class PostsApiController {

    private final PostsService postsService;
    private final PostsRepository postsRepository;
    private final GalleryRepository galleryRepository;
    private final MemberRepository userRepository;
    private final MovieRepository movieRepository;

    /* CREATE */
    @Operation(method = "post", summary = "커뮤니티 게시글 생성")
    @Transactional
    @PostMapping("/posts")
    public ResponseEntity save(@RequestBody PostsDto.DetailRequestParam param, UserDto.Response user) {
        PostsDto.Request dto= new PostsDto.Request();


        Movie movie = movieRepository.findById(param.getMovieId()).get();

        dto.setMovie(movie);
        log.info("movie={}",movie);
        dto.setTitle(param.getTitle());
        dto.setContent(param.getContent());
        dto.setWriter(user.getNickname());

        Optional<List<Long>> GalleryId = param.getGalleryId();
        Member member = userRepository.findByNickname(user.getNickname());
        dto.setUser(member);
        Posts posts = dto.toEntity();
        postsRepository.save(posts);


        if(GalleryId.isPresent()) {
            log.info("present");
            List<Long> ids = GalleryId.get();
            List<GalleryEntity> galleryList = new ArrayList<>();

            for (Long id: ids) {
                GalleryEntity gallery = galleryRepository.findById(id).get();
                galleryRepository.updatePosts(id,posts);
                galleryList.add(gallery);
            }
        }

        return ResponseEntity.ok(posts.getId());
    }

    /* READ */
    @Operation(method = "get", summary = "커뮤니티 게시글 조회")
    @GetMapping("/posts/{id}")
    public ResponseEntity read(@PathVariable Long id) {
        Posts posts = postsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id: " + id));
        PostsDto.Response response=new PostsDto.Response(posts);
        return ResponseEntity.ok(response);
    }

    /* UPDATE */
    @Operation(method = "put", summary = "커뮤니티 게시글 수정")
    @PutMapping("/posts/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody PostsDto.RequestParam dto) {
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
