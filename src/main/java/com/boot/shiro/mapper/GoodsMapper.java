package com.boot.shiro.mapper;

import com.boot.shiro.entity.Goods;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface GoodsMapper extends Mapper<Goods> {

    List<Goods> selectAll();

    int updateOneById(Goods goods);

}
