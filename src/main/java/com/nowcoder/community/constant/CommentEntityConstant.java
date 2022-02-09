package com.nowcoder.community.constant;

/**
 * 评论功能实体类型
 * @author Alex
 * @version 1.0
 * @date 2022/2/8 16:14
 */
public enum CommentEntityConstant {

    /**
     * 实体类型:帖子
     */
    ENTITY_TYPE_POST(1,"帖子"),

    /**
     * 实体类型:评论
     */
    ENTITY_TYPE_COMMENT(2,"评论");

    int type;
    String desc;

    CommentEntityConstant(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    CommentEntityConstant() {
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
