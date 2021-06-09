package com.lbw.seckill.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * 用户实体
 */
@TableName("user")
public class User {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String password;

    private Boolean gender;

    private Date birthday;

    private String address;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User() {}

    public User(String name, String password, Boolean gender, Date birthday, String address) {
        this.name = name;
        this.password = password;
        this.gender = gender;
        this.birthday = birthday;
        this.address = address;
    }
}
