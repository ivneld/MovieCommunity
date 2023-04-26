package Movie.MovieCommunity.service;

import Movie.MovieCommunity.JPADomain.Board;
import Movie.MovieCommunity.JPADomain.Comment;
import Movie.MovieCommunity.JPADomain.JpaMovie;
import Movie.MovieCommunity.JPADomain.Member;
import Movie.MovieCommunity.JPARepository.BoardRepository;
import Movie.MovieCommunity.JPARepository.CommentRepository;
import Movie.MovieCommunity.JPARepository.MemberRepository;
import Movie.MovieCommunity.JPARepository.MovieRepository;
import Movie.MovieCommunity.JPARepository.dao.BoardDao;
import Movie.MovieCommunity.advice.assertThat.DefaultAssert;
import Movie.MovieCommunity.annotation.CurrentMember;
import Movie.MovieCommunity.config.security.token.UserPrincipal;
import Movie.MovieCommunity.web.BoardController;
import Movie.MovieCommunity.web.apiDto.board.BoardAPIRequest;
import Movie.MovieCommunity.web.apiDto.board.BoardDeleteAPIRequest;
import Movie.MovieCommunity.web.apiDto.board.BoardDetailAPIRequest;
import Movie.MovieCommunity.web.apiDto.board.BoardDetailAPIResponse;
import Movie.MovieCommunity.web.dto.CommentDto;
import Movie.MovieCommunity.web.form.BoardForm;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final MovieRepository movieRepository;
    private final CommentRepository commentRepository;
    public Board write(BoardAPIRequest boardAPIRequest, UserPrincipal userPrincipal){
        Optional<Member> findMember = memberRepository.findByEmail(userPrincipal.getEmail());
        DefaultAssert.isOptionalPresent(findMember);

        Optional<JpaMovie> findMovie = movieRepository.findById(boardAPIRequest.getMovieId());
        DefaultAssert.isOptionalPresent(findMovie);

        Board board = Board.builder()
                .member(findMember.get())
                .movie(findMovie.get())
                .title(boardAPIRequest.getTitle())
                .content(boardAPIRequest.getContent())
                .build();
        return boardRepository.save(board);
    }




    @Transactional(readOnly = true)
    public BoardDetailAPIResponse getDetail(Long boardId){
        Optional<Board> findBoard = boardRepository.findById(boardId);
        DefaultAssert.isOptionalPresent(findBoard);

        BoardDetailAPIResponse boardDetailAPIResponse = new BoardDetailAPIResponse();


        BoardDao boardDao = new BoardDao(findBoard.get());


        boardDetailAPIResponse.setBoard(boardDao);


        PageRequest pageRequest = PageRequest.of(0,10);
        Page<CommentDto> commentPage = commentRepository.findByBoardId(boardId, pageRequest);


        boardDetailAPIResponse.setComment(commentPage);



        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdDt").ascending());
        Page<Comment> commentDtos = commentRepository.findByBoardIdAndParentIdIsNull(1l, pageable);

        List<Comment> contents = commentDtos.getContent();


        List<CommentDto> collect = contents.stream().map(comment -> new CommentDto(comment)).
                collect(Collectors.toList());


        int size = commentDtos.getSize();
//            System.out.println("size = " + size);
        for (CommentDto content : collect) {
//                System.out.println("content.getContent() = " + content.getContent());
            List<CommentDto> children = content.getChildren();
//                System.out.println("children = " + children);
            for (CommentDto child : children) {
//                    System.out.println("child = " + child);
            }
        }
        PageImpl<CommentDto> commentDtoPage = new PageImpl<CommentDto>(collect, pageable, pageable.getPageSize());

        boardDetailAPIResponse.setComment(commentDtoPage);

        return boardDetailAPIResponse;
    }

    public boolean update(BoardDetailAPIRequest boardDetailAPIRequest, UserPrincipal userPrincipal) {
        if (!boardDetailAPIRequest.getEmail().equals(userPrincipal.getEmail())){
            return false;
        }
        Optional<Member> findMember = memberRepository.findByEmail(userPrincipal.getEmail());
        DefaultAssert.isOptionalPresent(findMember);

        Optional<Board> findBoard = boardRepository.findByIdAndMemberId(boardDetailAPIRequest.getBoarId(), boardDetailAPIRequest.getBoarId());
        DefaultAssert.isOptionalPresent(findBoard);

        findBoard.get().updateBoard(boardDetailAPIRequest.getTitle(), boardDetailAPIRequest.getContent());
        return true;
    }



    public boolean delete(BoardDeleteAPIRequest boardDeleteAPIRequest, UserPrincipal userPrincipal) {
        if (!boardDeleteAPIRequest.getEmail().equals(userPrincipal.getEmail())){
            return false;
        }
        Optional<Member> findMember = memberRepository.findByEmail(userPrincipal.getEmail());
        DefaultAssert.isOptionalPresent(findMember);

        Optional<Board> findBoard = boardRepository.findByIdAndMemberId(boardDeleteAPIRequest.getBoarId(), boardDeleteAPIRequest.getBoarId());
        DefaultAssert.isOptionalPresent(findBoard);

        boardRepository.delete(findBoard.get());
        return true;
    }
}
