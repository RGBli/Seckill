package com.lbw.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
    private RedisService redisService;

    @Override
    public Stock getStock(int sid) throws Exception {
        return getById(sid);
    }

    @Override
    public Stock getStockFromRedis(int sid) throws Exception {
        Stock stock = (Stock) redisService.get(Constants.STOCK_PREFIX + sid);
        // 如果 Redis miss，则从 DB 查询，并写入 Redis
        // 设置过期时间为 60s，以避免长时间的数据库和缓存的数据不一致问题
        if (stock == null) {
            stock = getStock(sid);
            if (stock != null) {
                redisService.set(Constants.STOCK_PREFIX + sid, stock, 60);
            }
        }
        return stock;
    }

    @Override
    public List<Stock> getStocksByName(String name) {
        QueryWrapper<Stock> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", name);
        return list(queryWrapper);
    }

    @Override
    public void updateStock(Stock stock) {

    }


}
