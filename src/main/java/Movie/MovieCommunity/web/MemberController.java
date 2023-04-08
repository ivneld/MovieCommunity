package Movie.MovieCommunity.web;

import Movie.MovieCommunity.service.MemberService;
import Movie.MovieCommunity.web.dto.MemberRequestDto;
import Movie.MovieCommunity.web.dto.MemberResponseDto;
import Movie.MovieCommunity.web.form.AddMemberForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/loginForm")
    public String addForm(@ModelAttribute("member") AddMemberForm memberForm){
        return "addMember";
    }








    @GetMapping("/me")
    public ResponseEntity<MemberResponseDto> getMyMemberInfo() {
        MemberResponseDto myInfoBySecurity = memberService.getMyInfoBySecurity();
        System.out.println(myInfoBySecurity.getNickname());
        return ResponseEntity.ok((myInfoBySecurity));
        // return ResponseEntity.ok(memberService.getMyInfoBySecurity());
    }

    @PostMapping("/nickname")
    public ResponseEntity<MemberResponseDto> setMemberNickname(@RequestBody MemberRequestDto request) {
        return ResponseEntity.ok(memberService.changeMemberNickname(request.getEmail(), request.getNickname()));
    }

//    @PostMapping("/password")
//    public ResponseEntity<MemberResponseDto> setMemberPassword(@RequestBody ChangePasswordRequestDto request) {
//        return ResponseEntity.ok(memberService.changeMemberPassword(request.getExPassword(), request.getNewPassword()));
//    }
//    @PostMapping("/add")
//    public String save(@ModelAttribute("member") @Valid AddMemberForm memberForm, BindingResult bindingResult, HttpServletRequest request){
//        log.info("member = {}", memberForm);
//            if (bindingResult.hasErrors()){
//                return "addMember";
//            }
//            Member member = memberService.join(memberForm);
//            if (member == null){
//                bindingResult.reject("joinFail","이미 있는 이메일입니다.");
//                return "addMember";
//            }
//
//            request.setAttribute(SessionConst.LOGIN_MEMBER, member);
//            return "redirect:/";
//    }
//
//    @GetMapping("/login")
//    public String loginForm(@ModelAttribute LoginForm loginForm,@SessionAttribute(name="loginMember", required = false) Member loginMember){
//        if (loginMember != null){
//            return "redirect:/";
//        }
//        return "login";
//    }
//    @PostMapping("/login")
//    public String login(@ModelAttribute @Valid LoginForm loginForm, BindingResult bindingResult, HttpServletRequest request){
//        if (bindingResult.hasErrors()){
//            return "login";
//        }
//        Member login = memberService.login(loginForm);
//        if (login == null){
//            bindingResult.reject("loginFail", "입력한 회원 정보가 아닙니다.");
//            return "login";
//        }
//        HttpSession session = request.getSession();
//        session.setAttribute(SessionConst.LOGIN_MEMBER, login);
//        return "redirect:/";
//    }
//
//    @PostMapping("/logout")
//    public String logout(HttpServletRequest request){
//        HttpSession session = request.getSession(false);
//        if (session != null){
//            session.invalidate();
//        }
//        return "redirect:/";
//    }

}
