package com.boot.shiro.controller;

import com.boot.shiro.entity.Goods;
import com.boot.shiro.entity.User;
import com.boot.shiro.service.GoodsService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @RequestMapping("/toGoodsPage")
    public String toUserlistPage2(){

        return "page/goods/goods_list";
    }

    @RequestMapping("/goodsList")
    @ResponseBody
    public Map<String, Object> findAll(@RequestParam(value = "page",required = false) Integer page, @RequestParam(value = "pageSize",required = false) Integer pageSize){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (pageSize == null){
            pageSize = 10;
        }
        PageInfo<Goods> pager = goodsService.findAll(page,pageSize);
        resultMap.put("code", 0);
        resultMap.put("msg", "");
        //总条数
        resultMap.put("count", pager.getTotal());
        //获取每页数据
        resultMap.put("data", pager.getList());
        return resultMap;
    }
    @RequestMapping("/updateGoodsById")
    @ResponseBody
    public String updateOneById(Goods goods){
        boolean b = goodsService.updateOneById(goods);
        if (b){
            return "success";
        }
        return "error";
    }
}
