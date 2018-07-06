package com.boot.shiro.service;

import com.boot.shiro.entity.Goods;
import com.boot.shiro.mapper.GoodsMapper;
import com.boot.shiro.utils.AbstractService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import javax.persistence.Id;
import java.util.List;

@Service
public class GoodsService extends AbstractService<Goods>{
    @Autowired
    private GoodsMapper goodsMapper;
    @Override
    public Mapper<Goods> getMapper() {
        return this.goodsMapper;
    }

    public PageInfo<Goods> findAll(Integer page,Integer pageSize){
        PageHelper.startPage(page, pageSize);
        List<Goods> goods = goodsMapper.selectAll();
        PageInfo<Goods> pageInfo = new PageInfo<Goods>(goods);
        return pageInfo;
    }

    public boolean updateOneById(Goods goods){
        int i = goodsMapper.updateOneById(goods);
        if (i>=1){
            return true;
        }
        return false;
    }
}
