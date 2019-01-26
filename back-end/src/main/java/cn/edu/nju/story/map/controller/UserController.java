package cn.edu.nju.story.map.controller;

import cn.edu.nju.story.map.form.PageableForm;
import cn.edu.nju.story.map.form.RegisterForm;
import cn.edu.nju.story.map.form.LoginForm;
import cn.edu.nju.story.map.service.MailService;
import cn.edu.nju.story.map.service.UserService;
import cn.edu.nju.story.map.utils.OvalValidatorUtils;
import cn.edu.nju.story.map.utils.UserIdUtils;
import cn.edu.nju.story.map.vo.PageableVO;
import cn.edu.nju.story.map.vo.UserVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

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
    public UserVO register(@RequestBody RegisterForm createUserForm){

        OvalValidatorUtils.validate(createUserForm);
        return userService.register(createUserForm);
    }


    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiOperation(value = "获取用户信息", response = UserVO.class )
    public UserVO queryUserById(ServletRequest request) {
        return userService.queryUserDetailByUserId(UserIdUtils.praseUserIdFromRequest(request));
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "用户登录, 返回的token放到Authorization头部中", response = String.class)
    public String login(@RequestBody LoginForm loginForm){
        return userService.login(loginForm.getEmail(), loginForm.getPassword());
    }


    @RequestMapping(value = "/verify", method = RequestMethod.GET)
    @ApiOperation(value = "验证邮箱", response = boolean.class)
    public boolean verifyInvitationMail(@RequestParam String code){
        return mailService.verifyInvitationMail(code);
    }


    @RequestMapping(value = "/search_by_email", method = RequestMethod.GET)
    @ApiOperation(value = "根据邮箱分页搜索用户，添加项目参与者时使用", response = UserVO.class)
    public Page<UserVO> searchUserByEmail(@RequestParam String email,@RequestBody PageableForm pageableForm){
        OvalValidatorUtils.validate(pageableForm);
        return userService.queryUserByEmail(email, new PageableVO(pageableForm));

    }



}
