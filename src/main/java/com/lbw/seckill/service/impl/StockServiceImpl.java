package com.lbw.seckill.service.impl;

import com.lbw.seckill.core.redis.RedisService;
import com.lbw.seckill.core.util.Constants;
import com.lbw.seckill.mapper.StockMapper;
import com.lbw.seckill.model.Stock;
import com.lbw.seckill.service.api.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public int initStock() {
        return stockMapper.initStock();
    }

    @Override
    public int updateStockWithLock(Stock stock) {
        stock.setCount(stock.getCount() - 1);
        stock.setSale(stock.getSale() + 1);
        return stockMapper.updateStock(stock);
    }

    @Override
    public int updateStockWithOL(Stock stock) {
        int res = 0;
        // 乐观锁失败重试三次
        for (int i = 0; i < 4 && res == 0; i++) {
            res = stockMapper.updateStockWithOptimisticLock(stock);
        }
        return res;
    }

    @Override
    public int updateStockWithOLRedis(Stock stock) {
        int res = 0;
        // 乐观锁失败重试三次
        for (int i = 0; i < 4 && res == 0; i++) {
            res = stockMapper.updateStockWithOptimisticLock(stock);
        }
        // 删除 Redis
        redisService.del(Constants.STOCK_PREFIX + stock.getId());
        return res;
    }

    @Override
    public Stock selectStockByID(int id) {
        return stockMapper.selectStockById(id);
    }

    @Override
    public Stock checkStock(int sid) {
        Stock stock = selectStockByID(sid);
        if (stock.getCount() < 1) {
            return null;
        }
        return stock;
    }

    @Override
    public Stock checkStockFromRedis(int sid) {
        String[] stockInfo = redisService.getStrArr(Constants.STOCK_PREFIX + sid);
        // Redis miss，则从 DB 查询，并写入 Redis
        // 设置过期时间为 60s，以避免长时间的数据库和缓存的数据不一致问题
        if (stockInfo == null) {
            Stock stock = checkStock(sid);
            if (stock != null) {
                redisService.set(Constants.STOCK_PREFIX + sid, stock, 60);
            }
            return stock;
        }
        int count = Integer.parseInt(stockInfo[0]);
        if (count < 1) {
            return null;
        }
        Stock stock = new Stock(
                sid,
                stockInfo[2],
                count,
                Integer.parseInt(stockInfo[1]),
                Integer.parseInt(stockInfo[3]));
        return stock;
    }
}
