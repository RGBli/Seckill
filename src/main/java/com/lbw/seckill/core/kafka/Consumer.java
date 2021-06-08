package com.lbw.seckill.core.kafka;

import com.alibaba.fastjson.JSONObject;
import com.lbw.seckill.core.util.Constants;
import com.lbw.seckill.model.CartItem;
import com.lbw.seckill.model.Order;
import com.lbw.seckill.model.Stock;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import com.lbw.seckill.service.api.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

// Kafka consumer
@Component
public class Consumer {

    @Autowired
    private OrderService orderService;

    @KafkaListener(topics = Constants.KAFKA_TOPIC)
    public void listen(ConsumerRecord<String, String> record) throws Exception {
        Optional<?> KafkaMessage = Optional.ofNullable(record.value());
        String message = (String) KafkaMessage.get();
        Order order = JSONObject.parseObject(message, Order.class);
        orderService.createOrder(order);
    }
}
