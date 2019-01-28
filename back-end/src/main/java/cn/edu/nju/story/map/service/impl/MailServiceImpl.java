package cn.edu.nju.story.map.service.impl;

import cn.edu.nju.story.map.constants.ErrorCode;
import cn.edu.nju.story.map.constants.UserState;
import cn.edu.nju.story.map.entity.UserEntity;
import cn.edu.nju.story.map.exception.DefaultErrorException;
import cn.edu.nju.story.map.repository.UserRepository;
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
import java.util.Objects;
import java.util.Optional;

/**
 * @author xuan
 * @create 2019-01-15 10:19
 **/
@Service
public class MailServiceImpl implements MailService {



    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    UserRepository userRepository;

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

    @Override
    public boolean verifyInvitationMail(String code) {

        Long userId = invitationCodeService.consumeInvitationCode(code);

        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);


        if(userEntityOptional.isPresent()){

            UserEntity userEntity = userEntityOptional.get();
            if(!Objects.equals(userEntity.getState(), UserState.OK.getState())){
                userEntity.setState(UserState.OK.getState());
                userRepository.save(userEntity);
            }
        }else {
            throw new DefaultErrorException(ErrorCode.USER_NOT_EXIST);
        }


        return true;
    }


    private String processEmailTemplate(Map<String, Object> params, String templateName){

        Context context = new Context();
        context.setVariables(params);
        return TEMPLATE_ENGINE.process(templateName, context);

    }
}
