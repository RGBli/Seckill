package com.lbw.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lbw.seckill.core.exception.OutOfStockException;
import com.lbw.seckill.core.redis.RedisService;
import com.lbw.seckill.core.util.Constants;
import com.lbw.seckill.mapper.StockMapper;
import com.lbw.seckill.model.Stock;
import com.lbw.seckill.service.api.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockServiceImpl extends ServiceImpl<StockMapper, Stock> implements StockService {

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public Stock getStock(int sid) throws Exception {
        return getById(sid);
    }

    // 核心逻辑
    @Override
    public Stock getStockFromRedis(int sid) throws Exception {
        Stock stock = (Stock) redisService.get(Constants.STOCK_PREFIX + sid);
        // 如果 Redis miss，则从 DB 查询，并写入 Redis
        if (stock == null) {
            stock = getStock(sid);
            if (stock != null) {
                // 设置过期时间为 60s，以避免长时间的数据库和缓存的数据不一致问题
                redisService.set(Constants.STOCK_PREFIX + sid, stock, 60);
            }
        }
        return stock;
    }

    // 核心逻辑
    @Override
    public void updateStock(int sid, int offset, int version) throws OutOfStockException {
        // 乐观锁失败，则换悲观锁
        if (stockMapper.updateStockWithCas(sid, offset, version) == 0) {
            synchronized (this) {
                int number = getStockNum(sid);
                if (number >= offset) {
                    stockMapper.updateStockWithLock(sid, offset);
                    redisService.del(Constants.STOCK_PREFIX + sid);
                } else {
                    throw new OutOfStockException("Stocks are not sufficient");
                }
            }
        } else {
            redisService.del(Constants.STOCK_PREFIX + sid);
        }
    }

    @Override
    public List<Stock> getStocksByName(String name) {
        QueryWrapper<Stock> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", name);
        return list(queryWrapper);
    }

    @Override
    public int getStockNum(int sid) {
        QueryWrapper<Stock> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("number").eq("id", sid);
        return getOne(queryWrapper).getNumber();
    }

    @Override
    public float getStockPrice(int sid) {
        QueryWrapper<Stock> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("price").eq("id", sid);
        return getOne(queryWrapper).getPrice();
    }
}
