package Movie.MovieCommunity.community.repository;

import Movie.MovieCommunity.JPADomain.Member;
import Movie.MovieCommunity.community.domain.CommentLike;
import Movie.MovieCommunity.community.domain.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike,Long> {
    Optional<CommentLike> findByMemberAndPosts(Member member, Posts posts);

}
