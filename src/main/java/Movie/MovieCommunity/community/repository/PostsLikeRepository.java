package Movie.MovieCommunity.community.repository;

import Movie.MovieCommunity.JPADomain.Member;
import Movie.MovieCommunity.community.domain.PostsLike;
import Movie.MovieCommunity.community.domain.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostsLikeRepository extends JpaRepository<PostsLike,Long> {
    Optional<PostsLike> findByMemberAndPosts(Member member, Posts posts);

}
