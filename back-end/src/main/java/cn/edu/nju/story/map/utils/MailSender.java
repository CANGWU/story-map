package cn.edu.nju.story.map.utils;


import lombok.Data;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @author xuan
 * @create 2018-10-18 00:27
 **/


@Data
public class MailSender {

    private Properties prop = new Properties();
    private Session session;
    private Message msg;
    private Transport transport;


    private String mailContent;
    private String toAddress;

    private String debug;
    private String enableSSL;
    private String auth;
    private String host;
    private String protocol;

    private String subject;
    //发件人地址
    private String fromAddress;
    //发件账户密码
    private String fromPassword;
    private String fromPerson;
    private String port;


    private MailSender(){

    }

    public static class Builder {
        MailSender mailSender = new MailSender();

        public Builder debug(String debug) {
            mailSender.debug = debug;
            return this;
        }

        public Builder subject(String subject) {
            mailSender.subject = subject;
            return this;
        }

        public Builder auth(String auth) {
            mailSender.auth = auth;
            return this;
        }

        public Builder host(String host) {
            mailSender.host = host;
            return this;
        }

        public Builder port(String port){
            mailSender.port = port;
            return this;
        }


        public Builder fromPerson(String fromPerson) {
            mailSender.fromPerson = fromPerson;
            return this;
        }

        public Builder fromAddress(String fromAddress) {
            mailSender.fromAddress = fromAddress;
            return this;
        }

        public Builder fromPassword(String fromPassword) {
            mailSender.fromPassword = fromPassword;
            return this;
        }

        public Builder toAddress(String toAddress) {
            mailSender.toAddress = toAddress;
            return this;
        }


        public Builder mailContent(String mailContent){
            mailSender.mailContent = mailContent;
            return this;
        }

        public Builder enableSSL(String enableSSL){
            mailSender.enableSSL = enableSSL;
            return this;
        }



        public Builder protocol(String protocol) {
            mailSender.protocol = protocol;
            return this;
        }

        public MailSender build() throws Exception {
            mailSender.prop.setProperty("mail.debug", mailSender.debug);
            mailSender.prop.setProperty("mail.smtp.auth", mailSender.auth);
            mailSender.prop.setProperty("mail.host", mailSender.host);
            mailSender.prop.setProperty("mail.transport.protocol", mailSender.protocol);
            mailSender.prop.setProperty("mail.smtp.ssl.enable", mailSender.enableSSL);
            mailSender.prop.setProperty("mail.smtp.port", mailSender.port);
            mailSender.prop.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

            mailSender.session = Session.getInstance(mailSender.prop);
            mailSender.msg = new MimeMessage(mailSender.session);
            mailSender.transport = mailSender.session.getTransport();
            mailSender.msg.setSubject(mailSender.subject);
            mailSender.msg.setRecipient(Message.RecipientType.TO, new InternetAddress(mailSender.toAddress));
            mailSender.msg.setFrom(new InternetAddress(mailSender.fromAddress, mailSender.fromPerson));
            mailSender.transport.connect(mailSender.fromAddress, mailSender.fromPassword);
            mailSender.msg.setContent(mailSender.mailContent, "text/html;charset=utf-8");

            return mailSender;
        }

    }


    public void send() throws MessagingException {
        if(transport != null){
            transport.sendMessage(msg, new Address[] {new InternetAddress(toAddress)});
        }
    }

}
