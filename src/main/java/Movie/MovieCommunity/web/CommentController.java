package Movie.MovieCommunity.web;

import Movie.MovieCommunity.JPADomain.Comment;
import Movie.MovieCommunity.JPADomain.Member;
import Movie.MovieCommunity.service.CommentService;
import Movie.MovieCommunity.web.form.CommentForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
@Slf4j
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/create")
    public String create(@ModelAttribute @Valid CommentForm commentForm, BindingResult bindingResult, HttpServletRequest request, RedirectAttributes redirectAttributes){

        HttpSession session = request.getSession(false);
        if (session == null){// 댓글 작성 시 로그인 확인
            bindingResult.reject("loginRequired","로그인이 필요합니다.");
            return "boardDetail";
        }
        if (bindingResult.hasErrors()){
            return "boardDetail";
        }
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        commentForm.setMember(member);
        commentService.write(commentForm);
        redirectAttributes.addAttribute("boardId", commentForm.getBoard().getId());
        return "redirect:/boards/{boardId}";
    }

}
