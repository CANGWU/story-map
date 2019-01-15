package cn.edu.nju.story.map.service.impl;

import cn.edu.nju.story.map.service.InvitationCodeService;
import cn.edu.nju.story.map.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresource.ClassLoaderTemplateResource;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xuan
 * @create 2019-01-15 10:19
 **/
@Service
public class MailServiceImpl implements MailService {



    @Autowired
    JavaMailSender javaMailSender;

    @Value("${server.url}")
    private String serverUrl;

    private final static TemplateEngine TEMPLATE_ENGINE = new TemplateEngine();

    @Autowired
    InvitationCodeService invitationCodeService;

    @PostConstruct
    public void init(){
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("/static/");
        resolver.setSuffix(".html");
        TEMPLATE_ENGINE.setTemplateResolver(resolver);
    }

    @Override
    public void sendInvitation(Long userId, String username, String email) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        String from = ((JavaMailSenderImpl) javaMailSender).getUsername();
        String subject = ((JavaMailSenderImpl) javaMailSender).getJavaMailProperties().getProperty("subject");
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from);
            helper.setTo(email);
            helper.setSubject(subject);

            Map<String, Object> params = new HashMap<>();
            params.put("email_address", email);
            params.put("confirm_url", serverUrl + invitationCodeService.generateInvitationCode(userId));

            helper.setText(processEmailTemplate(params, "IStoryInvitation"), true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException ignore) {
        }

    }


    private String processEmailTemplate(Map<String, Object> params, String templateName){

        Context context = new Context();
        context.setVariables(params);
        return TEMPLATE_ENGINE.process(templateName, context);

    }
}
