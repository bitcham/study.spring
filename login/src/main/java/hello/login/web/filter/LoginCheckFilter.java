package hello.login.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@Slf4j
public class LoginCheckFilter implements Filter {

    private static final String[] whitelist = {
            "/",
            "/members/add",
            "/login",
            "/logout",
            "/css/*"
    };

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String requestURI = httpServletRequest.getRequestURI();

        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        try{
            log.info("Authentication filter start {}", requestURI);
            if(isLoginCheckPath(requestURI)){
                log.info("Authentication logic execute {}", requestURI);
                HttpSession session = httpServletRequest.getSession(false);
                if(session == null || session.getAttribute("loginMember") == null){
                    log.info("Unauthorized user {}", requestURI);
                    httpServletResponse.sendRedirect("/login?redirectURL=" + requestURI);
                    return;
                }

            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            throw e;
        } finally {
            log.info("Authentication filter end {}", requestURI);
        }
    }


    /**
     * Check if the request URI is in the whitelist.
     * @param requestURI
     * @return true if the request URI is not in the whitelist, false otherwise
     */
    private boolean isLoginCheckPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
    }
}
