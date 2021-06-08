package com.lbw.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lbw.seckill.mapper.UserMapper;
import com.lbw.seckill.model.User;
import com.lbw.seckill.service.api.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public String getAddress(int uid) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("address").eq("id", uid);
        return getOne(queryWrapper).getAddress();
    }

    // 给 name 和 password 字段添加联合索引，可以实现覆盖索引
    @Override
    public boolean checkPassword(String name, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id").eq("name", name).eq("password", password);
        User user = getOne(queryWrapper);
        return user != null;
    }
}
