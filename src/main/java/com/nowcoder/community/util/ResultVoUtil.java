package com.nowcoder.community.util;

import com.nowcoder.community.constant.ResultEnum;
import com.nowcoder.community.vo.ResultVo;

/**
 * 请求结果对象处理工具类
 * @author Alex
 * @version 1.0
 * @date 2022/1/30 18:18
 */
public class ResultVoUtil {

    /**
     * 请求失败的结果对象封装方法
     * @param t
     * @param <T>
     * @return
     */
    public static <T> ResultVo error(T t){
        return ResultVo.error(((ResultEnum) t).getMsg());
    }

    /**
     * 请求成功结果对象的封装方法
     * @param t
     * @param <T>
     * @return
     */
    public static <T> ResultVo success(T t){
        return ResultVo.success(((ResultEnum) t).getMsg());
    }
}
