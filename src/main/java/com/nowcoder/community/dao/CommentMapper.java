package com.nowcoder.community.dao;

import com.nowcoder.community.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户评论数据访问层
 * @author Alex
 * @version 1.0
 * @date 2022/2/8 15:52
 */
@Mapper
public interface CommentMapper {

    /**
     * 分页查询某条帖子下的所有评论
     * @param entityType
     * @param entityId
     * @param offset
     * @param limit
     * @return
     */
    List<Comment> selectCommentByEntity(int entityType,int entityId,int offset,int limit);

    /**
     * 查询某条帖子下的所有评论
     * @param entityType
     * @param entityId
     * @return
     */
    int selectCountByEntity(int entityType,int entityId);

    /**
     * 插入评论记录
     * @param comment
     * @return
     */
    int insertComment(Comment comment);
}
