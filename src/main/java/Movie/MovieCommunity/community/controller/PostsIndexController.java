package Movie.MovieCommunity.community.controller;


import Movie.MovieCommunity.JPADomain.Member;
import Movie.MovieCommunity.JPARepository.MemberRepository;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 화면 연결 Controller
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@Tag(name="community page", description = "커뮤니티 페이지 API")
public class PostsIndexController {

    private final PostsService postsService;
    private final MemberRepository memberRepository;

    @Operation(method = "get", summary = "커뮤니티 목록 페이지/ 이전, 이후 기능 /이후 하면 사이즈 개수의 게시물로 변경")
    @GetMapping("/posts")                 /* default page = 0, size = 10  */
    public ResponseEntity index(@PageableDefault(sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable,  UserDto.Response user) {
          Page<Posts> list = postsService.pageList(pageable);
//
//        JSONArray results = new JSONArray();
//        Map<String, Object> result1 = new HashMap<>();
//        Map<String, Object> result2 = new HashMap<>();
//        Map<String, Object> result3 = new HashMap<>();
//        Map<String, Object> result4 = new HashMap<>();
//        Map<String, Object> result5 = new HashMap<>();
//        Map<String, Object> result6 = new HashMap<>();
//
//        if (user != null) {
//            result1.put("user", user);
//            results.add(result1);
//        }
////
//        results.add(result2.put("posts",list));
//        results.add(result3.put("previous",pageable.previousOrFirst().getPageNumber()));
//        results.add(result4.put("next",pageable.next().getPageNumber()));
//        results.add(result5.put("hasNext",list.hasNext()));
//        results.add(result6.put("hasPrev", list.hasPrevious()));

        return ResponseEntity.ok().body(list);
    }
    /* 글 작성 */
    @Operation(method = "get", summary = "커뮤니티 게시글 작성 페이지")
    @GetMapping("/posts/write")
    public UserDto.Response write( UserDto.Response user) {
        Member member= memberRepository.findById(user.getId()).orElseThrow(() ->
                new IllegalArgumentException("유저 정보가 없습니다."));
        UserDto.Response writer= new UserDto.Response(member.getId(), member.getUsername(), member.getNickname());

        return writer;
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

