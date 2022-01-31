package com.nowcoder.community.exception;

import com.nowcoder.community.constant.ResultEnum;

/**
 * 自定义异常类
 * @author Alex
 * @version 1.0
 * @date 2022/1/30 15:47
 */
public class CustomizeException extends RuntimeException {

    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 方法名称
     */
    private final String method;


    /**
     * 自定义异常
     *
     * @param resultEnum 返回枚举对象
     * @param method     方法
     */
    public CustomizeException(ResultEnum resultEnum, String method) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
        this.method = method;
    }

    /**
     * @param code    状态码
     * @param message 错误信息
     * @param method  方法
     */
    public CustomizeException(Integer code, String message, String method) {
        super(message);
        this.code = code;
        this.method = method;
    }

    public Integer getCode() {
        return code;
    }

    public String getMethod() {
        return method;
    }
}
