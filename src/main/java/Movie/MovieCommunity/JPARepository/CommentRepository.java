package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.Comment;
import Movie.MovieCommunity.web.dto.CommentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
//    List<Comment> findByBoardId(Long boardId);
//    Page<CommentDto> findByBoardId(Long boardId, Pageable pageable);
    //Page<CommentDto> findByBoardIdAndParentIdIsNull(Long boardId, Pageable pageable);
//    Page<Comment> findByBoardIdAndParentIdIsNull(Long boardId, Pageable pageable);

    @Query("select c from comment c where movie_id = :movieId order by like_count desc")
    List<Comment> findAllOrderByLikeCountDesc(Long movieId);

    List<Comment> findByMovieId(Long movieId);
    List<Comment> findTop8ByMovieIdIsOrderByLikeCountDesc(Long movieId);

    @Query(value = "select c from comment c where c.member.id = :memberId",
        countQuery = "select count(c) from comment c where c.member.id = :memberId")
    Page<Comment> findPageByMemberId(@Param("memberId")Long memberId, Pageable pageable);
}
