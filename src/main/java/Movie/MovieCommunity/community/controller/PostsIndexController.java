package Movie.MovieCommunity.community.controller;


import Movie.MovieCommunity.JPADomain.Member;
import Movie.MovieCommunity.JPARepository.MemberRepository;
import Movie.MovieCommunity.community.dto.*;
import Movie.MovieCommunity.community.repository.PostsRepository;
import Movie.MovieCommunity.community.service.PostsService;

import Movie.MovieCommunity.community.domain.Posts;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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
    private final PostsRepository postsRepository;
    private final MemberRepository memberRepository;

    @Operation(method = "get", summary = "커뮤니티 목록 페이지/ 이전, 이후 기능 /이후 하면 사이즈 개수의 게시물로 변경")
    @GetMapping("/posts")                 /* default page = 0, size = 10  */
    public ListDto index(@PageableDefault(sort = "id", direction = Sort.Direction.DESC)
                               Pageable pageable, UserDto.Response user) {

        Page<Posts> list = postsRepository.findAll(pageable);

        ListDto dto = new ListDto(list,pageable.previousOrFirst().getPageNumber(),pageable.next().getPageNumber(),list.hasNext(),list.hasPrevious());
        if (user != null) {
            dto.setUser(user);
        }

        return dto;
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
    public PostDetailDto read(@PathVariable Long id, UserDto.Response user, Model model) {
        Posts posts = postsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id: " + id));

        PostDetailDto postDetailDto = new PostDetailDto();
        PostsDto.Response dto=new PostsDto.Response(posts);

        List<CommentDto.Response> comments = dto.getComments();


        /* 댓글 관련 */
        if (comments != null && !comments.isEmpty()) {
            postDetailDto.setComments(comments);
        }

        /* 사용자 관련 */
        if (user != null) {
            postDetailDto.setUser(user);

            /* 게시글 작성자 본인인지 확인 */
            postDetailDto.setIsPostWriter(dto.getUserId().equals(user.getId()));
        }

        List<Integer> IsCommentWriter = new ArrayList<>();

        /* 댓글 작성자 본인인지 확인 */
        for (int i = 0; i < comments.size(); i++) {
            boolean isWriter = comments.get(i).getUserId().equals(user.getId());
            if(isWriter==true){
                IsCommentWriter.add(i);
            }
        }
        postDetailDto.setIsCommentWriter(IsCommentWriter);

        postsService.updateView(id); // views ++
        postDetailDto.setPost( dto);

        return postDetailDto;
    }

    @Operation(method = "get", summary = "커뮤니티 게시글 수정 페이지")
    @GetMapping("/posts/update/{id}")
    public UpdatePageDto update(@PathVariable Long id,  UserDto.Response user, Model model) {
        UpdatePageDto updatePageDto = new UpdatePageDto();
        PostsDto.Response dto = postsService.findById(id);
        if (user != null) {
            updatePageDto.setUser(user);
        }
        updatePageDto.setPosts(dto);

        return updatePageDto;
    }

    @GetMapping("/posts/search")
    @Operation(method = "get", summary = "게시글 검색 페이지")
    public SearchPageDto search(String keyword, Model model, @PageableDefault(sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable,  UserDto.Response user) {

        SearchPageDto searchPageDto= new SearchPageDto();
        Page<Posts> searchList = postsService.search(keyword, pageable);

        if (user != null) {
            searchPageDto.setUser(user);
        }
        searchPageDto.setSearchList(searchList);
        searchPageDto.setKeyword(keyword);
        searchPageDto.setPreviousPageNumber(pageable.previousOrFirst().getPageNumber());
        searchPageDto.setNextPageNumber(pageable.next().getPageNumber());
        searchPageDto.setHasNextPage(searchList.hasNext());
        searchPageDto.setHasPreviousPage(searchList.hasPrevious());

        return searchPageDto;
    }
}

