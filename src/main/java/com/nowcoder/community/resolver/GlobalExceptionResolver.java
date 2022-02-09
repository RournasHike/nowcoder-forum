package com.nowcoder.community.resolver;

import com.nowcoder.community.constant.ResultEnum;
import com.nowcoder.community.exception.CustomizeException;
import com.nowcoder.community.util.ResultVoUtil;
import com.nowcoder.community.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

/**
 * 全局异常处理类
 * @author Alex
 * @version 1.0
 * @date 2022/1/30 18:15
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionResolver {

    /**
     * 自定义异常
     */
    @ExceptionHandler(value = CustomizeException.class)
    public ResultVo processException(CustomizeException e) {
        log.error("位置:{} -> 错误信息:{}", e.getMethod() ,e.getLocalizedMessage());
        return ResultVoUtil.error(Objects.requireNonNull(ResultEnum.getByCode(e.getCode())));
}

    /**
     * 通用异常
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(Exception.class)
    public String exception(Exception e) {
        log.error("error log info:{}",e.getMessage());
        return "/site/error/500";
    }
}
