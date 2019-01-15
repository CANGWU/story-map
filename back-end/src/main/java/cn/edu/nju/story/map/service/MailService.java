package cn.edu.nju.story.map.service;

/**
 * @author xuan
 * @create 2019-01-15 10:16
 **/
public interface MailService {


    /**
     * 发送邀请邮件
     */
    void sendInvitation(Long userId, String username, String email);

}
