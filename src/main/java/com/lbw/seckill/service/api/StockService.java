package com.lbw.seckill.service.api;


import com.baomidou.mybatisplus.extension.service.IService;
import com.lbw.seckill.core.exception.OutOfStockException;
import com.lbw.seckill.model.CartItem;
import com.lbw.seckill.model.Stock;

import java.util.List;

public interface StockService extends IService<Stock> {

    // 从数据库中获取指定商品的库存
    Stock getStock(int sid) throws Exception;

    // 从 Redis 中获取指定商品的库存
    Stock getStockFromRedis(int sid) throws Exception;

    List<Stock> getStocksByName(String name);

    void updateStock(int sid, int offset, int version) throws OutOfStockException, InterruptedException;

    int getStockNum(int sid);

    float getStockPrice(int sid);
}
