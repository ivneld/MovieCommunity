package Movie.MovieCommunity.web.dto;

import Movie.MovieCommunity.JPADomain.Authority;
import Movie.MovieCommunity.JPADomain.Member;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MemberRequestDto {

    private String email;
    private String password;
    private String nickname;

    public Member toMember(PasswordEncoder passwordEncoder) {
        return new Member(email, passwordEncoder.encode(password),nickname,Authority.ROLE_USER);
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}
