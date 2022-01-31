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

    public static <T> ResultVo error(T t){
        return ResultVo.error(((ResultEnum) t).getMsg());
    }

    public static <T> ResultVo success(T t){
        return ResultVo.success(((ResultEnum) t).getMsg());
    }
}
