package Movie.MovieCommunity.community.repository;

import Movie.MovieCommunity.JPADomain.Member;
import Movie.MovieCommunity.community.domain.Comment;
import Movie.MovieCommunity.community.domain.SubComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubCommentRepository extends JpaRepository<SubComment, Long> {
    List<SubComment> findAllByComment(Comment comment);
    List<SubComment> findAllByMember(Member member);
    @Modifying
    @Query("update SubComment p set p.likeCount = p.likeCount + 1 where p = :subComment")
    int updateAddCount(@Param("subComment") SubComment subComment);

    @Modifying
    @Query("update SubComment p set p.likeCount = p.likeCount - 1 where p = :subComment")
    int updateSubtractCount(@Param("subComment") SubComment subComment);
}