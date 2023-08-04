package Movie.MovieCommunity.community.service;

import Movie.MovieCommunity.JPADomain.Member;
import Movie.MovieCommunity.JPARepository.MemberRepository;
import Movie.MovieCommunity.community.domain.*;
import Movie.MovieCommunity.community.dto.HeartRequestDto.CommentHeartRequestDTO;
import Movie.MovieCommunity.community.dto.HeartRequestDto.HeartRequestDTO;
import Movie.MovieCommunity.community.dto.HeartRequestDto.SubCommentHeartRequestDTO;
import Movie.MovieCommunity.community.repository.*;
import Movie.MovieCommunity.service.auth.CustomTokenProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class HeartService {

    private final PostsLikeRepository postsLikeRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final MemberRepository memberRepository;
    private final PostsRepository postsRepository;
    private final CommunityCommentRepository commentRepository;
    private final SubCommentRepository subCommentRepository;
    private final SubCommentLikeRepository subCommentLikeRepository;


    @Transactional
    public boolean check(Long postId,Long memberId) {


        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("멤버 정보가 없습니다"));

        Posts posts = postsRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("게시글 정보가 없습니다."));

        // 이미 좋아요되어있으면 true 반환
        if (postsLikeRepository.findByMemberAndPosts(member, posts).isPresent()){
            return true;
        }
        return false;
    }

    @Transactional
    public void insert(HeartRequestDTO heartRequestDTO) throws Exception {

        Member member = memberRepository.findById(heartRequestDTO.getMemberId())
                .orElseThrow(() -> new NotFoundException("멤버 id를 찾을 수 없습니다. : " + heartRequestDTO.getMemberId()));

        Posts posts = postsRepository.findById(heartRequestDTO.getPostsId())
                .orElseThrow(() -> new NotFoundException("게시판 id를 찾을 수 없습니다. : " + heartRequestDTO.getPostsId()));

        // 이미 좋아요되어있으면 에러 반환
        if (postsLikeRepository.findByMemberAndPosts(member, posts).isPresent()){
            throw new Exception("이미 좋아요 누르셨습니다.");
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
                .orElseThrow(() -> new NotFoundException("멤버 id를 찾을 수 없습니다.: " + heartRequestDTO.getMemberId()));

        Posts posts = postsRepository.findById(heartRequestDTO.getPostsId())
                .orElseThrow(() -> new NotFoundException("게시판 id를 찾을 수 없습니다." + heartRequestDTO.getPostsId()));

        PostsLike postsLike = postsLikeRepository.findByMemberAndPosts(member, posts)
                .orElseThrow(() -> new NotFoundException("하트 정보를 찾을 수 없습니다."));
        postsRepository.updateSubtractCount(posts);
        postsLikeRepository.delete(postsLike);
    }



    /**
     * 댓글 좋아요 서비스
     * */

    @Transactional
    public boolean commentCheck(Long commentId,Long memberId) {


        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("멤버 정보가 없습니다"));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("댓글 정보가 없습니다."));

        // 이미 좋아요되어있으면 true 반환
        if (commentLikeRepository.findByMemberAndComment(member, comment).isPresent()){
            return true;
        }
        return false;
    }

    @Transactional
    public void CommentHeartInsert(CommentHeartRequestDTO heartRequestDTO) throws Exception {

        Member member = memberRepository.findById(heartRequestDTO.getMemberId())
                .orElseThrow(() -> new NotFoundException("멤버 id를 찾을 수 없습니다. : " + heartRequestDTO.getMemberId()));

        Comment comment = commentRepository.findById(heartRequestDTO.getCommentId())
                .orElseThrow(() -> new NotFoundException("댓글 id를 찾을 수 없습니다. : " + heartRequestDTO.getCommentId()));

        // 이미 좋아요되어있으면 에러 반환
        if (commentLikeRepository.findByMemberAndComment(member, comment).isPresent()){
            throw new Exception("이미 좋아요 누르셨습니다.");
        }


        CommentLike commentLike = CommentLike.builder()
                .comment(comment)
                .member(member)
                .build();
        commentRepository.updateAddCount(comment);
        commentLikeRepository.save(commentLike);
    }

    @Transactional
    public void CommentHeartDelete(CommentHeartRequestDTO heartRequestDTO) {

        Member member = memberRepository.findById(heartRequestDTO.getMemberId())
                .orElseThrow(() -> new NotFoundException("멤버 id를 찾을 수 없습니다.: " + heartRequestDTO.getMemberId()));

        Comment comment = commentRepository.findById(heartRequestDTO.getCommentId())
                .orElseThrow(() -> new NotFoundException("댓글 id를 찾을 수 없습니다.: " + heartRequestDTO.getCommentId()));

        CommentLike commentLike = commentLikeRepository.findByMemberAndComment(member, comment)
                .orElseThrow(() -> new NotFoundException("하트 id를 찾을 수 없습니다."));
        commentRepository.updateSubtractCount(comment);
        commentLikeRepository.delete(commentLike);
    }



    /**
     *    대댓글 좋아요 서비스
     * */

    @Transactional
    public boolean subCommentLikeCheck(Long subCommentId,Long memberId) {


        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("멤버 정보가 없습니다"));

        SubComment subComment = subCommentRepository.findById(subCommentId)
                .orElseThrow(() -> new NotFoundException("대댓글 정보가 없습니다."));

        // 이미 좋아요되어있으면 1반환
        if (subCommentLikeRepository.findByMemberAndSubComment(member, subComment).isPresent()){
            return true;
        }
        return false;
    }

    @Transactional
    public void subcommentLikeInsert(SubCommentHeartRequestDTO heartRequestDTO) throws Exception {

        Member member = memberRepository.findById(heartRequestDTO.getMemberId())
                .orElseThrow(() -> new NotFoundException("멤버 id를 찾을 수 없습니다. : " + heartRequestDTO.getMemberId()));

        SubComment subComment = subCommentRepository.findById(heartRequestDTO.getSubCommentId())
                .orElseThrow(() -> new NotFoundException("대댓글 id를 찾을 수 없습니다. : " + heartRequestDTO.getSubCommentId()));

        // 이미 좋아요되어있으면 에러 반환
        if (subCommentLikeRepository.findByMemberAndSubComment(member, subComment).isPresent()){
            throw new Exception("이미 좋아요 누르셨습니다.");
        }


        SubCommentLike subCommentLike = SubCommentLike.builder()
                .subComment(subComment)
                .member(member)
                .build();
        subCommentRepository.updateAddCount(subComment);
        subCommentLikeRepository.save(subCommentLike);
    }

    @Transactional
    public void subCommentLikeDelete(SubCommentHeartRequestDTO heartRequestDTO) {

        Member member = memberRepository.findById(heartRequestDTO.getMemberId())
                .orElseThrow(() -> new NotFoundException("멤버 id를 찾을 수 없습니다. : " + heartRequestDTO.getMemberId()));

        SubComment subComment = subCommentRepository.findById(heartRequestDTO.getSubCommentId())
                .orElseThrow(() -> new NotFoundException("대댓글 id를 찾을 수 없습니다. : " + heartRequestDTO.getSubCommentId()));

        SubCommentLike subCommentLike = subCommentLikeRepository.findByMemberAndSubComment(member, subComment)
                .orElseThrow(() -> new NotFoundException("하트 id를 찾을 수 없습니다."));


        subCommentRepository.updateSubtractCount(subComment);
        subCommentLikeRepository.delete(subCommentLike);
    }



}