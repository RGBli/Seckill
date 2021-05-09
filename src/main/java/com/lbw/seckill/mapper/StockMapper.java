package com.lbw.seckill.mapper;


import com.lbw.seckill.model.Stock;
import org.apache.ibatis.annotations.*;

@Mapper
public interface StockMapper {

    @Update("UPDATE `stock` SET count = 2, sale = 0, version = 0")
    int initStock();

    @Select("SELECT * FROM `stock` WHERE id = #{id, jdbcType = INTEGER}")
    Stock selectStockById(@Param("id") int id);

    @Update("UPDATE `stock` SET count = #{count, jdbcType = INTEGER}, name = #{name, jdbcType = VARCHAR}, " +
            "sale = #{sale,jdbcType = INTEGER},version = #{version,jdbcType = INTEGER} " +
            "WHERE id = #{id, jdbcType = INTEGER}")
    int updateStock(Stock stock);

    // optimistic lock
    @Update("UPDATE `stock` SET count = count - 1, sale = sale + 1, version = version + 1 WHERE " +
            "id = #{id, jdbcType = INTEGER} AND version = #{version, jdbcType = INTEGER}")
    int updateStockWithOptimisticLock(Stock stock);

    @Insert("insert into `stock` values(#{name}, #{count}, #{sale}, #{version})")
    int addStock(Stock stock);

    @Delete("delete from `stock` where id = #{id}")
    int deleteStock(int id);
}
