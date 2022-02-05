package com.nowcoder.community.config;

import com.nowcoder.community.CommunityApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * 发送邮件测试类
 * @author Alex
 * @version 1.0
 * @date 2022/2/2 11:12
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
@Slf4j
public class MailTest {

    @Autowired
    private MailClientConfig mailClientConfig;

    /**
     * 在测试类中主动调用模板引擎,由模板引擎调用模板网页;在主程序中直接使用模板的路径，由dispatcherServlet调用
     */
    @Autowired
    private TemplateEngine templateEngine;

    /**
     * @Description 测试邮件客户端发送文本邮件功能
     * @param
     * @return
     * @throws
     */
    @Test
    public void testTextMail(){
        mailClientConfig.sendMail("2057473561@qq.com","TEST","welcome...");
        log.info("邮件发送成功...");
    }

    /**
     * @Description 测试使用thymeleaf模板引擎生成html动态网页，并使用邮件客户端配置类发送html邮件
     * @param 
     * @return  
     * @throws
     */
    @Test
    public void testHtmlMail(){
        Context context = new Context();
        context.setVariable("username","alex");
        //调用模板引擎生成动态网页
        String content = templateEngine.process("/mail/test_mail", context);
        log.info("thymeleaf模板引擎生成的动态网页内容为:{}",content);

        //发送邮件
        mailClientConfig.sendMail("2057473561@qq.com","发送邮件示例",content);
    }
}
