package com.lbw.seckill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lbw.seckill.model.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}
