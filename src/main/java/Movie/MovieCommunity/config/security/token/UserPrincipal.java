package Movie.MovieCommunity.config.security.token;

import Movie.MovieCommunity.JPADomain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

@Slf4j
public class UserPrincipal implements OAuth2User, UserDetails {

    private Long id;
    private String email;
    private String name;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    private Member member;

<<<<<<< HEAD

=======
>>>>>>> 84e9f5fe60ac96a6fccd7556886de3e6e2489a12
    public UserPrincipal(Long id, String email, String name, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserPrincipal create(Member member) {
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(member.getAuthority().getValue()));
        return new UserPrincipal(
                member.getId(),
                member.getEmail(),
                member.getName(),
                member.getPassword(),
                authorities
        );
    }

    @Override
    public String toString() {
        return "UserPrincipal{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", attributes=" + attributes +
                '}';
    }

    public static UserPrincipal create(Member member, Map<String, Object> attributes) {
        log.info("사용자 정보 세팅");
        log.info("member = {}" , member);
        UserPrincipal userPrincipal = UserPrincipal.create(member);
        userPrincipal.setAttributes(attributes);
        log.info("userPrincipal = " + userPrincipal);
        return userPrincipal;
    }

    public Member getMember() {
        return member;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Member getMember() {
        return member;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
