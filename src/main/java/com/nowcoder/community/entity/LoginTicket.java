package com.nowcoder.community.entity;

import java.util.Date;

/**
 * 用户凭证实体类
 * @author Alex
 * @version 1.0
 * @date 2022/2/5 22:25
 */
public class LoginTicket {
    private int id;
    /**
     * 用户id
     */
    private int userId;
    /**
     * 用户凭证字符串
     */
    private String ticket;
    /**
     * 用户登录状态 1 有效 / 0 无效
     */
    private int status;
    /**
     * 过期时间
     */
    private Date expired;

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

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }

    @Override
    public String toString() {
        return "LoginTicket{" +
                "id=" + id +
                ", userId=" + userId +
                ", ticket='" + ticket + '\'' +
                ", status=" + status +
                ", expired=" + expired +
                '}';
    }
}
