package com.nowcoder.community.service;

import com.nowcoder.community.dao.LoginTicketMapper;
import com.nowcoder.community.entity.LoginTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 登录凭证业务层
 * @author Alex
 * @version 1.0
 * @date 2022/2/6 11:21
 */
@Service
public class LoginTicketService {

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    /**
     * 根据用户浏览器cookie携带的ticket查询用户登录凭证
     * @param ticket
     * @return
     */
    public LoginTicket findLoginTicket(String ticket){
        return loginTicketMapper.selectByTicket(ticket);
    }
}
