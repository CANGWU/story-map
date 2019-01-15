package cn.edu.nju.story.map.service.impl;

import cn.edu.nju.story.map.service.InvitationCodeService;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.stereotype.Service;

/**
 * @author xuan
 * @create 2019-01-15 10:27
 **/

@Service
public class InvitationCodeServiceImpl implements InvitationCodeService {


    private JwtHelper jwtHelper = new JwtHelper();


    @Override
    public String generateInvitationCode(Long userId) {
        return null;
    }

    @Override
    public Long consumeInvitationCode(String code) {
        return null;
    }
}
