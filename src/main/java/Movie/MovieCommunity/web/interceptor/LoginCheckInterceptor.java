package Movie.MovieCommunity.web.interceptor;

import Movie.MovieCommunity.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        String requestURI = request.getRequestURI();
        if (session==null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null){
            String contextPath = request.getQueryString();
            if(contextPath == null) {
                response.sendRedirect("/member/login?RedirectURL="+requestURI);
            } else{
                response.sendRedirect("/member/login?RedirectURL="+requestURI+"&"+contextPath);
            }
            return false;
        }

        return true;
    }
}
