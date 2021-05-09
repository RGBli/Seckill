package com.lbw.seckill.mapper;

import com.lbw.seckill.model.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface OrderMapper {

    @Update("truncate table `order`")
    int delOrderDBBefore();

    @Insert("insert into `order` values(#{id},#{sid},#{name},#{createTime})")
    int createOrder(Order order);
}
