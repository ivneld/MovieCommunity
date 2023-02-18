package Movie.MovieCommunity.web;

import Movie.MovieCommunity.JPADomain.Board;
import Movie.MovieCommunity.JPADomain.JpaMovie;
import Movie.MovieCommunity.JPADomain.Member;
import Movie.MovieCommunity.JPARepository.BoardRepository;
import Movie.MovieCommunity.JPARepository.MovieRepository;
import Movie.MovieCommunity.JPARepository.dao.BoardDao;
import Movie.MovieCommunity.JPARepository.searchCond.BoardSearchCond;
import Movie.MovieCommunity.domain.Movie;
import Movie.MovieCommunity.service.BoardService;
import Movie.MovieCommunity.web.dto.BoardDto;
import Movie.MovieCommunity.web.form.AddMemberForm;
import Movie.MovieCommunity.web.form.BoardForm;
import Movie.MovieCommunity.web.form.CommentForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

@Controller
@RequestMapping("/boards")
@RequiredArgsConstructor
@Slf4j
public class BoardController {
    private final BoardRepository boardRepository;
    private final MovieRepository movieRepository;
    private final BoardService boardService;
    @ResponseBody
    @GetMapping
    @Transactional(readOnly = true)
    public Page<BoardDao> boardList(BoardSearchCond boardSearchCond, Pageable pageable){
//        Page<Board> page = boardRepository.findAll(pageable);
//        Page<BoardDto> pageDto = page.map(BoardDto::new);
        Page<BoardDao> boardDaos = boardRepository.searchBoardList(boardSearchCond, pageable);
        return boardDaos;
    }

    @Transactional(readOnly = true)
    @GetMapping("/create")// 파라미터에 movie 를 확인하는 것이 아닌 movie 상세에서 movie data 를 넘겨 받을 수 있어야함..
    public String createForm(@ModelAttribute BoardForm boardForm, @RequestParam(name = "movieId") Long movieId, HttpServletRequest request){
        log.info("movie ID = {}", movieId);
        Optional<JpaMovie> findMovie = movieRepository.findById(movieId);

        if (findMovie.isPresent()){
            log.info("{}", findMovie);
            request.setAttribute("movie", findMovie);
        }else{
            return "redirect:/boards";
        }
        HttpSession session = request.getSession(false);
        if(session == null) {
            return "redirect:/member/login";
        }
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (member==null){
            return "redirect:/member/login";
        }
        return "boardCreate";
    }

    @Transactional
    @PostMapping("/create")
    public String create(@ModelAttribute @Valid BoardForm boardForm,@RequestParam(name = "movieId") Long movieId, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "boardCreate";
        }
        JpaMovie jpaMovie = movieRepository.findById(movieId).get();
        boardForm.setMovie(jpaMovie);
        boardFormUpdate(boardForm, request);
        Board savedBoard = boardRepository.save(new Board(boardForm));
        redirectAttributes.addAttribute("boardId", savedBoard.getId());
        return "redirect:/boards/{boardId}";
    }



    @GetMapping("/{boardId}")
    public String boardDetail(@PathVariable Long boardId, Model model, @ModelAttribute CommentForm commentForm){
        Board board = boardService.findOne(boardId);
        if (board != null){
            model.addAttribute("board", board);
            commentForm.setBoard(board);

            return "boardDetail";
        }
        return "redirect:/boards";
    }

    @GetMapping("/{boardId}/update")
    public String updateForm(@PathVariable Long boardId, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session == null){
            return "redirect:/member/login";
        }
        Member member = (Member)session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (member == null){// 로그인이 필요
            return "redirect:/member/login";}
        Board board = boardService.checkMyBoard(boardId, member.getId());
        if ( board != null){ // 로그인한 멤버가 작성한 게시판인지 확인
            BoardForm boardForm = new BoardForm(board);
            model.addAttribute("boardForm", boardForm);
            return "boardUpdate";
        }
        return "redirect:/boards";
    }


    @PostMapping("/{boardId}/update")
    public String update(@PathVariable Long boardId, @ModelAttribute @Valid BoardForm boardForm,  BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            return "boardUpdate";
        }
        boardForm.setId(boardId);
        boardService.updateBoard(boardForm);
        redirectAttributes.addAttribute("boardId", boardId);
        return "redirect:/boards/{boardId}";
    }


    private void boardFormUpdate(BoardForm boardForm, HttpServletRequest request) { // movie 와 member 데이터 세팅
        HttpSession session = request.getSession(false);
//        JpaMovie movie = (JpaMovie) request.getAttribute("movie");
//        System.out.println("movie = " + movie);
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
//        System.out.println("member = " + member);
        boardForm.setMember(member);
//        boardForm.setMovie(movie);
    }
}
