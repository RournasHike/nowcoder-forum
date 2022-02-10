package com.nowcoder.community.dao;

import com.nowcoder.community.CommunityApplication;
import com.nowcoder.community.entity.ReplyInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 评论管理数据访问层测试类
 * @author Alex
 * @version 1.0
 * @date 2022/2/10 12:33
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class CommentMapperTest {

    @Autowired
    private CommentMapper commentMapper;

    @Test
    public void testSelectDiscussPostCommentByUserId(){
        List<ReplyInfo> replyInfos = commentMapper.selectDiscussPostCommentByUserId(111,0,10);
        for (ReplyInfo reply:replyInfos){
            System.out.println(reply);
        }
    }

    @Test
    public void testSelectDiscussPostCommentCount(){
        int rows = commentMapper.selectDiscussPostCommentCount(111);
        System.out.println("共查询到"+rows+"条记录");
    }
}
