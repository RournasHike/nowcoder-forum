package com.nowcoder.community.service;

import com.nowcoder.community.dao.DiscussPostMapper;
import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.filter.SensitiveWordFilter;
import com.nowcoder.community.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

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

    @Autowired
    private SensitiveWordFilter sensitiveWordFilter;

    public List<DiscussPost> findDiscussPostList(int userId,int offset,int limit) {
        return discussPostMapper.selectDiscussPostsByPage(userId,offset,limit);
    }

    public int findDiscussPostCount(int userId){
        return discussPostMapper.selectDiscussPostCount(userId);
    }

    public int addDiscussPost(DiscussPost post){
        if (CommonUtil.isEmtpy(post)){
            throw new IllegalArgumentException("参数不能为空");
        }

        // 转义html标签
        post.setTitle(HtmlUtils.htmlEscape(post.getTitle()));
        post.setContent(HtmlUtils.htmlEscape(post.getContent()));

        // 过滤敏感词
        post.setTitle(sensitiveWordFilter.filterSensitiveWords(post.getTitle()));
        post.setContent(sensitiveWordFilter.filterSensitiveWords(post.getContent()));

        return discussPostMapper.insertDiscussPost(post);
    }
}
