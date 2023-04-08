package Movie.MovieCommunity.service;

import Movie.MovieCommunity.JPADomain.Member;
import Movie.MovieCommunity.JPARepository.MemberRepository;
import Movie.MovieCommunity.config.SecurityUtil;
import Movie.MovieCommunity.web.dto.MemberResponseDto;
import Movie.MovieCommunity.web.form.AddMemberForm;
import Movie.MovieCommunity.web.form.LoginForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberResponseDto getMyInfoBySecurity() {
        return memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .map(MemberResponseDto::of)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
    }

    @Transactional
    public MemberResponseDto changeMemberNickname(String email, String nickname) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
        member.setNickname(nickname);
        return MemberResponseDto.of(memberRepository.save(member));
    }

    @Transactional
    public MemberResponseDto changeMemberPassword(String email, String exPassword, String newPassword) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
        if (!passwordEncoder.matches(exPassword, member.getPassword())) {
            throw new RuntimeException("비밀번호가 맞지 않습니다");
        }
        member.setPassword(passwordEncoder.encode((newPassword)));
        return MemberResponseDto.of(memberRepository.save(member));




//    public Member join(AddMemberForm memberForm){
//        Optional<Member> getMember = memberRepository.findByEmail(memberForm.getEmail());
//        System.out.println(getMember.isEmpty());
//        if (getMember.isEmpty()) {
//            Member member = new Member(memberForm);
//            memberRepository.save(member);
//            return member;
//        }
//        else{
//            return null;
//        }
//    }
//    public Member login(LoginForm loginForm){
//        Optional<Member> getMember = memberRepository.findByEmail(loginForm.getEmail());
//        if(getMember.isPresent()){
//            Member member = getMember.get();
//            if (member.getPassword().equals(loginForm.getPassword())){
//                return member;
//            }
//            else{
//                return null;
//            }
//        }
//        else{
//            return null;
//        }


    }
}
