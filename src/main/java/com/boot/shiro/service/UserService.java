package com.boot.shiro.service;

import com.boot.shiro.entity.User;
import com.boot.shiro.mapper.UserMapper;
import com.boot.shiro.utils.AbstractService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Service
public class UserService extends AbstractService<User>{
    @Autowired
    private UserMapper userMapper;
    @Override
    public Mapper<User> getMapper() {
        return this.userMapper;
    }

    public PageInfo<User> findAll(Integer page,Integer pageSize){
        PageHelper.startPage(page, pageSize);
        List<User> users = userMapper.selectAll();
        PageInfo<User> pageInfo = new PageInfo<User>(users);
        return pageInfo;
    }

    public boolean login(User user){
        User user1 = userMapper.selectOne(user);
        if (user1 != null){
            return true;
        }
        return false;
    }

    public User selectOneByPrimaryKey(Integer userid){
        User user = userMapper.selectByUserid(userid);
        return user;
    }
}
