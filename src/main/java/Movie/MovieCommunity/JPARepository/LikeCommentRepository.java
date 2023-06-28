package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.LikeComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeCommentRepository extends JpaRepository<LikeComment, Long> {
    Optional<LikeComment> findByCommentIdAndMemberId(Long commentId, Long memberId);

}
