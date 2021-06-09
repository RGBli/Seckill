package com.lbw.seckill.service.api;


import com.baomidou.mybatisplus.extension.service.IService;
import com.lbw.seckill.model.CartItem;
import com.lbw.seckill.model.Order;

import java.util.List;

public interface OrderService extends IService<Order> {

    void createOrder(Order order) throws Exception;

    List<Order> getOrdersByName(int uid, String name);

    void seckill(int uid, int sid, int number) throws Exception;
}
