package com.lbw.seckill.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lbw.seckill.core.exception.OutOfStockException;
import com.lbw.seckill.core.util.Constants;
import com.lbw.seckill.mapper.OrderMapper;
import com.lbw.seckill.model.Order;
import com.lbw.seckill.model.Stock;
import com.lbw.seckill.service.api.OrderService;
import com.lbw.seckill.service.api.StockService;
import com.lbw.seckill.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private StockService stockService;

    @Autowired
    private UserService userService;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 核心逻辑
     * 秒杀的主接口
     * 查询库存，如果满足要求则将后续请求发送给 Kafka，由 Kafka 消费者异步完成扣库存和保存订单操作
     */
    @Override
    public void seckill(int uid, int sid, int number) throws Exception {
        // 查库存
        Stock stock = stockService.getStockFromRedis(sid);
        // 库存不足
        if (stock.getNumber() < number) {
            throw new OutOfStockException("Stocks are not sufficient");
        }
        // 发往 Kafka，异步减库存和创建订单
        Order order = new Order(sid, uid, stock.getName(), number, stock.getPrice() * number, null, null, stock.getVersion());
        kafkaTemplate.send(Constants.KAFKA_TOPIC, JSON.toJSONString(order));
    }

    /**
     * 核心逻辑
     * 更新库存并保存订单逻辑，供 Kafka 消费者调用
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createOrder(Order order) throws Exception {
        int uid = order.getUid();
        // 更新库存
        stockService.updateStock(order.getSid(), order.getNumber(), order.getStockVersion());
        order.setAddress(userService.getAddress(uid));
        order.setCreateTime(new Timestamp(System.currentTimeMillis()));
        // 保存订单
        save(order);
    }

    @Override
    public List<Order> getOrdersByName(int uid, String name) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        // MybatisPlus 的 like 是环绕模糊查询，即 %#{name}%，不会走索引
        queryWrapper.eq("uid", uid).like("name", name);
        return list(queryWrapper);
    }
}
