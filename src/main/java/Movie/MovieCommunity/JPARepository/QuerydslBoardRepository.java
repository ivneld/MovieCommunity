package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPARepository.dao.BoardDao;
import Movie.MovieCommunity.JPARepository.searchCond.BoardSearchCond;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuerydslBoardRepository {
    Page<BoardDao> searchBoardList(BoardSearchCond boardSearchCond, Pageable pageable);
}
