package Movie.MovieCommunity.service;

import Movie.MovieCommunity.JPADomain.Member;
import Movie.MovieCommunity.JPARepository.MemberRepository;
import Movie.MovieCommunity.jwt.TokenDto;
import Movie.MovieCommunity.jwt.TokenProvider;
import Movie.MovieCommunity.web.dto.MemberRequestDto;
import Movie.MovieCommunity.web.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final AuthenticationManagerBuilder managerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;


    public MemberResponseDto signup(MemberRequestDto requestDto) {
        if (memberRepository.existsByEmail(requestDto.getEmail())) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다");
        }

        Member member = requestDto.toMember(passwordEncoder);
        return MemberResponseDto.of(memberRepository.save(member));
    }

    public TokenDto login(MemberRequestDto requestDto) {
        UsernamePasswordAuthenticationToken authenticationToken = requestDto.toAuthentication();
        System.out.println("authenticationToken = " + authenticationToken);
        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);
        System.out.println("authentication = " + authentication);
        Optional<Member> findMember = memberRepository.findByEmail(requestDto.getEmail());

        return tokenProvider.generateTokenDto(authentication, requestDto.getEmail(), findMember.get().getNickname());
    }

    public boolean checkId(String email){
        //System.out.println("memberRepository.existsByEmail(email) = " + memberRepository.existsByEmail(email));
        return memberRepository.existsByEmail(email);
    }

}
