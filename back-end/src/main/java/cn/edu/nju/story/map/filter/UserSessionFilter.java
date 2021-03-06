package cn.edu.nju.story.map.filter;

import cn.edu.nju.story.map.constants.ErrorCode;
import cn.edu.nju.story.map.exception.DefaultErrorException;
import cn.edu.nju.story.map.utils.JwtGenerator;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

import static cn.edu.nju.story.map.utils.UserIdUtils.USER_ID_PARAMETER_NAME;

/**
 * UserSessionFilter
 *
 * @author xuan
 * @date 2019-01-16
 */

@Order(1)
@WebFilter(filterName = "user-session", urlPatterns = "/*")
public class UserSessionFilter implements Filter {


     private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList("/user/register", "/user/login", "/user/verify", "/swagger-ui.html")));




    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        boolean isAllow = isAllowPath(request.getRequestURI(), request.getContextPath());

        if(!isAllow){
            String jwt = request.getHeader("Authorization");
            if(Objects.nonNull(jwt)){
                try{
                    String userId = JwtGenerator.verifyJwt(jwt);
                    request.setAttribute(USER_ID_PARAMETER_NAME, userId);
                }catch (Exception ignore){
                    throw new DefaultErrorException(ErrorCode.UNAUTHORIZED);
                }
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }


    private boolean isAllowPath(String path, String contextPath){

        String shortPath = path.substring(contextPath.length()).replaceAll("[/]+$", "");
        return ALLOWED_PATHS.contains(shortPath);
    }
}
