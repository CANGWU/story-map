package cn.edu.nju.story.map.form;

import lombok.Data;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

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
    private String email;

    /**
     * 手机号
     */
    @NotBlank
    @NotNull
    private String phone;

}
