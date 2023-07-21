package Movie.MovieCommunity.community.controller;


import Movie.MovieCommunity.JPADomain.Member;
import Movie.MovieCommunity.JPARepository.MemberRepository;
import Movie.MovieCommunity.community.dto.*;
import Movie.MovieCommunity.community.repository.PostsRepository;
import Movie.MovieCommunity.community.service.PostsService;

import Movie.MovieCommunity.community.domain.Posts;
import Movie.MovieCommunity.config.security.token.CurrentUser;
import Movie.MovieCommunity.config.security.token.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

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

    @Operation(method = "get", summary = "상세페이지 커뮤니티 리뷰 가져오기")
    @GetMapping("/postByMovie/{movieId}")
    public List<DetailPageDto> read(@PathVariable("movieId") Long movieId) {
        List<DetailPageDto> detailPageDtos= new ArrayList<>();
        Optional<List<Posts>> byMovie = postsRepository.findByMovieId(movieId);
        if(byMovie.isPresent()){
            List<Posts> posts = byMovie.get();
            for (Posts post : posts) {
                DetailPageDto dto = new DetailPageDto();
                dto.setNickname(post.getUser().getNickname());
                dto.setTitle(post.getTitle());
                dto.setContent(post.getContent());
                dto.setGallery(post.getGalleries());
                dto.setLikeCount(post.getLikeCount());
                dto.setCommentCount(post.getComments().size());
                detailPageDtos.add(dto);
            }
        }
        return detailPageDtos;
    }


    @Operation(method = "get", summary = "마이페이지 리뷰 검색 API")
    @GetMapping("/postByMember/nickname")
    public List<MyPagePostsDto> read(@CurrentUser UserPrincipal member) {

        String nickname = member.getNickName();
        List<MyPagePostsDto> MyPagePostsDto= new ArrayList<>();
        Optional<List<Posts>> byMember = postsRepository.findByWriter(nickname);
        if(byMember.isPresent()){
            List<Posts> posts = byMember.get();
            for (Posts post : posts) {
                MyPagePostsDto dto = new MyPagePostsDto();
                dto.setTitle(post.getTitle());
                dto.setContent(post.getContent());
                dto.setGallery(post.getGalleries());
                dto.setModifiedAt(post.getModifiedDate());
                MyPagePostsDto.add(dto);
            }
        }
        return MyPagePostsDto;
    }

    @Operation(method = "get", summary = "좋아요 많은순 게시글 가져오기")
    @GetMapping("/posts/like")                 /* default page = 0, size = 10  */
    public ListDto indexByLike(@PageableDefault(sort="likeCount",direction = Sort.Direction.DESC)
                              Pageable pageable) {

        Page<Posts> list = postsRepository.findAll(pageable);

        ListDto dto = new ListDto(list,pageable.previousOrFirst().getPageNumber(),pageable.next().getPageNumber(),list.hasNext(),list.hasPrevious());
        /**
         if (user != null) {
         dto.setUser(user);
         }
         */
        return dto;
    }




    @Operation(method = "get", summary = "최신순 게시글 가져오기")
    @GetMapping("/posts/new")                 /* default page = 0, size = 10  */
    public ListDto indexByNew(@PageableDefault(sort="id",direction = Sort.Direction.DESC)
                               Pageable pageable) {

        Page<Posts> list = postsRepository.findAll(pageable);

        ListDto dto = new ListDto(list,pageable.previousOrFirst().getPageNumber(),pageable.next().getPageNumber(),list.hasNext(),list.hasPrevious());
        /**
        if (user != null) {
            dto.setUser(user);
        }
         */
        return dto;
    }

    @Operation(method = "get", summary = "조회수 많은순 게시글 가져오기")
    @GetMapping("/posts/view")                 /* default page = 0, size = 10  */
    public ListDto indexByView(@PageableDefault(sort="view",direction = Sort.Direction.DESC)
                         Pageable pageable) {

        Page<Posts> list = postsRepository.findAll(pageable);

        ListDto dto = new ListDto(list,pageable.previousOrFirst().getPageNumber(),pageable.next().getPageNumber(),list.hasNext(),list.hasPrevious());
        /**
         if (user != null) {
         dto.setUser(user);
         }
         */
        return dto;
    }

    /* 글 작성 */
    @Operation(method = "get", summary = "커뮤니티 게시글 작성 페이지")
    @GetMapping("/posts/write")
    public UserDto.Response write( @CurrentUser UserPrincipal member) {

        Member user= memberRepository.findById(member.getId()).orElseThrow(() ->
                new IllegalArgumentException("유저 정보가 없습니다."));
        UserDto.Response writer= new UserDto.Response(user.getId(), user.getUsername());

        return writer;
    }

    /* 글 상세보기 */
    @Operation(method = "get", summary = "커뮤니티 게시글 1개 상세보기 페이지")
    @GetMapping("/posts/read/{postId}")
    public PostDetailDto read(@PathVariable Long postId,@CurrentUser UserPrincipal member) {
        Posts posts = postsRepository.findById(postId).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id: " + postId));


        PostDetailDto postDetailDto = new PostDetailDto();
        PostsDto.Response dto=new PostsDto.Response(posts);

        List<CommentDto.Response> comments = dto.getComments();


        /* 댓글 관련 */
        if (comments != null && !comments.isEmpty()) {
            postDetailDto.setComments(comments);
        }

        /* 사용자 관련 */
        if (member.getId() != null) {

            UserDto.Response user = new UserDto.Response(member.getId(),member.getNickName());
            postDetailDto.setUser(user);

            /* 게시글 작성자 본인인지 확인 */
            postDetailDto.setIsPostWriter(dto.getUserId().equals(user.getId()));
            postDetailDto.setIsHeartWriter(dto.getUserId().equals(user.getId()));
        }


        if (member.getId() != null) {
            List<Integer> IsCommentWriter = new ArrayList<>();
            /* 댓글 작성자 본인인지 확인 */
            for (int i = 0; i < comments.size(); i++) {
                boolean isWriter = comments.get(i).getUserId().equals(member.getId());
                if (isWriter == true) {
                    IsCommentWriter.add(i);
                }
            }
            postDetailDto.setIsCommentWriter(IsCommentWriter);
        }

        postsService.updateView(postId); // views ++
        postDetailDto.setPost( dto);

        return postDetailDto;
    }


    @Operation(method = "get", summary = "커뮤니티 게시글 수정 페이지")
    @GetMapping("/posts/update/{postId}")
    public UpdatePageDto update(@PathVariable Long postId,  @CurrentUser UserPrincipal member, Model model) {

        UpdatePageDto updatePageDto = new UpdatePageDto();
        PostsDto.Response dto = postsService.findById(postId);
        if (member.getId() != null) {
            UserDto.Response user = new UserDto.Response(member.getId(), member.getUsername());
            updatePageDto.setUser(user);
        }
        updatePageDto.setPosts(dto);

        return updatePageDto;
    }

    @GetMapping("/posts/search")
    @Operation(method = "get", summary = "게시글 검색 페이지")
    public SearchPageDto search(String keyword, @PageableDefault(sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable) {

        SearchPageDto searchPageDto= new SearchPageDto();
        Page<Posts> searchList = postsService.search(keyword, pageable);

        searchPageDto.setSearchList(searchList);
        searchPageDto.setKeyword(keyword);
        searchPageDto.setPreviousPageNumber(pageable.previousOrFirst().getPageNumber());
        searchPageDto.setNextPageNumber(pageable.next().getPageNumber());
        searchPageDto.setHasNextPage(searchList.hasNext());
        searchPageDto.setHasPreviousPage(searchList.hasPrevious());

        return searchPageDto;
    }
}

