package com.lbw.seckill.service.impl;

import com.lbw.seckill.core.redis.RedisService;
import com.lbw.seckill.core.util.Constants;
import com.lbw.seckill.model.CartItem;
import com.lbw.seckill.service.api.CartService;
import org.springframework.beans.factory.annotation.Autowired;

public class CartServiceImpl implements CartService {

    @Autowired
    private RedisService redisService;

    @Override
    public void addCartItem(CartItem item) {
        redisService.hset(Constants.CART_PREFIX + item.getUid().toString(), item.getId().toString(), item);
    }

    @Override
    public void deleteCartItem(int uid, int itemId) {
        redisService.hdel(Constants.CART_PREFIX + uid, String.valueOf(itemId));
    }
}
