package cn.edu.nju.story.map.controller;

import cn.edu.nju.story.map.form.PageableForm;
import cn.edu.nju.story.map.form.RegisterForm;
import cn.edu.nju.story.map.form.LoginForm;
import cn.edu.nju.story.map.service.MailService;
import cn.edu.nju.story.map.service.UserService;
import cn.edu.nju.story.map.utils.OvalValidatorUtils;
import cn.edu.nju.story.map.utils.UserIdUtils;
import cn.edu.nju.story.map.vo.PageableVO;
import cn.edu.nju.story.map.vo.SimpleResponseVO;
import cn.edu.nju.story.map.vo.UserVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;

/**
 * @author xuan
 * @create 2019-01-05 15:43
 **/
@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    UserService userService;
    @Autowired
    MailService mailService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ApiOperation(value = "用户注册", response = UserVO.class)
    public SimpleResponseVO register(@RequestBody RegisterForm createUserForm){

        OvalValidatorUtils.validate(createUserForm);
        return SimpleResponseVO.OK(userService.register(createUserForm));
    }


    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiOperation(value = "获取用户信息", response = UserVO.class )
    public SimpleResponseVO queryUserById(ServletRequest request) {
        return SimpleResponseVO.OK(userService.queryUserDetailByUserId(UserIdUtils.praseUserIdFromRequest(request)));
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "用户登录, 返回的token放到Authorization头部中", response = String.class)
    public SimpleResponseVO login(@RequestBody LoginForm loginForm){

        OvalValidatorUtils.validate(loginForm);
        return SimpleResponseVO.OK(userService.login(loginForm.getEmail(), loginForm.getPassword()));
    }


    @RequestMapping(value = "/verify", method = RequestMethod.GET)
    @ApiOperation(value = "验证邮箱", response = boolean.class)
    public SimpleResponseVO verifyInvitationMail(@RequestParam String code){
        return SimpleResponseVO.OK(mailService.verifyInvitationMail(code));
    }


    @RequestMapping(value = "/search_by_email", method = RequestMethod.POST)
    @ApiOperation(value = "根据邮箱分页搜索用户，添加项目参与者时使用", response = UserVO.class)
    public SimpleResponseVO searchUserByEmail(@RequestParam String email,@RequestBody PageableForm pageableForm){
        OvalValidatorUtils.validate(pageableForm);
        return SimpleResponseVO.OK(userService.queryUserByEmail(email, new PageableVO(pageableForm)));

    }



}
