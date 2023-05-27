package Movie.MovieCommunity.config.security.auth;

import Movie.MovieCommunity.JPADomain.Provider;
import Movie.MovieCommunity.advice.assertThat.DefaultAssert;
import Movie.MovieCommunity.config.security.auth.provider.Google;
import Movie.MovieCommunity.config.security.auth.provider.Kakao;
import Movie.MovieCommunity.config.security.auth.provider.Naver;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if(registrationId.equalsIgnoreCase(Provider.google.toString())) {
            return new Google(attributes);
        } else if (registrationId.equalsIgnoreCase(Provider.naver.toString())) {
            return new Naver(attributes);
        } else if (registrationId.equalsIgnoreCase(Provider.kakao.toString())) {
            return new Kakao(attributes);
        } else {
            DefaultAssert.isAuthentication("해당 oauth2 기능은 지원하지 않습니다.");
        }
        return null;
    }
}
