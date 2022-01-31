package com.nowcoder.community.vo;

import com.nowcoder.community.constant.ResultEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 通用异步请求返回对象
 * @author Alex
 * @version 1.0
 * @date 2022/1/30 16:00
 */
@ApiModel("固定返回格式")
public class ResultVo {

    /**
     * 错误码
     */
    @ApiModelProperty("错误码")
    private Integer code;

    /**
     * 提示信息
     */
    @ApiModelProperty("提示信息")
    private String message;

    /**
     * 具体的内容
     */
    @ApiModelProperty("响应数据")
    private Object data;

    /**
     * 返回的数据条数
     */
    private Integer count;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * 数据返回成功的方法
     * @param msg
     * @return
     */
    public static ResultVo success(String msg){
        ResultVo resultVo = new ResultVo();
        resultVo.setMessage(msg);
        resultVo.setCode(ResultEnum.SUCCESS.getCode());
        return resultVo;
    }

    /**
     * 数据返回成功的方法重载
     * @param msg
     * @param data
     * @return
     */
    public static ResultVo success(String msg,Object data){
        ResultVo resultVo = new ResultVo();
        resultVo.setMessage(msg);
        resultVo.setCode(ResultEnum.SUCCESS.getCode());
        resultVo.setData(data);
        return resultVo;
    }

    /**
     * 数据返回成功的方法重载
     * @param msg
     * @param data
     * @param count
     * @return
     */
    public static ResultVo success(String msg,Object data,Integer count){
        ResultVo resultVo = new ResultVo();
        resultVo.setMessage(msg);
        resultVo.setCode(ResultEnum.SUCCESS.getCode());
        resultVo.setData(data);
        resultVo.setCount(count);
        return resultVo;
    }

    /**
     * 数据返回错误时执行的方法
     * @param msg
     * @return
     */
    public static ResultVo error(String msg){
        ResultVo resultVo = new ResultVo();
        resultVo.setMessage(msg);
        resultVo.setCode(ResultEnum.SUCCESS.getCode());
        return resultVo;
    }

    @Override
    public String toString() {
        return "ResultVo{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", count=" + count +
                '}';
    }
}
