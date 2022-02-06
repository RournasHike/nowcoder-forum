package com.nowcoder.community.controller;

import com.google.code.kaptcha.Producer;
import com.nowcoder.community.annotation.LoginRequired;
import com.nowcoder.community.constant.ActivationStatus;
import com.nowcoder.community.constant.LoginConstant;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.UserService;
import com.nowcoder.community.util.CommonUtil;
import com.nowcoder.community.util.ThreadLocalHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

/**
 * 用户管理控制层
 * @author Alex
 * @version 1.0
 * @date 2022/2/2 11:58
 */
@Controller
@RequestMapping("user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private Producer kaptchaProducer;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Value("${community.path.upload}")
    private String uploadPath;

    @Value("${community.path.domain}")
    private String domain;

    @Autowired
    private ThreadLocalHolder<User> userThreadLocalHolder;


    @RequestMapping(path = "loginPage",method = RequestMethod.GET)
    public String loginPage(){
        return "site/login";
    }

    @RequestMapping(path = "registerPage",method = RequestMethod.GET)
    public String registerPage(){
        return "site/register";
    }

    @RequestMapping(path = "register", method = RequestMethod.POST)
    public String register(Model model, User user){
        Map<String,Object> map = userService.register(user);
        if(map == null || map.isEmpty()){
            model.addAttribute("msg","注册成功，我们已经向您的邮箱发送了一封激活邮件，请尽快激活！");
            model.addAttribute("target","/index");
            return "site/operate-result";
        }else{
            model.addAttribute("usernameMsg",map.get("usernameMsg"));
            model.addAttribute("passwordMsg",map.get("passwordMsg"));
            model.addAttribute("emailMsg",map.get("emailMsg"));
            return "site/register";
        }
    }

    @RequestMapping(path = "login",method = RequestMethod.POST)
    public String login(Model model,HttpSession session,HttpServletResponse response,
                        String username,String password,String verifyCode,boolean rememberMe){
        // 检查验证码
        String kaptcha = (String) session.getAttribute("kaptcha");
        if(StringUtils.isBlank(kaptcha)||StringUtils.isBlank(verifyCode)||!kaptcha.equalsIgnoreCase(verifyCode)){
            model.addAttribute("codeMsg","验证码不正确");
            return "/site/login";
        }

        // 检查账号，密码
        int expiredSeconds = rememberMe ? LoginConstant.REMEMBERME_EXPIRED_SECONDS.getExpired():LoginConstant.DEFAULT_EXPIRED_SECONDS.getExpired();
        Map<String, Object> map = userService.login(username, password, expiredSeconds);
        if(map.containsKey("ticket")){
            Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
            cookie.setPath(contextPath);
            cookie.setMaxAge(expiredSeconds);
            response.addCookie(cookie);
            return "redirect:/index";
        }else{
            model.addAttribute("usernameMsg",map.get("usernameMsg"));
            model.addAttribute("passwordMsg",map.get("passwordMsg"));
            return "site/login";
        }


    }

    @RequestMapping(path = "logout",method = RequestMethod.GET)
    public String logout(@CookieValue("ticket") String ticket){
        if(StringUtils.isBlank(ticket)){
            return "index";
        }
        userService.logout(ticket);
        return "redirect:/user/loginPage";
    }


    @RequestMapping(path = "activation/{userId}/{code}",method = RequestMethod.GET)
    public String activation(Model model, @PathVariable("userId") int userId,@PathVariable("code") String code){
        int activationResult = userService.activation(userId, code);
        if(activationResult == ActivationStatus.ACTIVATION_SUCCESS.getCode()){
            model.addAttribute("msg","激活成功,您的账号已经可以正常使用了！");
            model.addAttribute("target","/user/login");
        }else if(activationResult == ActivationStatus.ACTIVATION_FAILURE.getCode()){
            model.addAttribute("msg","无效操作，该账号已经激活过了");
            model.addAttribute("target","/index");
        }else{
            model.addAttribute("msg","激活失败,您提供的激活码不正确");
            model.addAttribute("target","/index");
        }

        return "/site/operate-result";
    }

    @RequestMapping(path = "/kaptcha",method = RequestMethod.GET)
    public void getKaptcha(HttpServletResponse response, HttpSession session){
        //生成验证码
        String text = kaptchaProducer.createText();
        BufferedImage image = kaptchaProducer.createImage(text);

        // 将验证码存入session
        session.setAttribute("kaptcha",text);

        // 将图片输出给浏览器
        response.setContentType("image/png");
        try {
            OutputStream os = response.getOutputStream();
            ImageIO.write(image,"png",os);
        }catch (Exception e){
            log.error("验证码生成失败，{}",e.getMessage());
        }
    }

    @LoginRequired
    @RequestMapping(path = "/settings",method = RequestMethod.GET)
    public String getSettingsPage(){
        return "site/setting";
    }

    @LoginRequired
    @RequestMapping(path = "/upload",method = RequestMethod.POST)
    public String uploadHeader(MultipartFile headerImg,Model model){
        if(CommonUtil.isEmtpy(headerImg)){
            model.addAttribute("error","您还没有上传头像");
            return "/site/setting";
        }
        String fileName = headerImg.getOriginalFilename();
        String fileSuffix = fileName.substring(fileName.lastIndexOf("."));
        if(StringUtils.isBlank(fileSuffix)){
            model.addAttribute("error","文件格式不正确!");
            return "site/setting";
        }

        // 生成随机文件名
        fileName = CommonUtil.generateUUID() + fileSuffix;
        // 确定文件存放路径
        String filePath = uploadPath + "/" +fileName;
        File dist = new File(filePath);
        if (!dist.getParentFile().exists()){
            dist.getParentFile().mkdirs();
        }
        try {
            // 存储文件
            headerImg.transferTo(dist);
        } catch (IOException e) {
            log.error("上传文件失败:"+e.getMessage());
            throw new RuntimeException("上传文件失败，服务器异常：",e);
        }

        // 更新当前用户头像路径(web访问路径)
        // http://localhost:8088/community/user/header/xxx.png
        User user = userThreadLocalHolder.getCache();
        String headerUrl = domain + contextPath + "/user/header/"+fileName;
        userService.updateHeader(user.getId(),headerUrl);
        return "redirect:/index";
    }

    @RequestMapping(path = "header/{fileName}",method = RequestMethod.GET)
    public void getHeader(@PathVariable("fileName") String fileName, HttpServletResponse response){
        // 获取用户头像文件存放路径
        File file = new File(uploadPath + "/" + fileName);
        // 文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf("."));

        //响应图片
        response.setContentType("img/"+suffix);

        try (OutputStream os = response.getOutputStream();
             FileInputStream fis = new FileInputStream(file);){

            byte[] buffer = new byte[1024];
            int offset = 0;
            while ((offset=fis.read(buffer))!=-1){
                os.write(buffer,0,offset);
            }
        } catch (IOException e) {
            log.error("读取头像失败:{}",e.getMessage());
        }
    }

}
