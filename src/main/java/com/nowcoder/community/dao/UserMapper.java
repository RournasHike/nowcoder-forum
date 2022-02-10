package com.nowcoder.community.dao;

import com.nowcoder.community.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户实体类
 * @author Alex
 * @version 1.0
 * @date 2022/1/30 17:34
 */
@Mapper
public interface UserMapper {

    /**
     * 根据id查询用户信息
     * @param id
     * @return
     */
    User selectById(int id);

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    User selectByUserName(String username);

    /**
     * 根据用户邮箱查询用户信息
     * @param email
     * @return
     */
    User selectByEmail(String email);

    /**
     * 插入用户
     * @param user
     * @return
     */
    int insertUser(User user);

    /**
     * 更新用户状态
     * @param id
     * @param status
     * @return
     */
    int updateStatus(int id,int status);

    /**
     * 更新用户头像
     * @param id
     * @param headerUrl
     * @return
     */
    int updateHeader(int id,String headerUrl);

    /**
     * 更新用户密码
     * @param id
     * @param password
     * @return
     */
    int updatePassword(int id,String password);

}
