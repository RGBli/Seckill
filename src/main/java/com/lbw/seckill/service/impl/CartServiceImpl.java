package com.lbw.seckill.service.impl;

import com.lbw.seckill.core.redis.RedisService;
import com.lbw.seckill.core.util.Constants;
import com.lbw.seckill.model.CartItem;
import com.lbw.seckill.service.api.CartService;
import com.lbw.seckill.service.api.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private StockService stockService;

    @Override
    public void addCartItem(int uid, int sid, int number) {
        float price = stockService.getStockPrice(sid);
        CartItem item = new CartItem(uid, sid, number, number * price);
        redisService.hset(String.valueOf(uid), String.valueOf(item.getId()), item);
    }

    @Override
    public void deleteCartItem(int uid, int cid) {
        redisService.hdel(Constants.CART_PREFIX + uid, String.valueOf(cid));
    }

    @Override
    public void updateCartItem(int uid) throws Exception {
        List<Object> itemList = redisService.hvals(String.valueOf(uid));
        for (Object item : itemList) {
            CartItem cartItem = (CartItem) item;
            cartItem.setTotalPrice(stockService.getStockPrice(cartItem.getSid()) * cartItem.getNumber());
            redisService.hset(String.valueOf(uid), String.valueOf(cartItem.getId()), cartItem);
        }
    }
}
