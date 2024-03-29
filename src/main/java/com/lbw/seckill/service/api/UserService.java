package com.lbw.seckill.service.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lbw.seckill.model.User;

public interface UserService extends IService<User> {

    String getAddress(int uid);

    Object checkPassword(String name, String password);
}
