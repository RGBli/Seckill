package com.lbw.seckill.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * 订单实体
 */
@TableName("order")
public class Order {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer sid;

    private Integer uid;

    private Integer number;

    private Float totalPrice;

    private Date createTime;

    public Order(Integer sid, Integer uid, Integer number, Float totalPrice, Date createTime) {
        this.sid = sid;
        this.uid = uid;
        this.number = number;
        this.totalPrice = totalPrice;
        this.createTime = createTime;
    }
}
