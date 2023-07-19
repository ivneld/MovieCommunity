package Movie.MovieCommunity.community.service;

import Movie.MovieCommunity.JPADomain.Member;
import Movie.MovieCommunity.JPARepository.MemberRepository;
import Movie.MovieCommunity.community.domain.*;
import Movie.MovieCommunity.community.dto.HeartRequestDTO;
import Movie.MovieCommunity.community.dto.LikeRequestDto;
import Movie.MovieCommunity.community.repository.CommentLikeRepository;
import Movie.MovieCommunity.community.repository.PostsLikeRepository;
import Movie.MovieCommunity.community.repository.PostsRepository;
import Movie.MovieCommunity.community.repository.SubCommentLikeRepository;
import Movie.MovieCommunity.community.response.ResponseDto;
import Movie.MovieCommunity.config.security.token.CurrentUser;
import Movie.MovieCommunity.config.security.token.UserPrincipal;
import Movie.MovieCommunity.service.auth.CustomTokenProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HeartService {

    private final PostsLikeRepository postsLikeRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final MemberRepository memberRepository;
    private final PostsRepository postsRepository;
    private final CustomTokenProviderService tokenProvider;
    private final SubCommentLikeRepository subCommentLikeRepository;
    private final CommunityCommentService commentService;
    private final SubCommentService subCommentService;

    @Transactional
    public void insert(HeartRequestDTO heartRequestDTO) throws Exception {

        Member member = memberRepository.findById(heartRequestDTO.getMemberId())
                .orElseThrow(() -> new NotFoundException("Could not found member id : " + heartRequestDTO.getMemberId()));

        Posts posts = postsRepository.findById(heartRequestDTO.getPostsId())
                .orElseThrow(() -> new NotFoundException("Could not found board id : " + heartRequestDTO.getPostsId()));

        // 이미 좋아요되어있으면 에러 반환
        if (postsLikeRepository.findByMemberAndPosts(member, posts).isPresent()){
            throw new Exception();
        }


        PostsLike postsLike = PostsLike.builder()
                .posts(posts)
                .member(member)
                .build();
        postsRepository.updateAddCount(posts);
        postsLikeRepository.save(postsLike);
    }

    @Transactional
    public void delete(HeartRequestDTO heartRequestDTO) {

        Member member = memberRepository.findById(heartRequestDTO.getMemberId())
                .orElseThrow(() -> new NotFoundException("Could not found member id : " + heartRequestDTO.getMemberId()));

        Posts posts = postsRepository.findById(heartRequestDTO.getPostsId())
                .orElseThrow(() -> new NotFoundException("Could not found board id : " + heartRequestDTO.getPostsId()));

        PostsLike postsLike = postsLikeRepository.findByMemberAndPosts(member, posts)
                .orElseThrow(() -> new NotFoundException("Could not found heart id"));
        postsRepository.updateSubtractCount(posts);
        postsLikeRepository.delete(postsLike);
    }



    /**
     * 댓글, 대댓글 좋아요 서비스
     * */

    @org.springframework.transaction.annotation.Transactional
    public ResponseDto<?> doCommentLike(LikeRequestDto requestDto,  @CurrentUser UserPrincipal member) {
        if (null ==member.getId()) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }
        /**
        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);


        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }
         */

        Comment comment = commentService.isPresentComment(requestDto.getCommentId());
        if (null == comment) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 댓글 id 입니다.");
        }

        CommentLike checkLike = commentLikeRepository.findByCommentIdAndMemberId(comment.getId(), member.getId());
        if(null!=checkLike) {
            return ResponseDto.fail("ALREADY_DONE", "이미 좋아요를 하셨습니다.");
        }

        Member member1 = memberRepository.findById(member.getId()).get();
        CommentLike commentLike = CommentLike.builder()
                .member(member1)
                .comment(comment)
                .build();

        commentLikeRepository.save(commentLike);
        return ResponseDto.success("success");     //좋아요 개수 넣기
    }

    @org.springframework.transaction.annotation.Transactional
    public ResponseDto<?> cancelCommentLike(Long id,  @CurrentUser UserPrincipal member) {
        if (null == member.getId()) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        /**
        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }
        */
        CommentLike commentLike = isPresentCommentLike(id);
        if (null == commentLike) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 좋아요 id 입니다.");
        }

        if (!(commentLike.getMember().getId().equals(member.getId()))) {       // 이 내용을 수행하기 전에 이미 사용자 검증... 프론트 단에서 좋아요 취소 버튼 활성화
            return ResponseDto.fail("BAD_REQUEST", "작성자만 취소할 수 있습니다.");
        }

        commentLikeRepository.delete(commentLike);
        return ResponseDto.success("success");      //좋아요 개수 넣기
    }


    @org.springframework.transaction.annotation.Transactional
    public ResponseDto<?> subCommentLike(LikeRequestDto requestDto,@CurrentUser UserPrincipal member) {
        if (null == member.getId()) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }
        /**
        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }
         */

        SubComment subComment = subCommentService.isPresentSubComment(requestDto.getCommentId());
        if (null == subComment) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 댓글 id 입니다.");
        }

        SubCommentLike checkLike = subCommentLikeRepository.findBySubCommentIdAndMemberId(subComment.getId(), member.getId());
        if(null!=checkLike) {
            return ResponseDto.fail("ALREADY_DONE", "이미 좋아요를 하셨습니다.");
        }
        Member member1 = memberRepository.findById(member.getId()).get();
        SubCommentLike subCommentLike = SubCommentLike.builder()
                .member(member1)
                .subComment(subComment)
                .build();
        subCommentLikeRepository.save(subCommentLike);
        return ResponseDto.success("success");     //좋아요 개수 넣기
    }

    @org.springframework.transaction.annotation.Transactional
    public ResponseDto<?> cancelSubCommentLike(Long id, @CurrentUser UserPrincipal member) {
        if (null == member.getId()) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }
        /**
        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

         */
        SubCommentLike subCommentLike = isPresentSubCommentLike(id);
        if (null == subCommentLike) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 좋아요 id 입니다.");
        }

        if (!(subCommentLike.getMember().getId().equals(member.getId()))) {       // 이 내용을 수행하기 전에 이미 사용자 검증... 프론트 단에서 좋아요 취소 버튼 활성화
            return ResponseDto.fail("BAD_REQUEST", "작성자만 취소할 수 있습니다.");
        }

        subCommentLikeRepository.delete(subCommentLike);
        return ResponseDto.success("success");      //좋아요 개수 넣기
    }


    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public CommentLike isPresentCommentLike(Long id) {
        Optional<CommentLike> optionalLike = commentLikeRepository.findById(id);
        return optionalLike.orElse(null);
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public SubCommentLike isPresentSubCommentLike(Long id) {
        Optional<SubCommentLike> optionalLike = subCommentLikeRepository.findById(id);
        return optionalLike.orElse(null);
    }

    /**
    @org.springframework.transaction.annotation.Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }
     */

}