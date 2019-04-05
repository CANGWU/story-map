package cn.edu.nju.story.map.form;

import lombok.Data;
import net.sf.oval.constraint.MatchPattern;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;


/**
 * @author xuan
 * @create 2019-01-05 15:39
 **/

@Data
public class RegisterForm {


    /**
     * 用户名
     */
    @NotBlank
    @NotNull
    private String username;

    /**
     * 密码
     */
    @NotBlank
    @NotNull
    private String password;

    /**
     * 邮箱
     */
    @NotBlank
    @NotNull
    @MatchPattern(message = "Not Email!",  pattern = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$")
    private String email;



}
