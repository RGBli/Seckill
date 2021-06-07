package com.lbw.seckill.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lbw.seckill.model.User;
import com.lbw.seckill.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 用户登陆
     */
    @RequestMapping("login")
    public String login(String username, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        User user = userService.getOne(
                queryWrapper.eq("username", username)
                        .eq("password", password));
        if (user != null) {
            StpUtil.setLoginId(user.getId());
            return "succeed";
        }
        return "fail";
    }

    /**
     * 用户登出
     */
    @RequestMapping("logout")
    public String logout() {
        StpUtil.logout();
        return "succeed";
    }
}
