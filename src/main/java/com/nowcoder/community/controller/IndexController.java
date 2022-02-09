package com.nowcoder.community.controller;

import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.DiscussPostService;
import com.nowcoder.community.service.UserService;

import com.nowcoder.community.vo.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 主页控制层
 * @author Alex
 * @version 1.0
 * @date 2022/1/30 17:09
 */
@Controller
@Slf4j
public class IndexController {

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private UserService userService;

    @RequestMapping("index")
    public String getIndexPage(Model model, PageInfo pageInfo){
        pageInfo.setRows(discussPostService.findDiscussPostCount(0));
        pageInfo.setPath("index");
        List<DiscussPost> discussPostList = discussPostService.findDiscussPostList(0, pageInfo.getOffset(), pageInfo.getLimit());
        List<Map<String,Object>> userDiscussPosts = new ArrayList<>();
        if(discussPostList!=null){
            // 迭代容器装配用户对应的帖子
            for (DiscussPost discussPost : discussPostList) {
                Map<String,Object> map = new HashMap<>();
                map.put("post",discussPost);
                User user = userService.findUserById(discussPost.getUserId());
                map.put("user",user);
                userDiscussPosts.add(map);
            }

        }
        model.addAttribute("discussPosts",userDiscussPosts);
        return "/index";
    }

    @RequestMapping(path = "/error",method = RequestMethod.GET)
    public String getErrorPage(){
        return "/error/500";
    }

}
