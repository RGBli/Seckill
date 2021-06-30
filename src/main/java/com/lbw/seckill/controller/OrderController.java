package com.lbw.seckill.controller;

import com.lbw.seckill.core.exception.RateLimitException;
import com.lbw.seckill.core.redis.Limit;
import com.lbw.seckill.core.util.BaseResult;
import com.lbw.seckill.core.util.Util;
import com.lbw.seckill.model.Order;
import com.lbw.seckill.service.api.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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

    @RequestMapping("/{uid}/seckill")
    public BaseResult<Boolean> seckill(@PathVariable Integer uid, Integer sid, Integer number) throws Exception {
        String start = "2021-6-30 15:24:00";
        String end = "2021-6-30 15:25:00";
        if (!Util.openUrl(start, end)) {
            return new BaseResult<>(200, "ok", false);
        }
        // 接口限流
        if (!limit.passLimit()) {
            throw new RateLimitException("This API is not working temporarily");
        }
        orderService.seckill(uid, sid, number);
        return new BaseResult<>(200, "ok", true);
    }

    @RequestMapping("/{uid}/list")
    public BaseResult<List<Order>> list(@PathVariable Integer uid, String name) {
        List<Order> orders = orderService.getOrdersByName(uid, name);
        return new BaseResult<>(200, "ok", orders);
    }
}
