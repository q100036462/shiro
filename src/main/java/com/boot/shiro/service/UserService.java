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

    public PageInfo<User> findAll(Integer page,Integer pageSize,User user){

        if (user.getUsername() == null || "".equals(user.getUsername())){
            PageHelper.startPage(page, pageSize);
            List<User> users = userMapper.selectAll();
            PageInfo<User> pageInfo = new PageInfo<User>(users);
            return pageInfo;
        }else {
            PageHelper.startPage(page, pageSize);
            List<User> users = userMapper.searchUsers(user);
            PageInfo<User> pageInfo = new PageInfo<User>(users);
            return pageInfo;
        }
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

    public boolean updateUserTypeById(User user){
        int i = userMapper.updateUserTypeById(user);
        if (i >= 1){
            return true;
        }
        return false;
    }

    public boolean updateUserById(User user){
        int i = userMapper.updateUserById(user);
        if (i >= 1){
            return true;
        }
        return false;
    }

    /**
     * 添加用户
     * @param user
     * @return 1：添加成功  2：用户名重复  3：添加失败
     */
    public Integer addUser(User user){
        int i1 = userMapper.checkUsername(user.getUsername());
        if (i1 <= 1){
            int i = userMapper.insert(user);
            if (i >= 1){
                return 1;
            }
            return 3;
        }
        return 2;
    }

}
