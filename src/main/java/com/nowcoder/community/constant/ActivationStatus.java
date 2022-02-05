package com.nowcoder.community.constant;

import java.rmi.activation.ActivationID;

/**
 * 用户账号激活状态
 * @author Alex
 * @version 1.0
 * @date 2022/2/4 18:05
 */
public enum ActivationStatus {
    /**
     * 激活成功
     */
    ACTIVATION_SUCCESS(0,"激活成功"),

    /**
     * 重复激活
     */
    ACTIVATION_REPEAT(1,"重复激活"),

    /**
     * 激活失败
     */
    ACTIVATION_FAILURE(2, "激活失败");

    private String msg;

    private int code;

    ActivationStatus(int code,String msg) {
        this.code = code;
        this.msg = msg;
    }

    ActivationStatus(){

    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
