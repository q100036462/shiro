package com.boot.shiro.service;

import com.boot.shiro.entity.Role;
import com.boot.shiro.entity.User;
import com.boot.shiro.mapper.RoleMapper;
import com.boot.shiro.mapper.UserMapper;
import com.boot.shiro.utils.AbstractService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.net.idn.Punycode;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Service
public class RoleService extends AbstractService<Role>{
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Mapper<Role> getMapper() {
        return roleMapper;
    }

    public boolean insetRole(Role role){
        int i = roleMapper.insert(role);
        if (i>=1){
            return true;
        }
        return false;
    }
}
