package com.nowcoder.community.interceptor;

import com.nowcoder.community.annotation.LoginRequired;
import com.nowcoder.community.entity.LoginTicket;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.LoginTicketService;
import com.nowcoder.community.service.UserService;
import com.nowcoder.community.util.CommonUtil;
import com.nowcoder.community.util.CookieUtil;
import com.nowcoder.community.util.ThreadLocalHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 登录拦截器
 * @author Alex
 * @version 1.0
 * @date 2022/2/6 10:47
 */
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * ===============================================
     * preHandle 在controller之前执行
     * postHandle 在controller之后执行
     * afterCompletion 在模板引擎之后执行
     * ===============================================
     */

    @Autowired
    private LoginTicketService loginTicketService;

    @Autowired
    private UserService userService;

    @Autowired
    private ThreadLocalHolder<User> userThreadLocalHolder;

    /**
     * @Description
     * @param
     * @return
     * @throws
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从cookie中获取凭证
        String ticket = CookieUtil.getValue(request, "ticket");
        if(!CommonUtil.isEmtpy(ticket)){
            // 查询凭证
            LoginTicket loginTicket = loginTicketService.findLoginTicket(ticket);
            // 检查登录凭证是否有效
            if(loginTicket.getStatus()==0&&loginTicket.getExpired().after(new Date())){
                // 根据凭证查询用户
                User user = userService.findUserById(loginTicket.getUserId());
                // 在本次请求中持有用户信息
                userThreadLocalHolder.setCache(user);
                log.info("{}用户登录成功,当前登录时间为{}",user.getUsername(),CommonUtil.getFormatDate(new Date()));
                return true;
            }
        }

        // 获取请求路径
        String uri = request.getRequestURI();
        log.info("当前请求路径为:{}",uri);

        // 登录拦截
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            LoginRequired loginRequired = method.getAnnotation(LoginRequired.class);
            // 如果用户未登录，跳转到登录页面
            if(!CommonUtil.isEmtpy(loginRequired)&&CommonUtil.isEmtpy(userThreadLocalHolder.getCache())){
                response.sendRedirect(request.getContextPath() + "/user/loginPage");
                log.info("登录失败,请转向用户登录页面......");
                return false;
            }
        }
        return true;
    }

    /**
     * @Description
     * @param
     * @return
     * @throws
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle..............");
        User user = userThreadLocalHolder.getCache();
        if(!CommonUtil.isEmtpy(user)){
            modelAndView.addObject("loginUser",user);
        }
    }

    /**
     * @Description
     * @param
     * @return
     * @throws
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("afterCompletion..............");
        // 清除用户信息
        userThreadLocalHolder.clear();
    }
}
