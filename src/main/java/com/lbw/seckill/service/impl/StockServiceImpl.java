package com.lbw.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lbw.seckill.core.exception.SeckillFailException;
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

    /**
     * 核心逻辑
     * 试图从 Redis 中获取商品信息
     * 如果 Redis miss，则从 DB 查询，并写入 Redis
     */
    @Override
    public Stock getStockFromRedis(int sid) throws Exception {
        Stock stock = (Stock) redisService.get(Constants.STOCK_PREFIX + sid);
        if (stock == null) {
            stock = getStock(sid);
            if (stock != null) {
                // 设置过期时间为 60s，以避免长时间的数据库和缓存的数据不一致问题
                redisService.set(Constants.STOCK_PREFIX + sid, stock, 60);
            }
        }
        return stock;
    }

    /**
     * 核心逻辑
     * 乐观锁更新库存，更新失败则抛出异常并让用户重试
     */
    @Override
    public void updateStock(int sid, int offset, int version) throws SeckillFailException, InterruptedException {
        // 采用延时双删的一致性策略
        redisService.del(Constants.STOCK_PREFIX + sid);
        if (stockMapper.updateStockWithCas(sid, offset, version) != 0) {
            Thread.sleep(100);
            redisService.del(Constants.STOCK_PREFIX + sid);
        } else {
            throw new SeckillFailException("Seckill failed due to unknown reason, please try again");
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

    @Override
    public Stock getStock(int sid) {
        return getById(sid);
    }
}
