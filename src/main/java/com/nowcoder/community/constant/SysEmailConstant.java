package com.nowcoder.community.constant;

/**
 * @author Alex
 * @version 1.0
 * @date 2022/2/10 16:25
 */
public enum SysEmailConstant {
    /**
     * 邮件验证码失效时间 5min
     */
    EMAIL_VERIFYCODE_EXPIRED(60*5,"邮件验证码失效时间");
    private int sec;
    private String msg;

    SysEmailConstant() {
    }

    SysEmailConstant(int sec, String msg) {
        this.sec = sec;
        this.msg = msg;
    }

    public int getSec() {
        return sec;
    }

    public String getMsg() {
        return msg;
    }

}
