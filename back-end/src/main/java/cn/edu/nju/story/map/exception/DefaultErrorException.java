package cn.edu.nju.story.map.exception;

import cn.edu.nju.story.map.constants.ErrorCode;
import lombok.Getter;

/**
 * @author xuan
 * @create 2019-01-05 16:31
 **/
@Getter
public class DefaultErrorException extends RuntimeException {

    /**
     * 错误码
     */
    private final ErrorCode errorCode;

    public DefaultErrorException(ErrorCode errorCode){
        super(errorCode.getDescription());
        this.errorCode = errorCode;

    }



}
