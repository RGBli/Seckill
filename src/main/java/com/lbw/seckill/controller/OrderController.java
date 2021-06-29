package com.lbw.seckill.controller;

import com.lbw.seckill.core.exception.RateLimitException;
import com.lbw.seckill.core.redis.Limit;
import com.lbw.seckill.core.util.BaseResult;
import com.lbw.seckill.model.Order;
import com.lbw.seckill.service.api.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private Limit limit;

    @RequestMapping("/{md5}/seckill")
    public BaseResult<Boolean> seckill(Integer uid, Integer sid, Integer number) throws Exception {
        // 接口限流
        if (!limit.passLimit()) {
            throw new RateLimitException("This API is not working temporarily");
        }
        orderService.seckill(uid, sid, number);
        return new BaseResult<>(200, "ok", true);
    }

    @RequestMapping("/list")
    public BaseResult<List<Order>> list(Integer uid, String name) {
        List<Order> orders = orderService.getOrdersByName(uid, name);
        return new BaseResult<>(200, "ok", orders);
    }
}
