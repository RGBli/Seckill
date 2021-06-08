package com.lbw.seckill.core.redis;

import com.lbw.seckill.core.util.Constants;
import com.lbw.seckill.model.Stock;
import com.lbw.seckill.service.api.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class RedisInitializer implements CommandLineRunner {
    @Autowired
    private StockService stockService;

    @Autowired
    private RedisService redisService;

    // 商品预热，静态热点永不过期
    public void preHeat(List<Integer> sidList) throws Exception {
        for (int sid : sidList) {
            Stock stock = stockService.getStock(sid);
            if (stock != null) {
                redisService.set(Constants.STOCK_PREFIX + sid, stock);
            }
        }
    }

    // 初始化限流 k-v
    public void initLimitKey() {
        redisService.set(Constants.LIMIT, 0);
    }

    // Springboot 自动执行的方法实现
    @Override
    public void run(String... args) throws Exception {
        preHeat(Collections.singletonList(3));
        initLimitKey();
    }
}
