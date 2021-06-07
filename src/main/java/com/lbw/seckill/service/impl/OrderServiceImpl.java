package com.lbw.seckill.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lbw.seckill.core.exception.OutOfStockException;
import com.lbw.seckill.core.util.Constants;
import com.lbw.seckill.mapper.OrderMapper;
import com.lbw.seckill.model.CartItem;
import com.lbw.seckill.model.Order;
import com.lbw.seckill.model.Stock;
import com.lbw.seckill.service.api.OrderService;
import com.lbw.seckill.service.api.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private StockService stockService;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    // 核心逻辑
    @Override
    public void createOrder(int uid, int sid, int number) throws Exception {
        Stock stock = stockService.getStockFromRedis(sid);
        if (stock.getNumber() < number) {
            throw new OutOfStockException("Stocks are not enough");
        }
        CartItem item = new CartItem(uid, sid, number, stock.getPrice() * number);
        kafkaTemplate.send(Constants.KAFKA_TOPIC, JSON.toJSONString(item));
    }

    @Override
    public List<Order> getOrdersByName(int uid, String name) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", uid).like("name", name);
        return list(queryWrapper);
    }
}
