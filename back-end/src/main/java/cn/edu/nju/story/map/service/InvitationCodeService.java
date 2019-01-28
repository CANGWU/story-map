package cn.edu.nju.story.map.service;

/**
 * @author xuan
 * @create 2019-01-15 10:18
 **/
public interface InvitationCodeService {


    /**
     * 生成邀请码
     * @param userId
     * @return
     */
    public String generateInvitationCode(Long userId);

    /**
     * 验证邀请码并获取用户Id
     * @param code
     * @return
     */
    public Long consumeInvitationCode(String code);


}
