package com.lbw.seckill.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.sql.Timestamp;
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

    private String address;

    private Timestamp createTime;

    @TableField(exist = false)
    private int stockVersion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public int getStockVersion() {
        return stockVersion;
    }

    public void setStockVersion(int stockVersion) {
        this.stockVersion = stockVersion;
    }

    public Order(Integer sid, Integer uid, Integer number, Float totalPrice, String address, Timestamp createTime, int stockVersion) {
        this.sid = sid;
        this.uid = uid;
        this.number = number;
        this.totalPrice = totalPrice;
        this.address = address;
        this.createTime = createTime;
        this.stockVersion = stockVersion;
    }
}
