package cn.edu.nju.story.map.controller;

import cn.edu.nju.story.map.form.CreateUserForm;
import cn.edu.nju.story.map.service.UserService;
import cn.edu.nju.story.map.utils.OvalValidatorUtils;
import cn.edu.nju.story.map.vo.UserVO;
import com.alibaba.fastjson.JSON;
import net.sf.oval.ConstraintViolation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public UserVO register(@RequestBody CreateUserForm createUserForm){

        OvalValidatorUtils.validate(createUserForm);
        return userService.register(createUserForm);
    }


    @RequestMapping("/{userId}")
    public UserVO queryUserById(@PathVariable("userId") Long userId) {

        return userService.queryUserDetailByUserId(userId);
    }


}
