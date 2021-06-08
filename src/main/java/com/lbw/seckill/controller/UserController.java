package com.lbw.seckill.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lbw.seckill.core.result.BaseResult;
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
    public BaseResult<String> add(User user) {
        userService.save(user);
        return new BaseResult<>(200, "ok", "Create new user succeed");
    }

    @RequestMapping("/delete")
    public BaseResult<String> add(int uid) {
        userService.removeById(uid);
        return new BaseResult<>(200, "ok", "Delete new user succeed");
    }

    @RequestMapping("/update")
    public BaseResult<String> update(User user) {
        userService.updateById(user);
        return new BaseResult<>(200, "ok", "Update new user succeed");
    }

    @RequestMapping("/list")
    public BaseResult<List<User>> list(String name) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", name);
        List<User> users = userService.list(queryWrapper);
        return new BaseResult<>(200, "ok", users);
    }
}
