package com.nowcoder.community.constant;

/**
 * 登录凭证过期时间枚举类
 * @author Alex
 * @version 1.0
 * @date 2022/2/5 23:39
 */
public enum LoginConstant {
    /**
     * 默认状态的登录凭证的超时时间:10小时
     */
    DEFAULT_EXPIRED_SECONDS(3600*10,"默认过期时间"),

    /**
     * 记住状态的登录凭证超时时间:10天
     */
    REMEMBERME_EXPIRED_SECONDS(3600*24*10,"点击记住我之后的登录过期时间");

    private int expired;
    private String msg;

    public int getExpired() {
        return expired;
    }

    public String getMsg() {
        return msg;
    }

    LoginConstant(int expired, String msg) {
        this.expired = expired;
        this.msg = msg;
    }

    LoginConstant(){

    }
}
