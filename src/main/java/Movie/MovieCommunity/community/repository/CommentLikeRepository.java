package Movie.MovieCommunity.community.repository;

import Movie.MovieCommunity.JPADomain.Member;
import Movie.MovieCommunity.community.domain.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    int countLikeBycommentId(Long commentId);
    CommentLike findByCommentIdAndMemberId(Long commentId, Long memberId);
    List<CommentLike> findCommentLikeByMember(Member member);
}
