package com.nowcoder.community.dao;

import com.nowcoder.community.CommunityApplication;
import com.nowcoder.community.entity.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author Alex
 * @version 1.0
 * @date 2022/2/9 17:04
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MessageMapperTest {

    @Autowired
    private MessageMapper messageMapper;

    @Test
    public void testSelectConversations(){
        List<Message> messages = messageMapper.selectConversations(111, 0, 20);
        for(Message message:messages){
            System.out.println(message);
        }

    }

    @Test
    public void testSelectConversationCount(){
        int rows = messageMapper.selectConversationCount(111);
        System.out.println("会话数目为:"+rows);
    }

    @Test
    public void testSelectLetters(){
        List<Message> messages = messageMapper.selectLetters("111_112", 0, 20);
        for (Message message:messages){
            System.out.println(message);
        }
    }

    @Test
    public void testSelectLetterCount(){
        int rows = messageMapper.selectLetterCount("111_112");
        System.out.println("会话私信条数:"+rows);
    }

    @Test
    public void testSelectLetterUnreadCount(){
        int rows = messageMapper.selectLetterUnreadCount(131, "111_131");
        System.out.println("未读消息数目:"+rows);
    }



}
