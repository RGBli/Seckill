package com.lbw.seckill.controller;


import com.lbw.seckill.core.redis.RedisService;
import com.lbw.seckill.core.redis.Limit;
import com.lbw.seckill.service.api.OrderService;
import com.lbw.seckill.service.api.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
@Slf4j
public class IndexController {
    private static final String success = "SUCCESS";
    private static final String error = "ERROR";

    @Autowired
    private OrderService orderService;

    @Autowired
    private StockService stockService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private Limit limit;

    @RequestMapping(value = "initDB")
    public String initDB() {
        try {
            stockService.initStock();
            orderService.clearOrderTable();
            log.info("success to init mysql and redis before run！");
            return success;
        } catch (Exception e) {
            log.error("failed to init mysql and redis before run", e);
            return error;
        }
    }

    @RequestMapping(value = "addOrderWithLock", method = RequestMethod.POST)
    @ResponseBody
    public String addOrderWithLock(int saleId) throws Exception {
        int res = orderService.createOrderWithLock(saleId);
        log.info("add a order result:{}", res);
        return res == 1 ? success : error;
    }

    @RequestMapping(value = "addOrderWithOL", method = RequestMethod.POST)
    @ResponseBody
    public String addOrderWithOL(int saleId) throws Exception {
        int res = orderService.createOrderWithOL(saleId);
        log.info("add a order with optimistic lock result:{}", res);
        return res == 1 ? success : error;
    }

    @RequestMapping(value = "addOrderWithOLRedis", method = RequestMethod.POST)
    @ResponseBody
    public String addOrderWithOLRedis(int saleId) throws Exception {
        int res = orderService.CreateOrderWithOLRedis(saleId);
        log.info("add a order with optimistic lock and redis result:{}", res);
        return res == 1 ? success : error;
    }

    @RequestMapping(value = "addOrderWithOLRedisLimit", method = RequestMethod.POST)
    @ResponseBody
    public String addOrderWithOLRedisLimit(int saleId) throws Exception {
        // 接口限流
        if (!limit.passLimit()) {
            return error;
        }
        // 释放 Redis 连接，因为下面要调用事务方法了
        redisService.release();
        int res = orderService.CreateOrderWithOLRedis(saleId);
        log.info("add a order with optimistic lock, redis and limit result:{}", res);
        return res == 1 ? success : error;
    }

    @RequestMapping(value = "addOrderWithOLRedisLimitKafka", method = RequestMethod.POST)
    @ResponseBody
    public String addOrderWithOLRedisLimitKafka(int saleId) throws Exception {
        // 接口限流
        if (!limit.passLimit()) {
            return error;
        }
        orderService.CreateOrderWithOLRedisLimitKafka(saleId);
        log.info("add the request to a kafka queue");
        return "add into a kafka queue";
    }
}
