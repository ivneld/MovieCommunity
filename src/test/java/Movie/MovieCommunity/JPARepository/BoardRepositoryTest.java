package Movie.MovieCommunity.JPARepository;

import Movie.MovieCommunity.JPADomain.Board;
import Movie.MovieCommunity.JPARepository.dao.BoardDao;
import Movie.MovieCommunity.JPARepository.searchCond.BoardSearchCond;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
@Transactional
@SpringBootTest
class BoardRepositoryTest {
    @Autowired
    EntityManager em;
    @Autowired
    BoardRepository boardRepository;
    @Test
    public void update(){
//        List<Board> all = boardRepository.findAll();
        Board result = boardRepository.findByMovieIdAndMemberId(318l, 1l);
        System.out.println("result = " + result);
    }

    @Test
    public void JPQLDetail(){
        List<Board> boardList = em.createQuery("select distinct b from Board b join fetch b.comments where b.id = :boardId", Board.class)
                .setParameter("boardId", 1l)
                .getResultList();

        for (Board board : boardList) {
            System.out.println("board = " + board);
        }
    }

    @Test
    public void QueryDetail(){
                Optional<Board> byDetailBoard = boardRepository.findByDetailBoard(1l);
            if (byDetailBoard.isPresent()){
                System.out.println("byDetailBoard.get() = " + byDetailBoard.get());
            }
    }

    @Test
    public void Dynamic(){
        BoardSearchCond boardSearchCond = new BoardSearchCond();
        boardSearchCond.setMovieNm("");
        PageRequest pageRequest = PageRequest.of(0,10);
        Page<BoardDao> result = boardRepository.searchBoardList(boardSearchCond, pageRequest);
//        Stream<BoardDao> boardDaoStream = result.get();
        for (BoardDao boardDao : result) {
            System.out.println("boardDao = " + boardDao);
        }

    }

//    @Test
//    public void findQueryList(){
//        List<Board> boardList = boardRepository.findBoardList();
//        for (Board board : boardList) {
//            System.out.println("board = " + board);
//        }
//    }

}