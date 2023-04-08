package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long>, QuerydslBoardRepository {
    Board findByMovieIdAndMemberId(Long MovieId,Long memberId);
    Optional<Board> findByIdAndMemberId(Long id, Long memberId);

    @Query("select distinct b from Board b join fetch b.movie join fetch b.member where b.id = :boardId")
    Optional<Board> findByDetailBoard(@Param("boardId") Long boardId);

//    @Query("select b from board b")
//    List<Board> findBoardList();
}
