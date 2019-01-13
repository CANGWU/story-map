package cn.edu.nju.story.map.form;

import lombok.Data;
import net.sf.oval.constraint.MatchPattern;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNull;

import java.util.regex.Pattern;

/**
 * @author xuan
 * @create 2019-01-05 15:39
 **/

@Data
public class CreateUserForm {


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


    public static void main(String[]args){

        System.out.println(Pattern.matches("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$", "1533704796@qq.com"));



    }


}
