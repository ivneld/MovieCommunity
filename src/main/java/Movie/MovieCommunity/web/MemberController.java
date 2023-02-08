package Movie.MovieCommunity.web;

import Movie.MovieCommunity.JPADomain.Member;
import Movie.MovieCommunity.service.MemberService;
import Movie.MovieCommunity.web.form.AddMemberForm;
import Movie.MovieCommunity.web.form.LoginForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/member/add")
    public String addForm(@ModelAttribute("member") AddMemberForm memberForm){
        return "addMember";
    }
    @PostMapping("/member/add")
    public String save(@ModelAttribute("member") @Valid AddMemberForm memberForm, BindingResult bindingResult, HttpServletRequest request){
        log.info("member = {}", memberForm);
            if (bindingResult.hasErrors()){
                return "addMember";
            }
            Member member = memberService.join(memberForm);
            if (member == null){
                bindingResult.reject("joinFail","이미 있는 이메일입니다.");
                return "addMember";
            }

            request.setAttribute(SessionConst.LOGIN_MEMBER, member);
            return "redirect:/";
    }

    @GetMapping("/member/login")
    public String loginForm(@ModelAttribute LoginForm loginForm,@SessionAttribute(name="loginMember", required = false) Member loginMember){
        if (loginMember != null){
            return "redirect:/";
        }
        return "login";
    }
    @PostMapping("/member/login")
    public String login(@ModelAttribute @Valid LoginForm loginForm, BindingResult bindingResult, HttpServletRequest request){
        if (bindingResult.hasErrors()){
            return "login";
        }
        Member login = memberService.login(loginForm);
        if (login == null){
            bindingResult.reject("loginFail", "입력한 회원 정보가 아닙니다.");
            return "login";
        }
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, login);
        return "redirect:/";
    }

}
