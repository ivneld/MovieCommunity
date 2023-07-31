package Movie.MovieCommunity.community.controller;


import Movie.MovieCommunity.JPADomain.Member;
import Movie.MovieCommunity.JPARepository.CommentRepository;
import Movie.MovieCommunity.JPARepository.MemberRepository;
import Movie.MovieCommunity.community.domain.Comment;
import Movie.MovieCommunity.community.domain.SubComment;
import Movie.MovieCommunity.community.dto.*;
import Movie.MovieCommunity.community.dto.util.PageRequestDto;
import Movie.MovieCommunity.community.repository.CommunityCommentRepository;
import Movie.MovieCommunity.community.repository.PostsRepository;
import Movie.MovieCommunity.community.repository.SubCommentRepository;
import Movie.MovieCommunity.community.response.SubCommentResponseDto;
import Movie.MovieCommunity.community.service.HeartService;
import Movie.MovieCommunity.community.service.PostsService;

import Movie.MovieCommunity.community.domain.Posts;
import Movie.MovieCommunity.config.security.token.CurrentUser;
import Movie.MovieCommunity.config.security.token.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.module.FindException;
import java.util.*;

/**
 * 화면 연결 Controller
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Tag(name="community page", description = "커뮤니티 페이지 API")
public class PostsIndexController {

    private final PostsService postsService;
    private final PostsRepository postsRepository;
    private final MemberRepository memberRepository;
    private final HeartService heartService;

    @Operation(method = "get", summary = "상세페이지 커뮤니티 리뷰 가져오기")
    @GetMapping("/postByMovie/{movieId}")
    public DetailPageDto read(@PathVariable("movieId") Long movieId) {
        List<PostsDto.Total> postArray = new ArrayList<>();
        Optional<List<Posts>> byMovie = postsRepository.findByMovieId(movieId);
        Integer postsCount;
        if(byMovie.isPresent()){
            List<Posts> posts = byMovie.get();
            for (Posts post : posts) {
                PostsDto.Total total = new PostsDto.Total(post);
                postArray.add(total);
            }
            postsCount = postsRepository.findByMovieId(movieId).get().size();
        }
        else{
            postsCount = null;
        }
        DetailPageDto detailPageDtos= new DetailPageDto(postArray,postsCount);
        return detailPageDtos;
    }


    @Operation(method = "get", summary = "마이페이지 리뷰 검색 API")
    @GetMapping("/postByMember/nickname")
    public DetailPageDto read(@CurrentUser UserPrincipal member) {

        if(member==null){
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }
        Integer postsCount;
        Long memberId = member.getId();
        Member member1 = memberRepository.findById(memberId).get();
        List<PostsDto.Total> MyPagePostsDto= new ArrayList<>();
        Optional<List<Posts>> byMember = postsRepository.findByUser(member1);
        if(byMember.isPresent()){
            List<Posts> posts = byMember.get();
            for (Posts post : posts) {
                PostsDto.Total dto = new PostsDto.Total(post);
                MyPagePostsDto.add(dto);
            }
            postsCount = postsRepository.findByUser(member1).get().size();
        }
        else{
            postsCount = null;
        }
        DetailPageDto detailPageDtos= new DetailPageDto(MyPagePostsDto,postsCount);
        return detailPageDtos;
    }

    @Operation(method = "get", summary = "좋아요 많은순 게시글 가져오기")
    @GetMapping("/posts/like")                 /* default page = 0, size = 10  */
    public ListDto indexByLike(@RequestBody PageRequestDto requestDto){
        int totalPageCount = postsRepository.findAll().size();
        if (totalPageCount % requestDto.getSize() == 0){
        totalPageCount = totalPageCount /requestDto.getSize();
        }
        else{
        totalPageCount = totalPageCount / requestDto.getSize() +1;
        }
        Pageable pageable = PageRequest.of(requestDto.getPage(), requestDto.getSize(), Sort.Direction.DESC, "likeCount");
        List<PostsDto.Total> postInfo = new ArrayList<>();
        Page<Posts> list = postsRepository.findAll(pageable);
            for (Posts posts : list) {
            PostsDto.Total response=new PostsDto.Total(posts);
            postInfo.add(response);
        }

        ListDto dto = new ListDto(postInfo,totalPageCount,pageable.previousOrFirst().getPageNumber(),pageable.next().getPageNumber(),list.hasNext(),list.hasPrevious());

        return dto;
    }




    @Operation(method = "get", summary = "최신순 게시글 가져오기")
    @GetMapping("/posts/new")                 /* default page = 0, size = 10  */
    public ListDto indexByNew(@RequestBody PageRequestDto requestDto) {
        int totalPageCount = postsRepository.findAll().size();
        if (totalPageCount % requestDto.getSize() == 0){
            totalPageCount = totalPageCount /requestDto.getSize();
        }
        else{
            totalPageCount = totalPageCount / requestDto.getSize() +1;
        }
        Pageable pageable = PageRequest.of(requestDto.getPage(), requestDto.getSize(), Sort.Direction.DESC, "id");
        List<PostsDto.Total> postInfo = new ArrayList<>();
        Page<Posts> list = postsRepository.findAll(pageable);
        for (Posts posts : list) {
            PostsDto.Total response=new PostsDto.Total(posts);
            postInfo.add(response);
        }

        ListDto dto = new ListDto(postInfo,totalPageCount,pageable.previousOrFirst().getPageNumber(),pageable.next().getPageNumber(),list.hasNext(),list.hasPrevious());

        return dto;
    }

    @Operation(method = "get", summary = "조회수 많은순 게시글 가져오기")
    @GetMapping("/posts/view")                 /* default page = 0, size = 10  */
    public ListDto indexByView(@RequestBody PageRequestDto requestDto) {

        int totalPageCount = postsRepository.findAll().size();
        if (totalPageCount % requestDto.getSize() == 0){
            totalPageCount = totalPageCount /requestDto.getSize();
        }
        else{
            totalPageCount = totalPageCount / requestDto.getSize() +1;
        }
        Pageable pageable = PageRequest.of(requestDto.getPage(), requestDto.getSize(), Sort.Direction.DESC, "view");
        List<PostsDto.Total> postInfo = new ArrayList<>();
        Page<Posts> list = postsRepository.findAll(pageable);
        for (Posts posts : list) {
            PostsDto.Total response=new PostsDto.Total(posts);
            postInfo.add(response);
        }

        ListDto dto = new ListDto(postInfo,totalPageCount,pageable.previousOrFirst().getPageNumber(),pageable.next().getPageNumber(),list.hasNext(),list.hasPrevious());

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

        if(member != null) {
            List<JSONArray> IsSubCommentWriter = new ArrayList<>();
            List<JSONArray> IsSubCommentHeartWriter = new ArrayList<>();
            List<Comment> comments1 = posts.getComments();
            for(Integer i = 0; i< comments1.size();i++){
                List<SubComment> subComments = comments1.get(i).getSubComments();
                for (Integer j = 0; j < subComments.size(); j++) {
                    boolean isSubCommentWriter = subComments.get(j).getMember().getId().equals(member.getId());
                    if (isSubCommentWriter == true) {
                        JSONArray map = new JSONArray();
                        map.add(i);
                        map.add(j);
                        IsSubCommentWriter.add(map);
                    }
                    boolean isHeartCommentWriter = heartService.subCommentLikeCheck(subComments.get(j).getId(), member.getId());
                    if(isHeartCommentWriter==true){
                        JSONArray map = new JSONArray();
                        map.add(i);
                        map.add(j);
                        IsSubCommentHeartWriter.add(map);
                    }
                }
            }

            postDetailDto.setIsSubCommentWriter(IsSubCommentWriter);
            postDetailDto.setIsSubCommentHeartWriter(IsSubCommentHeartWriter);
        }
        if(member == null){
            postDetailDto.setIsPostWriter(null);
            postDetailDto.setIsHeartWriter(null);
        }
        /* 사용자 관련 */
        if (member != null) {

            UserDto.Response user = new UserDto.Response(member.getId(),member.getNickName());
            postDetailDto.setUser(user);
            /* 게시글 작성자 본인인지 확인 */
            postDetailDto.setIsPostWriter(dto.getUserId().equals(member.getId()));
            postDetailDto.setIsHeartWriter(heartService.check(postId, member.getId()));
        }


        if (member != null) {
            List<Integer> IsCommentWriter = new ArrayList<>();
            List<Integer> IsCommentHeartWriter = new ArrayList<>();
            /* 댓글 작성자 본인인지 확인 */
            for (int i = 0; i < comments.size(); i++) {
                boolean isWriter = comments.get(i).getUserId().equals(member.getId());
                if (isWriter == true) {
                    IsCommentWriter.add(i);
                }
                boolean isHeartCommentWriter = heartService.commentCheck(comments.get(i).getId(), member.getId());
                if(isHeartCommentWriter==true){
                    IsCommentHeartWriter.add(i);
                }
            }

            postDetailDto.setIsCommentWriter(IsCommentWriter);
            postDetailDto.setIsCommentHeartWriter(IsCommentHeartWriter);
        }



        postsService.updateView(postId); // views ++
        postDetailDto.setPost(dto);

        return postDetailDto;
    }


    @Operation(method = "get", summary = "커뮤니티 게시글 수정 페이지")
    @GetMapping("/posts/update/{postId}")
    public UpdatePageDto update(@PathVariable Long postId,  @CurrentUser UserPrincipal member) {
        if (member==null){
            throw new FindException("로그인이 필요합니다.");
        }
        UpdatePageDto updatePageDto = new UpdatePageDto();
        PostsDto.Response dto = postsService.findById(postId);
        if(dto.getUserId()!=member.getId()){
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }
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

