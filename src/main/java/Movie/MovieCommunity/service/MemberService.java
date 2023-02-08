package Movie.MovieCommunity.service;

import Movie.MovieCommunity.JPADomain.Member;
import Movie.MovieCommunity.JPARepository.MemberRepository;
import Movie.MovieCommunity.web.form.AddMemberForm;
import Movie.MovieCommunity.web.form.LoginForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    public Member join(AddMemberForm memberForm){
        Optional<Member> getMember = memberRepository.findByEmail(memberForm.getEmail());
        System.out.println(getMember.isEmpty());
        if (getMember.isEmpty()) {
            Member member = new Member(memberForm);
            memberRepository.save(member);
            return member;
        }
        else{
            return null;
        }
    }
    public Member login(LoginForm loginForm){
        Optional<Member> getMember = memberRepository.findByEmail(loginForm.getEmail());
        if(getMember.isPresent()){
            Member member = getMember.get();
            if (member.getPassword().equals(loginForm.getPassword())){
                return member;
            }
            else{
                return null;
            }
        }
        else{
            return null;
        }
    }
}
