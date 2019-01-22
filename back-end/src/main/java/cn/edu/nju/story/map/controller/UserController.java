package cn.edu.nju.story.map.controller;

import cn.edu.nju.story.map.form.RegisterForm;
import cn.edu.nju.story.map.form.LoginForm;
import cn.edu.nju.story.map.service.UserService;
import cn.edu.nju.story.map.utils.OvalValidatorUtils;
import cn.edu.nju.story.map.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author xuan
 * @create 2019-01-05 15:43
 **/
@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public UserVO register(@RequestBody RegisterForm createUserForm){

        OvalValidatorUtils.validate(createUserForm);
        return userService.register(createUserForm);
    }


    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public UserVO queryUserById(@PathVariable("userId") Long userId) {

        return userService.queryUserDetailByUserId(userId);
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody LoginForm loginForm){

        return userService.login(loginForm.getEmail(), loginForm.getPassword());
    }


}
