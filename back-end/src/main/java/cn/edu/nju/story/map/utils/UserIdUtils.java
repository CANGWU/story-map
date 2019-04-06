package cn.edu.nju.story.map.utils;

import cn.edu.nju.story.map.constants.ErrorCode;
import cn.edu.nju.story.map.exception.DefaultErrorException;

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

    private UserIdUtils(){}

    /**
     * 从请求头中获取用户Id
     * @param request
     * @return
     */
    public static long praseUserIdFromRequest(ServletRequest request){
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            return Long.valueOf(httpServletRequest.getAttribute(USER_ID_PARAMETER_NAME).toString());
        }catch (Exception e){
            throw new DefaultErrorException(ErrorCode.FORBIDDEN);
        }

    }

}
