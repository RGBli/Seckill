package com.lbw.seckill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lbw.seckill.model.Stock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface StockMapper extends BaseMapper<Stock> {
    @Update("UPDATE `stock` SET number = number - #{offset}, version = version + 1 where id = #{sid} and version = #{version}")
    int updateStockWithCas(int sid, int offset, int version);

    @Update("UPDATE `stock` SET number = number - #{offset}, version = version + 1 where id = #{sid}")
    int updateStockWithLock(int sid, int offset);
}
