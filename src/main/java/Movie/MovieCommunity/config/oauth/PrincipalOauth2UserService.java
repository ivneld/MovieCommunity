package Movie.MovieCommunity.config.oauth;

import Movie.MovieCommunity.JPADomain.Member;
import Movie.MovieCommunity.JPARepository.MemberRepository;
import Movie.MovieCommunity.auth.PrincipalDetails;
import Movie.MovieCommunity.config.oauth.provider.GoogleUserInfo;
import Movie.MovieCommunity.config.oauth.provider.NaverUserInfo;
import Movie.MovieCommunity.config.oauth.provider.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("userRequest = " + userRequest.getClientRegistration());
        System.out.println("userRequest = " + userRequest.getAccessToken());


        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("userRequest = " + oAuth2User.getAttributes());

        OAuth2UserInfo oAuth2UserInfo = null;
        if(userRequest.getClientRegistration().getRegistrationId().equals("google")){
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        }else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")){
            oAuth2UserInfo = new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));
        }

        String provider = oAuth2UserInfo.getProvider();
        String providerId = oAuth2UserInfo.getProviderId();
        String username = provider + "_" + providerId;// google_ID;

        String email = oAuth2UserInfo.getEmail();
        String name = oAuth2UserInfo.getName();
        String role = "ROLE_USER";

        Optional<Member> findMember = memberRepository.findByUsername(username);
        Member member = null;
        if(findMember.isEmpty()){
            member = Member.builder()
                    .username(username)
                    .name(name)
                    .email(email)
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            memberRepository.save(member);
        }else{
            member=findMember.get();
        }

        return new PrincipalDetails(member, oAuth2User.getAttributes());
    }
}
