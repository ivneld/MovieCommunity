package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.Comment;
import Movie.MovieCommunity.web.dto.CommentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
//    List<Comment> findByBoardId(Long boardId);
//    Page<CommentDto> findByBoardId(Long boardId, Pageable pageable);
    //Page<CommentDto> findByBoardIdAndParentIdIsNull(Long boardId, Pageable pageable);
//    Page<Comment> findByBoardIdAndParentIdIsNull(Long boardId, Pageable pageable);

    List<Comment> findAllOrderByLikeCountDesc();

    List<Comment> findTop8ByOrderByLikeCountDesc();

}
