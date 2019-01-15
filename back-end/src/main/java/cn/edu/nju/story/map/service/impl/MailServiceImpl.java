package cn.edu.nju.story.map.service.impl;

import cn.edu.nju.story.map.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author xuan
 * @create 2019-01-15 10:19
 **/
@Service
public class MailServiceImpl implements MailService {



    @Autowired
    JavaMailSender javaMailSender;

    @Override
    public void sendInvitation(Long userId, String username, String email) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        String from = ((JavaMailSenderImpl) javaMailSender).getJavaMailProperties().getProperty("from");
        String subject = ((JavaMailSenderImpl) javaMailSender).getJavaMailProperties().getProperty("subject");
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from);
            helper.setTo(email);
            helper.setSubject(subject);

            StringBuffer sb = new StringBuffer();
            sb.append("<h1>大标题-h1</h1>")
                    .append("<p style='color:#F00'>红色字</p>")
                    .append("<p style='text-align:right'>右对齐</p>");
            helper.setText(sb.toString(), true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException ignore) {
        }

    }
}
