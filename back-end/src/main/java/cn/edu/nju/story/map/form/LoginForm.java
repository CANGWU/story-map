package cn.edu.nju.story.map.form;

import lombok.Data;
import net.sf.oval.constraint.MatchPattern;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

/**
 * LoginForm
 *
 * @author xuan
 * @date 2019-01-15
 */
@Data
public class LoginForm {


    /**
     * 登录邮箱
     */
    @NotBlank
    @NotNull
    @MatchPattern(message = "Not Email!",  pattern = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$")
    private String email;


    /**
     * 登录密码
     */
    @NotBlank
    @NotNull
    private String password;

}
