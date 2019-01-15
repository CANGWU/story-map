package cn.edu.nju.story.map.service.impl;

import cn.edu.nju.story.map.constants.ErrorCode;
import cn.edu.nju.story.map.constants.UserState;
import cn.edu.nju.story.map.entity.UserEntity;
import cn.edu.nju.story.map.exception.DefaultErrorException;
import cn.edu.nju.story.map.form.CreateUserForm;
import cn.edu.nju.story.map.repository.UserRepository;
import cn.edu.nju.story.map.service.MailService;
import cn.edu.nju.story.map.service.UserService;
import cn.edu.nju.story.map.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author xuan
 * @create 2019-01-05 15:54
 **/
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;
    @Autowired
    MailService mailService;

    @Override
    public UserVO register(CreateUserForm createUserForm) {


        if(userRepository.existsByEmail(createUserForm.getEmail())){
            throw new DefaultErrorException(ErrorCode.EMAIL_IS_REGISTERED);
        }

        UserEntity newUser = new UserEntity();
        // 保存到数据库
        // TODO 2019-01-05 应该有一些规则检查
        BeanUtils.copyProperties(createUserForm, newUser);
        newUser.setState(UserState.VERIFYING.getState());
        newUser = userRepository.save(newUser);

        // 发送一封邀请邮件
        mailService.sendInvitation(newUser.getId(), newUser.getUsername(), newUser.getEmail());

        return new UserVO(newUser);
    }

    @Override
    public UserVO queryUserDetailByUserId(Long userId) {

        Optional<UserEntity> userOptional = userRepository.findById(userId);

        return userOptional.map(UserVO::new).orElse(null);

    }
}
