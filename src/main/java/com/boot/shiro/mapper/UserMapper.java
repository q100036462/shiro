package com.boot.shiro.mapper;

import com.boot.shiro.entity.User;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface UserMapper extends Mapper<User> {
    User selectByUserid(Integer userid);

    int updateUserTypeById(User user);

    int updateUserById(User user);
}
