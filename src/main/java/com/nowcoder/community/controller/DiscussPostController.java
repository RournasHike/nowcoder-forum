package com.nowcoder.community.controller;

import com.nowcoder.community.annotation.LoginRequired;
import com.nowcoder.community.constant.CommentEntityConstant;
import com.nowcoder.community.entity.Comment;
import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.CommentService;
import com.nowcoder.community.service.DiscussPostService;
import com.nowcoder.community.service.UserService;
import com.nowcoder.community.util.CommonUtil;
import com.nowcoder.community.util.ThreadLocalHolder;
import com.nowcoder.community.vo.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * 帖子管理控制层
 * @author Alex
 * @version 1.0
 * @date 2022/2/6 21:48
 */
@Controller
@RequestMapping("/discuss")
public class DiscussPostController {

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ThreadLocalHolder<User> userThreadLocalHolder;

    @RequestMapping(path = "/add",method = RequestMethod.POST)
    @ResponseBody
    @LoginRequired
    public String addDiscussPost(String title,String content){
        User user = userThreadLocalHolder.getCache();
        if(CommonUtil.isEmtpy(user)){
            return CommonUtil.getJsonString(403,"你还没有登录哦!");
        }

        DiscussPost discussPost = new DiscussPost();
        discussPost.setUserId(user.getId());
        discussPost.setTitle(title);
        discussPost.setContent(content);
        discussPost.setCreateTime(new Date());
        discussPostService.addDiscussPost(discussPost);

        // 报错的情况在全局异常处理器中处理
        return CommonUtil.getJsonString(200,"发布成功");
    }

    @RequestMapping(path = "/detail/{discussPostId}",method = RequestMethod.GET)
    @LoginRequired
    public String getDiscussPost(@PathVariable("discussPostId") int discussPostId, Model model, PageInfo pageInfo){
        // 查询帖子
        DiscussPost post = discussPostService.findDiscussPostById(discussPostId);
        model.addAttribute("post",post);

        // 查询作者
        User user = userService.findUserById(post.getUserId());
        model.addAttribute("user",user);

        // 分页查询帖子评论
        pageInfo.setLimit(5);
        pageInfo.setPath("/discuss/detail/" + discussPostId);
        pageInfo.setRows(post.getCommentCount());
        List<Comment> commentList = commentService.findCommentByEntity(CommentEntityConstant.ENTITY_TYPE_POST.getType(), post.getId(), pageInfo.getOffset(), pageInfo.getLimit());

        // 封装所有查询到的结果
        // 评论：给帖子的评论
        // 回复：给评论的评论
        // 评论列表
        List<Map<String,Object>> commentVoList = new ArrayList<>();
        if(!CommonUtil.isEmtpy(commentList)){
            for (Comment comment:commentList){
                // 一个评论的VO
                Map<String,Object> commentVo = new HashMap<>();
                // 评论
                commentVo.put("comment",comment);
                // 评论作者
                commentVo.put("user",userService.findUserById(comment.getUserId()));
                // 回复列表：评论的评论
                List<Comment> replyList = commentService.findCommentByEntity(CommentEntityConstant.ENTITY_TYPE_COMMENT.getType(), comment.getId(), 0, Integer.MAX_VALUE);
                // 回复VO列表
                List<Map<String,Object>> replyVoList = new ArrayList<>();
                if(!CommonUtil.isEmtpy(replyList)){
                    for(Comment reply:replyList){
                        Map<String,Object> replyVo = new HashMap<>();
                        // 回复
                        replyVo.put("reply",reply);
                        // 作者
                        replyVo.put("user",userService.findUserById(reply.getUserId()));
                        // 回复目标
                        User target = reply.getTargetId()==0?null:userService.findUserById(reply.getTargetId());
                        replyVo.put("target",target);

                        replyVoList.add(replyVo);
                    }
                }

                commentVo.put("replys",replyVoList);

                // 回复数量
                int replyCount = commentService.findCommentCount(CommentEntityConstant.ENTITY_TYPE_COMMENT.getType(), comment.getId());
                commentVo.put("replyCount",replyCount);
                commentVoList.add(commentVo);
            }
        }

        model.addAttribute("comments",commentVoList);

        return "site/discuss-detail";
    }

}
