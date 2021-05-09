package com.lbw.seckill.service.api;


import com.lbw.seckill.model.Stock;

public interface StockService {

    int initStock() throws Exception;

    int updateStockWithLock(Stock stock) throws Exception;

    int updateStockWithOL(Stock stock) throws Exception;

    int updateStockWithOLRedis(Stock stock) throws Exception;

    Stock checkStock(int sid) throws Exception;

    Stock checkStockFromRedis(int sid) throws Exception;

    Stock selectStockByID(int id) throws Exception;
}
