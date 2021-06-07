package com.lbw.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lbw.seckill.mapper.UserMapper;
import com.lbw.seckill.model.User;
import com.lbw.seckill.service.api.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
