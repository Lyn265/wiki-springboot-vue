package com.lyn.wiki.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyn.wiki.domain.User;
import com.lyn.wiki.mapper.UserMapper;
import com.lyn.wiki.req.RestPasswordReq;
import com.lyn.wiki.req.UserLoginReq;
import com.lyn.wiki.req.UserQueryReq;
import com.lyn.wiki.req.UserSaveReq;
import com.lyn.wiki.resp.CommonResp;
import com.lyn.wiki.resp.UserLoginResp;
import com.lyn.wiki.resp.UserResp;
import com.lyn.wiki.resp.PageResp;
import com.lyn.wiki.service.IUserService;
import com.lyn.wiki.util.CopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author Mr.Liu
 * @since 2021-05-08
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserMapper userMapper;

    @Autowired
    IUserService userService;

    /**
     * 电子书查询
     * @param userQueryReq
     * @return
     */
    @GetMapping("/list")
    public CommonResp list(UserQueryReq userQueryReq){
        CommonResp<PageResp> resp = new CommonResp();
        QueryWrapper<User> wrapper = new QueryWrapper();
        if(!ObjectUtils.isEmpty(userQueryReq.getLoginName())){
            wrapper.like("login_name", userQueryReq.getLoginName());
        }
        Page<User> userPage = new Page<>(userQueryReq.getPage(), userQueryReq.getSize());
        userPage = userMapper.selectPage(userPage, wrapper);
        List<User> userList = userPage.getRecords();
        PageResp pageResp = new PageResp<User>();
        pageResp.setTotal(userPage.getTotal());
        List<UserResp> respList = CopyUtil.copyList(userList, UserResp.class);
        pageResp.setList(respList);
        resp.setContent(pageResp);
        return resp;
    }
    @PostMapping("/save")
    public CommonResp saveUser(@Valid @RequestBody UserSaveReq userSaveReq){
        CommonResp commonResp = new CommonResp();
        userSaveReq.setPassword(DigestUtils.md5DigestAsHex(userSaveReq.getPassword().getBytes()));
        int result = userService.saveUser(userSaveReq);
        if(result == 1){
            return commonResp;
        }
            commonResp.setSuccess(false);
            commonResp.setMessage("更新失败");
            return commonResp;
    }
    @DeleteMapping("/delete/{id}")
    public CommonResp delUser(@PathVariable Long id){
        CommonResp commonResp = new CommonResp();
        int result = userService.delUser(id);
        if(result == 1){
            commonResp.setMessage("删除成功。");
            return commonResp;
        }
            commonResp.setSuccess(false);
            commonResp.setMessage("删除失败。");
            return commonResp;
    }

    /**
     * 重置密码
     * @param passwordReq
     * @return
     */
    @PostMapping("/reset-password")
    public CommonResp resetPassword(@Valid @RequestBody RestPasswordReq passwordReq){
           CommonResp commonResp = new CommonResp();
        passwordReq.setPassword(DigestUtils.md5DigestAsHex(passwordReq.getPassword().getBytes()));
        int result = userService.resetPassword(passwordReq);
        if (result == 1){
            return commonResp;
        }
            commonResp.setSuccess(false);
            commonResp.setMessage("更新失败");
            return commonResp;
    }
    /**
     * 登录
     * @param req
     * @return
     */
    @PostMapping("/login")
    public CommonResp login(@Valid @RequestBody UserLoginReq req){
        CommonResp commonResp = new CommonResp();
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
        UserLoginResp loginResp = userService.login(req);
        commonResp.setContent(loginResp);
        return commonResp;
    }
}
