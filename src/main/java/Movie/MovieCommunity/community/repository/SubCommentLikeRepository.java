package Movie.MovieCommunity.community.repository;

import Movie.MovieCommunity.JPADomain.Member;
import Movie.MovieCommunity.community.domain.SubCommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubCommentLikeRepository extends JpaRepository<SubCommentLike, Long> {
    int countLikeBySubCommentId(Long subCommentId);
    SubCommentLike findBySubCommentIdAndMemberId(Long subCommentId, Long memberId);
    List<SubCommentLike> findAllByMember(Member member);
}
