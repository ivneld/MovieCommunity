package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.LikeBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeBoardRepository extends JpaRepository<LikeBoard, Long> {
    boolean existByBoardIdAndMemberId(Long boardId, Long memberId);
}
