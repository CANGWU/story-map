package cn.edu.nju.story.map.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xuan
 * @create 2019-01-15 11:44
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceTest {

    @Autowired
    MailService mailService;

    @Test
    public void testSendEmail(){

        mailService.sendInvitation(0L, "xuan", "1425296454@qq.com");

    }


}
