package com.lbw.seckill.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.lbw.seckill.core.util.BaseResult;
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
    public BaseResult<Boolean> login(String name, String password) {
        Object id = userService.checkPassword(name, password);
        if (id != null) {
            StpUtil.setLoginId(id);
            return new BaseResult<>(200, "ok", true);
        } else {
            return new BaseResult<>(403, "Forbidden", false);
        }
    }

    /**
     * 用户登出
     */
    @RequestMapping("/logout")
    public BaseResult<Boolean> logout() {
        StpUtil.logout();
        return new BaseResult<>(200, "ok", true);
    }
}
