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

    /**
     * 分页查询(用户)帖子列表
     * @param userId
     * @param offset
     * @param limit
     * @return List<DiscussPost>
     */
    public List<DiscussPost> findDiscussPostList(int userId,int offset,int limit) {
        return discussPostMapper.selectDiscussPostsByPage(userId,offset,limit);
    }

    /**
     * 根据用户id查询用户帖子总数
     * @param userId
     * @return
     */
    public int findDiscussPostCount(int userId){
        return discussPostMapper.selectDiscussPostCount(userId);
    }

    /**
     * 添加帖子
     * @param post
     * @return int
     */
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

    /**
     * 根据帖子id查询帖子信息
     * @param id
     * @return DiscussPost
     */
    public DiscussPost findDiscussPostById(int id){
        return discussPostMapper.selDiscussPostById(id);
    }

    /**
     * 根据id更新评论数量
     * @param id
     * @param commentCount
     * @return
     */
    public int updateCommentCount(int id,int commentCount){
        return discussPostMapper.updateCommentCount(id,commentCount);
    }
}
