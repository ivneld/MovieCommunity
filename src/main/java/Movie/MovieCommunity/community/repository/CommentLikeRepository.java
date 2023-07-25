package Movie.MovieCommunity.community.repository;

import Movie.MovieCommunity.JPADomain.Member;
import Movie.MovieCommunity.community.domain.Comment;
import Movie.MovieCommunity.community.domain.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    int countLikeBycommentId(Long commentId);
    Optional<CommentLike> findByMemberAndComment (Member member, Comment comment);
}
