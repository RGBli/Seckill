package com.lbw.seckill.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 商品实体，主要用于查库存，以及价格等商品信息
 */
@TableName("stock")
public class Stock {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private Float price;

    private String detail;

    private Integer number;

    private Integer version;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Stock() {}

    public Stock(String name, Float price, String detail, Integer number, Integer version) {
        this.name = name;
        this.price = price;
        this.detail = detail;
        this.number = number;
        this.version = version;
    }
}
