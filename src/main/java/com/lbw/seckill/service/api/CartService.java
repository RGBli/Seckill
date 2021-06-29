package com.lbw.seckill.service.api;

import com.lbw.seckill.model.CartItem;

import java.util.List;

public interface CartService {
    // 向 Redis 添加一个 CartItem
    void addCartItem(int uid, int sid, int number);

    // 从 Redis 中删除指定的 Item
    void deleteCartItem(int uid, int itemId);

    // 在刷新购物车时更新 CartItem 的信息
    void updateCartItem(int uid);

    // 获取用户购物车中的所有 CartItem
    List<CartItem> getAllCartItems(int uid);
}
