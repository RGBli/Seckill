package com.lbw.seckill.model;

/**
 * 购物车中信息实体，也是用于生成订单的主要信息来源
 * 用 Redis 来存储，因此不需要建表
 */
public class CartItem {
    private Integer id;

    private Integer uid;

    private Integer sid;

    private Integer number;

    private Float totalPrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
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

    public CartItem(Integer uid, Integer sid, Integer number, Float totalPrice) {
        this.uid = uid;
        this.sid = sid;
        this.number = number;
        this.totalPrice = totalPrice;
    }
}
