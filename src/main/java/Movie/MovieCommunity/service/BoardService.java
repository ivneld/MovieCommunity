package Movie.MovieCommunity.service;

import Movie.MovieCommunity.JPADomain.*;
import Movie.MovieCommunity.JPARepository.*;
import Movie.MovieCommunity.JPARepository.dao.BoardDao;
import Movie.MovieCommunity.advice.assertThat.DefaultAssert;
import Movie.MovieCommunity.config.security.token.UserPrincipal;
import Movie.MovieCommunity.web.apiDto.board.*;
import Movie.MovieCommunity.web.dto.CommentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final MovieRepository movieRepository;
    private final CommentRepository commentRepository;
    private final LikeBoardRepository likeBoardRepository;
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
        findBoard.get().setModifiedDt(LocalDateTime.now());
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

    public boolean likeBoard(BoardLikeAPIRequest boardLikeAPIRequest, UserPrincipal userPrincipal) {
        Optional<Member> findMember = memberRepository.findById(boardLikeAPIRequest.getMemberId());
        DefaultAssert.isOptionalPresent(findMember);

        Optional<Board> findBoard = boardRepository.findById(boardLikeAPIRequest.getBoardId());
        DefaultAssert.isOptionalPresent(findBoard);

        if(boardLikeAPIRequest.getMemberId() != userPrincipal.getId()){
            log.error("사용자 정보가 일치하지 않습니다.");
            return false;
        }
        if (likeBoardRepository.existByBoardIdAndMemberId(boardLikeAPIRequest.getBoardId(), boardLikeAPIRequest.getMemberId())){
            log.error("이미 좋아요를 눌렀습니다.");
            return false;
        }


        LikeBoard likeBoard = LikeBoard.builder()
                .board(findBoard.get())
                .member(findMember.get())
                .build();
        likeBoardRepository.save(likeBoard);
        return true;
    }
}
