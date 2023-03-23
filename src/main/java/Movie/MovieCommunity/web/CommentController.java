package Movie.MovieCommunity.web;

import Movie.MovieCommunity.JPADomain.Board;
import Movie.MovieCommunity.JPADomain.Comment;
import Movie.MovieCommunity.JPADomain.Member;
import Movie.MovieCommunity.JPARepository.BoardRepository;
import Movie.MovieCommunity.JPARepository.dao.BoardDao;
import Movie.MovieCommunity.service.CommentService;
import Movie.MovieCommunity.web.form.BoardForm;
import Movie.MovieCommunity.web.form.CommentForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;


/**
 * movieWithgenre table
 * movie : movie_id ,movie_nm, open_dt
 * genre : genre_id, genre_nm
 */
@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
@Slf4j
public class CommentController {
    private final CommentService commentService;
    private final BoardRepository boardRepository;

    @PostMapping("/create")
    public String create(@ModelAttribute @Valid CommentForm commentForm, BindingResult bindingResult, HttpServletRequest request, RedirectAttributes redirectAttributes){


        HttpSession session = request.getSession(false);
        Board board = (Board) session.getAttribute(SessionConst.BOARD);
        commentForm.setBoard(board);

        if (session == null){// 댓글 작성 시 로그인 확인
            bindingResult.reject("loginRequired","로그인이 필요합니다.");
            return "redirect:/member/login";
        }
        redirectAttributes.addAttribute("boardId", commentForm.getBoard().getId());


        if (bindingResult.hasErrors()){
//            model.addAttribute("board", board);
            redirectAttributes.addAttribute("comment", false);
            return "redirect:/boards/{boardId}";
//            return "boardDetail";
        }
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        commentForm.setMember(member);
        commentService.write(commentForm);
        session.removeAttribute(SessionConst.BOARD);
        return "redirect:/boards/{boardId}";
    }

}






















