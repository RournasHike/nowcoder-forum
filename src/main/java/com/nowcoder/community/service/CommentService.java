package com.nowcoder.community.service;

import com.nowcoder.community.constant.CommentEntityConstant;
import com.nowcoder.community.dao.CommentMapper;
import com.nowcoder.community.entity.Comment;
import com.nowcoder.community.entity.ReplyInfo;
import com.nowcoder.community.filter.SensitiveWordFilter;
import com.nowcoder.community.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * 评论管理业务曾
 * @author Alex
 * @version 1.0
 * @date 2022/2/8 16:02
 */
@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private SensitiveWordFilter sensitiveWordFilter;

    @Autowired
    private DiscussPostService discussPostService;

    /**
     * 分页查询帖子评论信息
     * @param entityType
     * @param entityId
     * @param offset
     * @param limit
     * @return
     */
    public List<Comment> findCommentByEntity(int entityType,int entityId,int offset,int limit){
        return commentMapper.selectCommentByEntity(entityType,entityId,offset,limit);
    }

    /**
     * 查询帖子评论数量
     * @param entityType
     * @param entityId
     * @return
     */
    public int findCommentCount(int entityType,int entityId){
        return commentMapper.selectCountByEntity(entityType,entityId);
    }

    /**
     * 添加评论信息
     * @param comment
     * @return
     */
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public int addComment(Comment comment){
        if(CommonUtil.isEmtpy(comment)){
            throw new IllegalArgumentException("参数不能为空");
        }

        // 添加评论
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        comment.setContent(sensitiveWordFilter.filterSensitiveWords(comment.getContent()));
        int rows = commentMapper.insertComment(comment);

        // 跟新帖子评论数量
        if(comment.getEntityType() == CommentEntityConstant.ENTITY_TYPE_POST.getType()){
            int count = commentMapper.selectCountByEntity(comment.getEntityType(), comment.getEntityId());
            discussPostService.updateCommentCount(comment.getEntityId(),count);
        }

        return rows;
    }

    /**
     * 根据用户id分页查询帖子回复列表
     * @param userId
     * @param offset
     * @param limit
     * @return
     */
    public List<ReplyInfo> findReplyInfoList(int userId,int offset,int limit){
        return commentMapper.selectDiscussPostCommentByUserId(userId,offset,limit);
    }

    /**
     * 根据用户id查询用户帖子回复总数
     * @param userId
     * @return
     */
    public int findReplyInfoCount(int userId){
        return commentMapper.selectDiscussPostCommentCount(userId);
    }
}