package com.nowcoder.community.entity;

/**
 * @author Alex
 * @version 1.0
 * @date 2022/2/10 12:19
 */
public class ReplyInfo extends Comment{
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + getId() +
                ", userId=" + getUserId() +
                ", entityType=" + getEntityType() +
                ", entityId=" + getEntityId() +
                ", targetId=" + getTargetId() +
                ", content='" + getContent() + '\'' +
                ", status=" + getStatus() +
                ", createTime=" + getCreateTime() +
                ", title="+ getTitle() +
                '}';
    }
}
