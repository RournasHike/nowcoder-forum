package com.nowcoder.community.constant;

/**
 * 错误枚举类
 * @author Alex
 * @version 1.0
 * @date 2022/1/30 15:37
 */
public enum ResultEnum {
    /**
     * 成功
     */
    SUCCESS(200,"操作成功"),

    /**
     * 邮箱为空或不存在
     */
    EMAIL_NOT_EXIST(101,"邮箱为空或不存在"),

    /**
     * 未知异常
     */
    UNKNOWN_EXCEPTION(100, "未知异常"),

    /**
     * 添加失败
     */
    ADD_ERROR(103, "添加失败"),

    /**
     * 更新失败
     */
    UPDATE_ERROR(104, "更新失败"),

    /**
     * 删除失败
     */
    DELETE_ERROR(105, "删除失败"),

    /**
     * 查找失败
     */
    GET_ERROR(106, "查找失败");

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    /**
     * 通过状态码获取枚举对象
     * @param code 状态码
     * @return 枚举对象
     */
    public static ResultEnum getByCode(int code){
        for (ResultEnum resultEnum : ResultEnum.values()) {
            if(code == resultEnum.getCode()){
                return resultEnum;
            }
        }
        return null;
    }

}

