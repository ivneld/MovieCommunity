package Movie.MovieCommunity.community.repository;


import Movie.MovieCommunity.community.domain.Comment;
import Movie.MovieCommunity.community.domain.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommunityCommentRepository extends JpaRepository<Comment, Long> {
    /* 게시글 댓글 목록 가져오기 */
    List<Comment> getCommentByPostsOrderById(Posts posts);

    Optional<Comment> findByPostsIdAndId(Long postsId, Long id);
}
