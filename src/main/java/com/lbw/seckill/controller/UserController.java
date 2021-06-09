package com.lbw.seckill.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lbw.seckill.core.util.BaseResult;
import com.lbw.seckill.core.util.Util;
import com.lbw.seckill.model.User;
import com.lbw.seckill.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/add")
    public BaseResult<Boolean> add(User user) {
        if (!Util.checkPassword(user.getPassword())) {
            return new BaseResult<>(200, "ok", false);
        }
        userService.save(user);
        return new BaseResult<>(200, "ok", true);
    }

    @RequestMapping("/delete")
    public BaseResult<Boolean> delete(Integer id) {
        userService.removeById(id);
        return new BaseResult<>(200, "ok", true);
    }

    @RequestMapping("/update")
    public BaseResult<Boolean> update(User user) {
        userService.updateById(user);
        return new BaseResult<>(200, "ok", true);
    }

    @RequestMapping("/list")
    public BaseResult<List<User>> list(String name) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", name);
        List<User> users = userService.list(queryWrapper);
        return new BaseResult<>(200, "ok", users);
    }
}
