package com.lbw.seckill.controller;


import com.lbw.seckill.core.exception.RateLimitException;
import com.lbw.seckill.core.redis.Limit;
import com.lbw.seckill.core.result.BaseResult;
import com.lbw.seckill.service.api.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private Limit limit;

    @RequestMapping(value = "seckill", method = RequestMethod.POST)
    public BaseResult<String> seckill(int uid, int sid, int number) throws Exception {
        // 接口限流
        if (!limit.passLimit()) {
            throw new RateLimitException("This API is not working temporarily");
        }
        orderService.seckill(uid, sid, number);
        return new BaseResult<>(200, "ok", "Purchase succeed");
    }
}
