package com.nowcoder.community.dao;

import com.nowcoder.community.CommunityApplication;
import com.nowcoder.community.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void TestSelectUserById(){
        User user = userMapper.selectById(101);
        System.out.println(user);
    }

    @Test
    public void testInsertUser(){
        User user=new User();
        user.setUsername("test");
        user.setPassword("123456");
        user.setSalt("byz");
        user.setEmail("testing@qq.com");
        user.setHeaderUrl("http://www.nowcoder.com/img/101.png");
        user.setCreateTime(new Date());

        int res = userMapper.insertUser(user);
        System.out.println(res==1?"插入成功":"插入失败");
    }

    @Test
    public void updateUser(){
        int rows = userMapper.updatePassword(150, "niuke");
        System.out.println(rows==1?"更新成功":"更新失败");

        rows = userMapper.updateStatus(150,2);
        System.out.println(rows==1?"更新成功":"更新失败");

    }

}
