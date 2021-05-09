package com.lbw.seckill.service.impl;

import com.alibaba.fastjson.JSON;
import com.lbw.seckill.core.util.Constants;
import com.lbw.seckill.mapper.OrderMapper;
import com.lbw.seckill.model.Stock;
import com.lbw.seckill.model.Order;
import com.lbw.seckill.service.api.OrderService;
import com.lbw.seckill.service.api.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private StockService stockService;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public int clearOrderTable() {
        return orderMapper.delOrderDBBefore();
    }

    @Override
    public synchronized int createOrderWithLock(int sid) throws Exception {
        // 查库存
        Stock stock = stockService.checkStock(sid);
        if (stock == null) {
            return 0;
        }
        // 修改库存
        stockService.updateStockWithLock(stock);
        // 生成订单
        return createOrder(stock);
    }

    @Override
    public int createOrderWithOL(int sid) throws Exception {
        Stock stock = stockService.checkStock(sid);
        if (stock == null) {
            return 0;
        }
        int res = stockService.updateStockWithOL(stock);
        if (res == 0) {
            return 0;
        }
        return createOrder(stock);
    }

    @Override
    public int CreateOrderWithOLRedis(int sid) throws Exception {
        Stock stock = stockService.checkStockFromRedis(sid);
        if (stock == null) {
            stockService.checkStock(sid);
        }
        if (stock == null) {
            return 0;
        }
        int res = stockService.updateStockWithOLRedis(stock);
        if (res == 0) {
            return 0;
        }
        return createOrder(stock);
    }

    @Override
    public void CreateOrderWithOLRedisLimitKafka(int sid) throws Exception {
        Stock stock = stockService.checkStockFromRedis(sid);
        kafkaTemplate.send(Constants.KAFKA_TOPIC, JSON.toJSONString(stock));
    }

    @Override
    public int consumeFromKafka(Stock stock) throws Exception {
        if (stock == null) {
            return 0;
        }
        int res = stockService.updateStockWithOLRedis(stock);
        if (res == 0) {
            return 0;
        }
        return createOrder(stock);
    }

    @Override
    public int createOrder(Stock stock) throws Exception {
        Order order = new Order(stock.getId(), stock.getName(), new Date());
        return orderMapper.createOrder(order);
    }
}
