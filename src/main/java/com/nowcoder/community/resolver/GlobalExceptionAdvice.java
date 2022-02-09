package com.nowcoder.community.resolver;

import com.nowcoder.community.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 全局异常处理类
 * @author Alex
 * @version 1.0
 * @date 2022/2/9 23:08
 */
//@ControllerAdvice(annotations = Controller.class)
@Slf4j
public class GlobalExceptionAdvice {

    /**
     * 处理controller方法中出现的所有异常，统一记录日志，并且重定向到相应的错误页面
     * @param e
     * @param request
     * @param response
     */
//    @ExceptionHandler({Exception.class})
    public void handleException(Exception e, HttpServletRequest request, HttpServletResponse response) {
        log.error("服务器异常:{}",e.getMessage());
        for (StackTraceElement element : e.getStackTrace()){
            log.error(element.toString());
        }

        String xRequestWith = request.getHeader("x-requested-with");
        if("XMLHttpRequest".equals(xRequestWith)){
            response.setContentType("application/json;charset=utf-8");
            PrintWriter writer = null;
            try {
                writer = response.getWriter();
                writer.write(CommonUtil.getJsonString(1,"服务器异常!"));
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                if(writer!=null){
                    writer.close();
                }
            }
        }else{
            try {
                response.sendRedirect(request.getContextPath()+"/error");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
