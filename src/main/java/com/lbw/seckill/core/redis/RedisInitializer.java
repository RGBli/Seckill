package com.lbw.seckill.core.redis;

import com.lbw.seckill.core.util.Constants;
import com.lbw.seckill.model.Stock;
import com.lbw.seckill.service.api.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class RedisInitializer implements CommandLineRunner {
    @Autowired
    private StockService stockService;

    @Autowired
    private RedisService redisService;

    // 商品预热
    public void preHeat(List<Integer> idList) throws Exception {
        for (int id : idList) {
            Stock stock = stockService.selectStockByID(id);
            if (stock != null) {
                int sid = stock.getId();
                redisService.set(Constants.STOCK_PREFIX + sid, stock.toString());
            }
        }
    }

    // 初始化限流 k-v
    public void initLimitKey() {
        redisService.set(Constants.LIMIT, 0);
    }

    @Override
    public void run(String... args) throws Exception {
        preHeat(Collections.singletonList(3));
        initLimitKey();
    }
}
