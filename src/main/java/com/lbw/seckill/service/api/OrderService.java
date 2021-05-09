package com.lbw.seckill.service.api;


import com.lbw.seckill.model.Stock;

public interface OrderService {

    int clearOrderTable() throws Exception;

    int consumeFromKafka(Stock stock) throws Exception;

    int createOrder(Stock stock) throws Exception;

    int createOrderWithLock(int sid) throws Exception;

    int createOrderWithOL(int sid) throws Exception;

    int CreateOrderWithOLRedis(int sid) throws Exception;

    void CreateOrderWithOLRedisLimitKafka(int sid) throws Exception;
}
