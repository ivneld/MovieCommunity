package Movie.MovieCommunity.community.repository;


import Movie.MovieCommunity.JPADomain.Member;
import Movie.MovieCommunity.community.domain.Comment;
import Movie.MovieCommunity.community.domain.CommentLike;
import Movie.MovieCommunity.community.domain.Posts;
import Movie.MovieCommunity.community.domain.PostsLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommunityCommentRepository extends JpaRepository<Comment, Long> {
    /* 게시글 댓글 목록 가져오기 */
    List<Comment> getCommentByPostsOrderById(Posts posts);
    Optional<Comment> findByPostsIdAndId(Long postsId, Long id);

    @Modifying
    @Query("update Comment p set p.likeCount = p.likeCount + 1 where p = :comment")
    int updateAddCount(@Param("comment") Comment comment);

    @Modifying
    @Query("update Comment p set p.likeCount = p.likeCount - 1 where p = :comment")
    int updateSubtractCount(@Param("comment") Comment comment);

}
