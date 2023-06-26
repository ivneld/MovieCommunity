package Movie.MovieCommunity.community.controller;


import Movie.MovieCommunity.community.service.PostsService;
import Movie.MovieCommunity.community.dto.CommentDto;
import Movie.MovieCommunity.community.dto.PostsDto;
import Movie.MovieCommunity.community.dto.UserDto;

import Movie.MovieCommunity.community.domain.Posts;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 화면 연결 Controller
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@Tag(name="community page", description = "커뮤니티 페이지 API")
public class PostsIndexController {

    private final PostsService postsService;

    @Operation(method = "get", summary = "커뮤니티 목록 페이지 뒤로 하면 10개 게시물 추가")
    @GetMapping("/posts")                 /* default page = 0, size = 10  */
    public JSONArray index(Model model, @PageableDefault(sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable,  UserDto.Response user) {
        Page<Posts> list = postsService.pageList(pageable);

        if (user != null) {
            model.addAttribute("user", user);
        }

        model.addAttribute("posts", list);
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("hasNext", list.hasNext());
        model.addAttribute("hasPrev", list.hasPrevious());
        JSONArray jsonArray = (JSONArray) model;

        return jsonArray;
    }
    /* 글 작성 */
    @Operation(method = "get", summary = "커뮤니티 게시글 작성 페이지")
    @GetMapping("/posts/write")
    public String write( UserDto.Response user, Model model) {
        if (user != null) {
            model.addAttribute("user", user);
        }
        return "posts/posts-write";
    }

    /* 글 상세보기 */
    @Operation(method = "get", summary = "커뮤니티 게시글 1개 상세보기 페이지")
    @GetMapping("/posts/read/{id}")
    public JSONArray read(@PathVariable Long id, UserDto.Response user, Model model) {
        PostsDto.Response dto = postsService.findById(id);
        List<CommentDto.Response> comments = dto.getComments();


        /* 댓글 관련 */
        if (comments != null && !comments.isEmpty()) {
            model.addAttribute("comments", comments);
        }

        /* 사용자 관련 */
        if (user != null) {
            model.addAttribute("user", user);

            /* 게시글 작성자 본인인지 확인 */
            if (dto.getUserId().equals(user.getId())) {
                model.addAttribute("writer", true);
            }

            /* 댓글 작성자 본인인지 확인 */
            if (comments.stream().anyMatch(s -> s.getUserId().equals(user.getId()))) {
                model.addAttribute("isWriter", true);
            }
/*            for (int i = 0; i < comments.size(); i++) {
                boolean isWriter = comments.get(i).getUserId().equals(user.getId());
                model.addAttribute("isWriter",isWriter);
            }*/
        }

        postsService.updateView(id); // views ++
        model.addAttribute("posts", dto);
        JSONArray jsonArray = (JSONArray) model;

        return jsonArray;
    }

    @Operation(method = "get", summary = "커뮤니티 게시글 수정 페이지")
    @GetMapping("/posts/update/{id}")
    public JSONArray update(@PathVariable Long id,  UserDto.Response user, Model model) {
        PostsDto.Response dto = postsService.findById(id);
        if (user != null) {
            model.addAttribute("user", user);
        }
        model.addAttribute("posts", dto);
        JSONArray jsonArray = (JSONArray) model;

        return jsonArray;
    }

    @GetMapping("/posts/search")
    @Operation(method = "get", summary = "게시글 검색 페이지")
    public JSONArray search(String keyword, Model model, @PageableDefault(sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable,  UserDto.Response user) {
        Page<Posts> searchList = postsService.search(keyword, pageable);

        if (user != null) {
            model.addAttribute("user", user);
        }
        model.addAttribute("searchList", searchList);
        model.addAttribute("keyword", keyword);
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("hasNext", searchList.hasNext());
        model.addAttribute("hasPrev", searchList.hasPrevious());

        JSONArray jsonArray = (JSONArray) model;

        return jsonArray;
    }
}

