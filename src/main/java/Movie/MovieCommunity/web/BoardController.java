package Movie.MovieCommunity.web;

import Movie.MovieCommunity.JPADomain.Board;
import Movie.MovieCommunity.JPADomain.Comment;
import Movie.MovieCommunity.JPADomain.JpaMovie;
import Movie.MovieCommunity.JPADomain.Member;
import Movie.MovieCommunity.JPARepository.BoardRepository;
import Movie.MovieCommunity.JPARepository.CommentRepository;
import Movie.MovieCommunity.JPARepository.MovieRepository;
import Movie.MovieCommunity.JPARepository.dao.BoardDao;
import Movie.MovieCommunity.JPARepository.searchCond.BoardSearchCond;
import Movie.MovieCommunity.service.BoardService;
import Movie.MovieCommunity.web.dto.CommentDto;
import Movie.MovieCommunity.web.form.BoardForm;
import Movie.MovieCommunity.web.form.CommentForm;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
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

import static Movie.MovieCommunity.web.SessionConst.*;

@Controller
@RequestMapping("/boards")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class BoardController {
    private final BoardRepository boardRepository;
    private final MovieRepository movieRepository;
    private final BoardService boardService;
    private final CommentRepository commentRepository;
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
    public String createForm(@ModelAttribute BoardForm boardForm, @RequestParam(name = "movieId") Long movieId, HttpServletRequest request, Model model) {


        log.info("movie ID = {}", movieId);
        Optional<JpaMovie> findMovie = movieRepository.findById(movieId);

        if (findMovie.isPresent()){
            log.info("{}", findMovie);
            request.setAttribute("movie", findMovie);
        }else{
            return "redirect:/boards";
        }
//        HttpSession session = request.getSession(false);
//        if(session == null) {
//            String requestURI = request.getRequestURI();
//            log.info("{}",response);
//            httpResponse.sendRedirect("/member/login?redirectURL="+requestURI);
//           // redirectAttributes.addAttribute("path", requestURI);
//            //return "redirect:/member/login";
//        }
//        Member member = (Member) session.getAttribute(LOGIN_MEMBER);
//        if (member==null){
//            log.info("BBBBBBBB");
//
//            String requestURI = request.getRequestURI();
//            log.info("AAAAAAA");
//            httpResponse.sendRedirect("/member/login?redirectURL="+requestURI);
////            return "redirect:/member/login";
//        }
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



    @ResponseBody
    @GetMapping("/{boardId}")
    public  BoardDetail boardDetail(@PathVariable Long boardId, Model model, @ModelAttribute CommentForm commentForm, HttpServletRequest request){
        Board board = boardService.findOne(boardId);
        BoardDetail boardDetail = new BoardDetail();
       // log.info("1{}", boardDetail);
        if (board != null){
            model.addAttribute("board", board);
            BoardDao boardDao = new BoardDao(board);

            // comment/create 에서 확인 시 값이 없음, html form 에서 board를 추가적으로 설정해야 할 듯
            // commentForm.setBoard(board);
        //    log.info("2{}", boardDetail);

            // 임시로 세션 활용
            HttpSession session = request.getSession();
            session.setAttribute(BOARD, board);
            boardDetail.setBoard(boardDao);

            //댓글 데이터 추가 필요


            //댓글 데이터 추가 필요
            PageRequest pageRequest = PageRequest.of(0,10);
            Page<CommentDto> commentPage = commentRepository.findByBoardId(boardId, pageRequest);


            boardDetail.setComment(commentPage);



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
            //////////////////////////////
            PageImpl<CommentDto> commentDtoPage = new PageImpl<CommentDto>(collect, pageable, pageable.getPageSize());

            boardDetail.setComment(commentDtoPage);
//            System.out.println("boardDetail = " + boardDetail);
            return boardDetail;
//            return "boardDetail";

        }
//        return "redirect:/boards";
    //    log.info("4{}", boardDetail);
        log.info("보드 id 못찾음1");
        return null;
    }

    @GetMapping("/{boardId}/update")
    public String updateForm(@PathVariable Long boardId, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);

        Member member = (Member)session.getAttribute(LOGIN_MEMBER);
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
        Member member = (Member) session.getAttribute(LOGIN_MEMBER);
//        System.out.println("member = " + member);
        boardForm.setMember(member);
//        boardForm.setMovie(movie);
    }


    @Data
    @NoArgsConstructor
    static class BoardDetail{
        private BoardDao board;
        private Page<CommentDto> comment;

        public BoardDetail(BoardDao board, Page<CommentDto> comment) {
            this.board = board;
            this.comment = comment;
        }
    }
}
