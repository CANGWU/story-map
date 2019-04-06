package cn.edu.nju.story.map.utils;

import cn.edu.nju.story.map.constants.ErrorCode;
import cn.edu.nju.story.map.exception.DefaultErrorException;
import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * OvalValidatorUtils
 *
 * @author xuan
 * @date 2018/12/10
 */
public class OvalValidatorUtils {


    private static final Validator VALIDATOR = new Validator();


    private OvalValidatorUtils(){}

    public static void validate(Object form){
        List<ConstraintViolation> ret = VALIDATOR.validate(form);
        if(!CollectionUtils.isEmpty(ret)){
            throw new DefaultErrorException(ErrorCode.BAD_REQUEST);
        }

    }


}
