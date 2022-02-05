package com.nowcoder.community.service;

import com.nowcoder.community.config.MailClientConfig;
import com.nowcoder.community.constant.ActivationStatus;
import com.nowcoder.community.dao.LoginTicketMapper;
import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.LoginTicket;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.util.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 用户管理业务层
 * @author Alex
 * @version 1.0
 * @date 2022/2/1 15:31
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Autowired
    private MailClientConfig mailClientConfig;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;



    public User findUserById(int id){
        return userMapper.selectById(id);
    }

    public Map<String,Object> register(User user){
        Map<String,Object> map = new HashMap<>();
        if(user==null){
            throw new IllegalArgumentException("用户信息不能为空");
        }

        // 非空校验
        if(StringUtils.isBlank(user.getUsername())){
            map.put("usernameMsg","账号不能为空!");
            return map;
        }

        if(StringUtils.isBlank(user.getPassword())){
            map.put("passwordMsg","密码不能为空");
            return map;
        }

        if(StringUtils.isBlank(user.getEmail())){
            map.put("emailMsg","邮箱不能为空!");
            return map;
        }

        // 验证用户账号
        User existedUser = userMapper.selectByUserName(user.getUsername());
        if(existedUser!=null){
            map.put("usernameMsg","该账号已存在");
            return map;
        }

        // 验证邮箱
        User mailedUser = userMapper.selectByEmail(user.getEmail());
        if(mailedUser!=null){
            map.put("emailMsg","该邮箱已被注册");
            return map;
        }

        // 注册用户
        // 设置盐值
        user.setSalt(CommonUtil.generateUUID().substring(0,5));
        user.setPassword(CommonUtil.md5Encode(user.getPassword()+user.getSalt()));
        // 用户类型设置为普通用户类型
        user.setType(0);
        // 用户状态设置为未激活状态
        user.setStatus(0);
        // 设置激活码
        user.setActivationCode(CommonUtil.generateUUID());
        // 设置随机头像
        user.setHeaderUrl(String.format("http://images.nowcoder.com/head/%dt.png",new Random().nextInt(1000)));
        user.setCreateTime(new Date());
        userMapper.insertUser(user);

        // 发送激活邮件
        Context context = new Context();
        context.setVariable("email",user.getEmail());
        // 设置邮件激活链接 http://localhost:8080/community/activation/{id}/{activationCode}
        String url = domain + contextPath + "/activation/" + user.getId() + "/" + user.getActivationCode();
        context.setVariable("url",url);
        String content = templateEngine.process("/mail/activation", context);
        mailClientConfig.sendMail(user.getEmail(),"激活账号",content);
        return map;
    }

    public int activation(int userId,String code){
        User user = userMapper.selectById(userId);
        if(user.getStatus() == 1){
            // 重复激活
            return ActivationStatus.ACTIVATION_REPEAT.getCode();
        }else if (user.getActivationCode().equals(code)){
            userMapper.updateStatus(userId,1);
            return ActivationStatus.ACTIVATION_SUCCESS.getCode();
        }else{
            return ActivationStatus.ACTIVATION_FAILURE.getCode();
        }
    }

    public Map<String, Object> login(String username,String password,int expiredSeconds){
        Map<String,Object> map = new HashMap<>();

        // 空值处理
        if(StringUtils.isBlank(username)){
            map.put("usernameMsg","账号不能为空");
            return map;
        }

        if(StringUtils.isBlank(password)){
            map.put("passwordMsg","密码不能为空");
            return map;
        }

        // 验证账号
        User user = userMapper.selectByUserName(username);
        if(user == null){
            map.put("usernameMsg","账号不存在");
            return map;
        }

        //验证状态
        if(user.getStatus()==0){
            map.put("usernameMsg","该账号未激活");
            return map;
        }

        // 验证密码
        password = CommonUtil.md5Encode(password+user.getSalt());
        if(!user.getPassword().equals(password)){
            map.put("passwordMsg","密码不正确");
            return map;
        }

        // 生成登录凭证
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(user.getId());
        loginTicket.setTicket(CommonUtil.generateUUID());
        loginTicket.setStatus(1);
        loginTicket.setExpired(new Date(System.currentTimeMillis() + expiredSeconds * 1000));
        loginTicketMapper.insertLoginTicket(loginTicket);

        map.put("ticket",loginTicket.getTicket());
        return map;
    }
}
