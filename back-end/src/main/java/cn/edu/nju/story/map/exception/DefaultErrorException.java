package cn.edu.nju.story.map.exception;

import io.swagger.models.auth.In;

/**
 * @author xuan
 * @create 2019-01-05 16:31
 **/
public class DefaultErrorException extends RuntimeException {

    /**
     * 错误码
     */
    private Integer code;

    public DefaultErrorException(Integer code, String description){
        super(description);
        this.code = code;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
