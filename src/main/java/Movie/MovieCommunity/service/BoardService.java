package Movie.MovieCommunity.service;

import Movie.MovieCommunity.JPADomain.Board;
import Movie.MovieCommunity.JPARepository.BoardRepository;
import Movie.MovieCommunity.JPARepository.MemberRepository;
import Movie.MovieCommunity.web.form.BoardForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    public Board write(BoardForm boardForm){
        return boardRepository.save(new Board(boardForm));
    }
    public Board update(BoardForm boardForm){
        Optional<Board> findBoard = boardRepository.findById(boardForm.getId());
        if (findBoard.isPresent()){
            Board board = findBoard.get();
            board.updateBoard(boardForm.getTitle(), boardForm.getContent());
            return board;
        }
        else{
            return null;
        }
    }
    @Transactional(readOnly = true)
    public Board findOne(Long id){
        Optional<Board> result = boardRepository.findByDetailBoard(id);
        if (result.isPresent()){
            return result.get();
        }
        return null;
    }

    @Transactional(readOnly = true)
    public Board checkMyBoard(Long boardId, Long memberId){
//        Optional<Member> findMember = memberRepository.findById(memberId);
//        Member member = findMember.get();
//
        Optional<Board> findBoard = boardRepository.findByIdAndMemberId(boardId, memberId);
        if (findBoard.isPresent()){
            return findBoard.get();
        }
        return null;
    }

    public Board updateBoard(BoardForm boardForm){
        Optional<Board> findBoard = boardRepository.findById(boardForm.getId());
        Board board = findBoard.get();
        return board.updateBoard(boardForm.getTitle(), boardForm.getContent());
    }
}
