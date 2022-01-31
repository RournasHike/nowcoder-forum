package com.nowcoder.community.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Alex
 * @version 1.0
 * @date 2022/1/30 17:09
 */
@Controller
@Slf4j
@RequestMapping("/index")
public class IndexController {

    @GetMapping("/msg")
    @ResponseBody
    public String getMsg(){
        log.info("name:{}","彼岸舞-不知火舞");
        return "不知火舞";
    }
}
