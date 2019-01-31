package cn.edu.nju.story.map.vo;

import cn.edu.nju.story.map.constants.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xuan
 * @create 2019-01-30 21:45
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimpleResponseVO {

    Integer code;

    Object data;


    public static SimpleResponseVO OK(Object data){
        return new SimpleResponseVO(ErrorCode.OK.getCode(), data);
    }


}
