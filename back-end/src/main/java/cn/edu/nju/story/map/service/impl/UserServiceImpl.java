package cn.edu.nju.story.map.service.impl;

import cn.edu.nju.story.map.constants.ErrorCode;
import cn.edu.nju.story.map.constants.UserState;
import cn.edu.nju.story.map.entity.UserEntity;
import cn.edu.nju.story.map.exception.DefaultErrorException;
import cn.edu.nju.story.map.form.RegisterForm;
import cn.edu.nju.story.map.repository.UserRepository;
import cn.edu.nju.story.map.service.MailService;
import cn.edu.nju.story.map.service.UserService;
import cn.edu.nju.story.map.utils.BeanUtils;
import cn.edu.nju.story.map.utils.JwtGenerator;
import cn.edu.nju.story.map.vo.PageableVO;
import cn.edu.nju.story.map.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

/**
 * @author xuan
 * @create 2019-01-05 15:54
 **/
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MailService mailService;

    @Override
    public UserVO register(RegisterForm createUserForm) {

        if(userRepository.existsByEmail(createUserForm.getEmail())){
            throw new DefaultErrorException(ErrorCode.EMAIL_IS_REGISTERED);
        }

        UserEntity newUser = new UserEntity();
        // 保存到数据库
        BeanUtils.copyProperties(createUserForm, newUser);
        newUser.setState(UserState.VERIFYING.getState());
        newUser = userRepository.save(newUser);

        // 发送一封邀请邮件
        mailService.sendInvitation(newUser.getId(), newUser.getUsername(), newUser.getEmail());

        // 返回用户信息和用户token

        return new UserVO(newUser, JwtGenerator.generateJwtString(newUser.getId().toString()));
    }

    @Override
    public UserVO queryUserDetailByUserId(Long userId) {

        Optional<UserEntity> userOptional = userRepository.findById(userId);

        return userOptional.map(UserVO::new).orElse(null);

    }


    @Override
    public String login(String email, String password){


        UserEntity user = userRepository.findByEmail(email);

        if(Objects.isNull(user)){
            throw new DefaultErrorException(ErrorCode.USER_NOT_EXIST);
        }
        if(Objects.equals(password, user.getPassword())){

            return JwtGenerator.generateJwtString(user.getId().toString());
        }

        throw new DefaultErrorException(ErrorCode.USERNAME_OR_PASSWORD_ERROR);

    }

    @Override
    public Page<UserVO> queryUserByEmail(String email, PageableVO pageable) {

        Pageable p = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());

        return userRepository.findByEmailIsLike(email, p).map(UserVO::new);
    }


}
