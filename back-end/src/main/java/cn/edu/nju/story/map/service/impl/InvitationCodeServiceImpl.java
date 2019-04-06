package cn.edu.nju.story.map.service.impl;

import cn.edu.nju.story.map.constants.ErrorCode;
import cn.edu.nju.story.map.exception.DefaultErrorException;
import cn.edu.nju.story.map.service.InvitationCodeService;
import cn.edu.nju.story.map.utils.RandomValueStringGenerator;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xuan
 * @create 2019-01-15 10:27
 **/

@Service
public class InvitationCodeServiceImpl implements InvitationCodeService {


    private RandomValueStringGenerator generator = new RandomValueStringGenerator();
    protected final ConcurrentHashMap<String, Long> invitationCodeStore = new ConcurrentHashMap<>();



    @Override
    public String generateInvitationCode(Long userId) {

        String code = generator.generate();

        invitationCodeStore.put(code, userId);

        return code;
    }

    @Override
    public Long consumeInvitationCode(String code) {

        Long userId = invitationCodeStore.remove(code);

        if(Objects.isNull(userId)){
            throw new DefaultErrorException(ErrorCode.INVALID_INVITATION_CODE);
        }

        return userId;
    }
}
