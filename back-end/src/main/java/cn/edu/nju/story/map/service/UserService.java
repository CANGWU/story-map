package cn.edu.nju.story.map.service;

import cn.edu.nju.story.map.form.RegisterForm;
import cn.edu.nju.story.map.vo.UserVO;

/**
 * @author xuan
 * @create 2019-01-05 15:51
 **/
public interface UserService {


    /**
     * 注册一个新的用户,同时发送一封验证邮件
     * @param createUserForm
     * @return 返回用户token
     */
    UserVO register(RegisterForm createUserForm);

    /**
     * 根据userId获取用户详细信息
     * @param userId
     * @return
     */
    UserVO queryUserDetailByUserId(Long userId);


    /**
     * 用户登录，返回令牌
     * @param email
     * @param password
     * @return
     */
    String login(String email, String password);
}
