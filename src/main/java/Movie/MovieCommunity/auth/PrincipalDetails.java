package Movie.MovieCommunity.auth;

import Movie.MovieCommunity.JPADomain.Member;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
@Data
public class PrincipalDetails implements UserDetails, OAuth2User {
    private Member member;
    private Map<String, Object> attributes;
    public PrincipalDetails(Member member) {
        this.member = member;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }
    public PrincipalDetails(Member member, Map<String, Object> attributes) {
        this.member = member;
        this.attributes = attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Collection<GrantedAuthority> collect = new ArrayList<>();
//        collect.add(new GrantedAuthority() {
//            @Override
//            public String getAuthority() {
////                return member.getAu
//            }
//        })
        return null;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }



    @Override
    public String getUsername() {
        return member.getUsername();
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


    @Override
    public String getName() {
        return null;
    }
}
