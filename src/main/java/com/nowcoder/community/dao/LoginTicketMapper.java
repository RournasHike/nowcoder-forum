package com.nowcoder.community.dao;

import com.nowcoder.community.entity.LoginTicket;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户登录凭证表数据访问层
 * @author Alex
 * @version 1.0
 * @date 2022/2/5 22:30
 */
@Mapper
public interface LoginTicketMapper {
    /**
     * 插入用户登录凭证记录
     * @param loginTicket 用户登录凭证信息
     * @return
     */

    int insertLoginTicket(LoginTicket loginTicket);

    /**
     * 通过ticket查询用户登录凭证信息
     * @param ticket
     * @return
     */
    LoginTicket selectByTicket(String ticket);

    /**
     * 修改凭证状态，登出失效
     * @param ticket 用户凭证字符串
     * @param status 用户状态
     * @return
     */
    int updateStatus(@Param("ticket") String ticket, @Param("status") int status);
}
