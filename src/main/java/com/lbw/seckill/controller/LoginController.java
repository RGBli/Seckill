package com.lbw.seckill.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lbw.seckill.core.result.BaseResult;
import com.lbw.seckill.model.User;
import com.lbw.seckill.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 用户登陆
     */
    @RequestMapping("/login")
    public BaseResult<String> login(String username, String password) {
        if (userService.checkPassword(username, password)) {
            return new BaseResult<>(403, "Forbidden", "Password not match");
        } else {
            return new BaseResult<>(200, "ok", "Login succeed");
        }

    }

    /**
     * 用户登出
     */
    @RequestMapping("/logout")
    public BaseResult<String> logout() {
        StpUtil.logout();
        return new BaseResult<>(200, "ok", "Logout succeed");
    }
}
