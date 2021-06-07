package com.lbw.seckill.service.api;

import com.lbw.seckill.model.CartItem;

public interface CartService {
    // 向 Redis 添加一个 CartItem
    void addCartItem(CartItem item);

    // 从 Redis 中删除指定的 Item
    void deleteCartItem(int uid, int itemId);
}
