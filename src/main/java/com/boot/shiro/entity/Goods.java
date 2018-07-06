package com.boot.shiro.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Goods {
    @Id
    private Integer id;

    private String goodsName;

    private Integer num;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}