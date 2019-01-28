package cn.edu.nju.story.map.utils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * UserIdUtils
 *
 * @author xuan
 * @date 2019-01-26
 */
public class UserIdUtils {



    public static final String USER_ID_PARAMETER_NAME = "current_user_id";

    /**
     * 从请求头中获取用户Id
     * @param request
     * @return
     */
    public static long praseUserIdFromRequest(ServletRequest request){
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        return Long.parseLong(httpServletRequest.getParameter(USER_ID_PARAMETER_NAME));
    }

}
