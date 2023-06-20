package Movie.MovieCommunity.service;

import Movie.MovieCommunity.JPADomain.Comment;
import Movie.MovieCommunity.JPADomain.Movie;
import Movie.MovieCommunity.JPADomain.Member;
//import Movie.MovieCommunity.JPARepository.BoardRepository;
import Movie.MovieCommunity.JPARepository.CommentRepository;
import Movie.MovieCommunity.JPARepository.MemberRepository;
import Movie.MovieCommunity.JPARepository.MovieRepository;
import Movie.MovieCommunity.advice.assertThat.DefaultAssert;
import Movie.MovieCommunity.config.security.token.UserPrincipal;
import Movie.MovieCommunity.web.apiDto.comment.CommentAPI;
import Movie.MovieCommunity.web.apiDto.comment.CommentAPIRequest;
import Movie.MovieCommunity.web.apiDto.comment.CommentDeleteAPIRequest;
import Movie.MovieCommunity.web.apiDto.comment.CommentUpdateAPIRequest;
import Movie.MovieCommunity.web.form.CommentForm;
import Movie.MovieCommunity.web.response.CommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final MovieRepository movieRepository;
    public CommentForm write(CommentAPIRequest commentAPIRequest, Long memberId){
        Optional<Member> findMember = memberRepository.findById(memberId);
        DefaultAssert.isOptionalPresent(findMember);

        Optional<Movie> findMovie = movieRepository.findById(commentAPIRequest.getMovieId());
        DefaultAssert.isOptionalPresent(findMovie);

//        Comment parent = null;
//        if (commentAPIRequest.getParentId() != null){
//            Optional<Comment> findParent = commentRepository.findById(commentAPIRequest.getParentId());
//            DefaultAssert.isOptionalPresent(findParent);
//            parent = findParent.get();
//        }
        CommentForm commentForm = CommentForm.builder()
                .content(commentAPIRequest.getContent())
                .movie(findMovie.get())
                .member(findMember.get())
//                .parent(parent)
                .build();
        commentRepository.save(new Comment(commentForm));
        return commentForm;
    }
    public Boolean update(CommentUpdateAPIRequest commentUpdateAPIRequest, Long memberId){
        Optional<Comment> findComment = commentRepository.findById(commentUpdateAPIRequest.getCommentId());
        DefaultAssert.isOptionalPresent(findComment);

        Optional<Member> findMember = memberRepository.findById(memberId);
        DefaultAssert.isOptionalPresent(findMember);

        if (!checkMine(commentUpdateAPIRequest, findMember)){
            return false;
        }


        Comment comment = findComment.get();
        comment.updateContent(commentUpdateAPIRequest.getContent());

        return true;
    }
    public boolean delete(CommentDeleteAPIRequest commentDeleteAPIRequest, Long memberId) {
        Optional<Comment> findComment = commentRepository.findById(commentDeleteAPIRequest.getCommentId());
        DefaultAssert.isOptionalPresent(findComment);

        Optional<Member> findMember = memberRepository.findById(memberId);
        DefaultAssert.isOptionalPresent(findMember);

        if (!checkMine(commentDeleteAPIRequest, findMember)){
            return false;
        }

        commentRepository.delete(findComment.get());
        return true;
    }

//    movieNm 삭제
    public List<CommentResponse> commentList(Long movieId) {
        List<CommentResponse> result = new ArrayList<>();
        List<Comment> comments = commentRepository.findAllOrderByLikeCountDesc(movieId);
        for (Comment comment : comments) {

            result.add(CommentResponse.builder()
                    .commentId(comment.getId())
                    .memberId(comment.getMember().getId())
                    .username(comment.getMember().getUsername())
                    .movieId(comment.getMovie().getId())
                    .content(comment.getContent())
                    .likeCount(comment.getLikeCount())
                    .build());
        }
        return result;
    }

    public List<CommentResponse> top8CommentList(Long movieId) {
        List<CommentResponse> result = new ArrayList<>();
        List<Comment> comments = commentRepository.findTop8ByMovieIdIsOrderByLikeCountDesc(movieId);
        for (Comment comment : comments) {
            result.add(CommentResponse.builder()
                    .commentId(comment.getId())
                    .memberId(comment.getMember().getId())
                    .username(comment.getMember().getUsername())
                    .movieId(comment.getMovie().getId())
                    .content(comment.getContent())
                    .likeCount(comment.getLikeCount())
                    .build());
        }
        return result;
    }

    public Integer plusLike(Long commentId, Long memberId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        DefaultAssert.isOptionalPresent(comment);

        Optional<Member> member = memberRepository.findById(memberId);
        DefaultAssert.isOptionalPresent(member);

        Integer result = comment.get().plusLikeCount();
        return result;
    }

    public Integer updateLike(Long commentId, Integer count) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        DefaultAssert.isOptionalPresent(comment);

        comment.get().updateLikeCount(count);
        return count;
    }
    private boolean checkMine(CommentAPI commentUpdateAPIRequest, Optional<Member> findMember) {
        if (commentUpdateAPIRequest.getMemberId() != findMember.get().getId()){ // 댓글 작성자가 아닌 경우 예외처리
            return true;
        }
        return false;
    }


}
