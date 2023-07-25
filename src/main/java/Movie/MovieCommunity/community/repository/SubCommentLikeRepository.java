package Movie.MovieCommunity.community.repository;

import Movie.MovieCommunity.JPADomain.Member;
import Movie.MovieCommunity.community.domain.Comment;
import Movie.MovieCommunity.community.domain.CommentLike;
import Movie.MovieCommunity.community.domain.SubComment;
import Movie.MovieCommunity.community.domain.SubCommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SubCommentLikeRepository extends JpaRepository<SubCommentLike, Long> {
    int countLikeBySubCommentId(Long subCommentId);

    Optional<SubCommentLike> findByMemberAndSubComment (Member member, SubComment subComment);


    SubCommentLike findBySubCommentIdAndMemberId(Long subCommentId, Long memberId);
    List<SubCommentLike> findAllByMember(Member member);
}
