package cn.edu.nju.story.map.config;


import cn.edu.nju.story.map.constants.ErrorCode;
import cn.edu.nju.story.map.exception.DefaultErrorException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

/**
 * ExceptionResolver
 * 异常处理切面
 * @author xuan
 * @date 2018/8/2
 */
@Configuration
@Slf4j
public class ExceptionResolverConfig extends SimpleMappingExceptionResolver {


    private ObjectMapper objectMapper;

    public ExceptionResolverConfig() {
        objectMapper = new ObjectMapper();
    }


    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
                                         Object handler, Exception ex) {
        response.setStatus(200);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-cache, must-revalidate");
        Map<String, Object> map = new HashMap<>();
        if (ex instanceof NullPointerException) {
            map.put("code", ErrorCode.NP_EXCEPTION);
        } else if (ex instanceof ClassCastException) {
            map.put("code", ErrorCode.CLASS_CAST_EXCEPTION);
        } else if (ex instanceof IllegalArgumentException) {
            map.put("code", ErrorCode.ILLEGAL_ARGUMENT_EXCEPTION);
        } else if (ex instanceof ArrayStoreException) {
            map.put("code", ErrorCode.ARRAY_STORE_EXCEPTION);
        } else if (ex instanceof IndexOutOfBoundsException) {
            map.put("code", ErrorCode.INDEX_OUT_OF_BOUNDS_EXCEPTION);
        } else if (ex instanceof SecurityException) {
            map.put("code", ErrorCode.SECURITY_EXCEPTION);
        } else if (ex instanceof UnsupportedOperationException) {
            map.put("code", ErrorCode.UNSUPPORTED_OPERATION_EXCEPTION);
        } else if(ex instanceof AccessDeniedException) {
            map.put("code", ErrorCode.FORBIDDEN);
        } else if(ex instanceof DefaultErrorException) {
            map.put("code", ((DefaultErrorException) ex).getErrorCode().getCode());
        } else {
            map.put("code", ErrorCode.CATCH_EXCEPTION);
        }

        String url = request.getRequestURL().toString();
        try {

            map.put("data", ex.getMessage());
            response.getWriter().write(objectMapper.writeValueAsString(map));
        } catch (Exception ignore) {
            logger.error("Something happened in wrap ex！");
        }
        logger.error(url, ex);
        return new ModelAndView();
    }
}
