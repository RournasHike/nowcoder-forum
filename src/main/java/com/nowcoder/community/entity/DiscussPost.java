package com.nowcoder.community.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Alex
 * @version 1.0
 * @date 2022/1/30 17:03
 */
public class DiscussPost {
    @NotNull
    private int id;
    /**
     * 用户ID
     */
    @NotBlank
    private int userId;
    @NotBlank
    private String title;
    /**
     * 帖子内容
     */
    @NotBlank
    private String content;
    /**
     * 帖子类型
     *  0-普通; 1-置顶;
     */
    private int type;
    /**
     * 帖子状态
     *  0-正常; 1-精华; 2-拉黑;
     */
    private int status;
    private Date createTime;
    /**
     * 帖子评论数
     */
    @NotNull
    private int commentCount;
    /**
     * 帖子分数
     */
    private double score;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "DiscussPost{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", createTime=" + createTime +
                ", commentCount=" + commentCount +
                ", score=" + score +
                '}';
    }
}

