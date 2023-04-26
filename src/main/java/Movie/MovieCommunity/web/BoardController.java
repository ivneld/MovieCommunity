package Movie.MovieCommunity.web;

import Movie.MovieCommunity.JPADomain.Board;
import Movie.MovieCommunity.JPADomain.Comment;
import Movie.MovieCommunity.JPADomain.JpaMovie;
import Movie.MovieCommunity.JPADomain.Member;
import Movie.MovieCommunity.JPARepository.BoardRepository;
import Movie.MovieCommunity.JPARepository.CommentRepository;
import Movie.MovieCommunity.JPARepository.MemberRepository;
import Movie.MovieCommunity.JPARepository.MovieRepository;
import Movie.MovieCommunity.JPARepository.dao.BoardDao;
import Movie.MovieCommunity.JPARepository.searchCond.BoardSearchCond;
import Movie.MovieCommunity.annotation.CurrentMember;
import Movie.MovieCommunity.config.security.token.UserPrincipal;
import Movie.MovieCommunity.service.BoardService;
import Movie.MovieCommunity.web.apiDto.board.BoardAPIRequest;
import Movie.MovieCommunity.web.apiDto.board.BoardDeleteAPIRequest;
import Movie.MovieCommunity.web.apiDto.board.BoardDetailAPIRequest;
import Movie.MovieCommunity.web.apiDto.board.BoardDetailAPIResponse;
import Movie.MovieCommunity.web.dto.CommentDto;
import Movie.MovieCommunity.web.form.BoardForm;
import Movie.MovieCommunity.web.form.CommentForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
@Tag(name="board", description = "게시판 API")
public class BoardController {
    private final BoardRepository boardRepository;
    private final MovieRepository movieRepository;
    private final BoardService boardService;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;


    @GetMapping
    @Transactional(readOnly = true)
    public Page<BoardDao> boardList(BoardSearchCond boardSearchCond, Pageable pageable){
//        Page<Board> page = boardRepository.findAll(pageable);
//        Page<BoardDto> pageDto = page.map(BoardDto::new);
        Page<BoardDao> boardDaos = boardRepository.searchBoardList(boardSearchCond, pageable);
        return boardDaos;
    }


    @Operation(method="post", summary = "게시판 생성")
    @ApiResponses(value=
        @ApiResponse(responseCode = "201", description = "게시판 생성 성공", content={@Content(mediaType = "application/json")})
    )
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody BoardAPIRequest boardAPIRequest, @CurrentMember UserPrincipal userPrincipal) {
        boardService.write(boardAPIRequest, userPrincipal);
        return new ResponseEntity(HttpStatus.OK);
    }


    @Operation(method="get", summary = "게시판 상세 내용")
    @ApiResponses(value=
        @ApiResponse(responseCode = "200", description = "게시판 상세내용 출력 성공", content={@Content(mediaType = "application/json",schema = @Schema(implementation = BoardDetailAPIResponse.class))})
    )
    @GetMapping("/{boardId}")
    public  ResponseEntity<?> boardDetail(@PathVariable Long boardId){
        BoardDetailAPIResponse response = boardService.getDetail(boardId);
        return ResponseEntity.ok(response);
    }



    @Operation(method="post", summary = "게시판 상세 내용 수정")
    @ApiResponses(value=
    @ApiResponse(responseCode = "200", description = "게시판 상세내용 수정 성공", content={@Content(mediaType = "application/json")})
    )
    @PutMapping("/{boardId}")
    public ResponseEntity<?> update(@Valid @RequestBody BoardDetailAPIRequest boardDetailAPIRequest, @CurrentMember UserPrincipal userPrincipal){
        if(!boardService.update(boardDetailAPIRequest, userPrincipal)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(method="delete", summary = "게시판 상세 내용 삭제")
    @ApiResponses(value=
    @ApiResponse(responseCode = "200", description = "게시판 상세내용 삭제 성공", content={@Content(mediaType = "application/json")})
    )
    @DeleteMapping("/{boardId}")
    public ResponseEntity<?> delete(@Valid @RequestBody BoardDeleteAPIRequest boardDeleteAPIRequest, @CurrentMember UserPrincipal userPrincipal){
        if(!boardService.delete(boardDeleteAPIRequest, userPrincipal)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
