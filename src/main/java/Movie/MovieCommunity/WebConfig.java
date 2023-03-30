package Movie.MovieCommunity;

import Movie.MovieCommunity.web.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LoginCheckInterceptor())
//                .order(1)
//                .addPathPatterns("/**", "/boards/create", "/boards/**/update", "/comment/**")
//                .excludePathPatterns("/", "/member/**", "/boards","/boards/*");
//
//    }
    //    @Bean
//    public AuditorAware<String> auditorProvider(HttpServletRequest request){
//        HttpSession session = request.getSession(false);
//        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
//        return () -> Optional.of(UUID.randomUUID().toString());
////		return () -> Optional.of(member.getName());
//    }
}
