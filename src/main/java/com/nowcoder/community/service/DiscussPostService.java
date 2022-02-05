package com.nowcoder.community.service;

import com.nowcoder.community.dao.DiscussPostMapper;
import com.nowcoder.community.entity.DiscussPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户发表帖子service层
 * @author Alex
 * @version 1.0
 * @date 2022/2/1 16:00
 */
@Service
public class DiscussPostService {

    @Autowired
    private DiscussPostMapper discussPostMapper;

    public List<DiscussPost> findDiscussPostList(int userId,int offset,int limit) {
        return discussPostMapper.selectDiscussPostsByPage(userId,offset,limit);
    }

    public int findDiscussPostCount(int userId){
        return discussPostMapper.selectDiscussPostCount(userId);
    }
}
