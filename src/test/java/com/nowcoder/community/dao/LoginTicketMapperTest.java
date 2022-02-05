package com.nowcoder.community.dao;

import com.nowcoder.community.CommunityApplication;
import com.nowcoder.community.entity.LoginTicket;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.UUID;

/**
 * 用户凭证信息数据访问层测试类
 * @author Alex
 * @version 1.0
 * @date 2022/2/5 22:52
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class LoginTicketMapperTest {

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Test
    public void testInsertLoginTicket(){
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setTicket(UUID.randomUUID().toString());
        loginTicket.setStatus(1);
        loginTicket.setUserId(111);
        loginTicket.setExpired(new Date());
        System.out.println(loginTicket.getTicket());

        int res = loginTicketMapper.insertLoginTicket(loginTicket);
        System.out.println(res>0?"插入成功":"插入失败");
    }

    @Test
    public void testSelectByTicket(){
        LoginTicket loginTicket = loginTicketMapper.selectByTicket("1dd5617b-7751-44ff-8348-39564b34d385");
        System.out.println(loginTicket);
        loginTicketMapper.updateStatus("1dd5617b-7751-44ff-8348-39564b34d385",0);

        System.out.println("==============================================================================================================================");

        loginTicket = loginTicketMapper.selectByTicket("1dd5617b-7751-44ff-8348-39564b34d385");
        System.out.println(loginTicket);
    }
}
