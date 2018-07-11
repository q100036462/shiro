package com.boot.shiro.mapper;

import com.boot.shiro.entity.User;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface UserMapper extends Mapper<User> {
    User selectByUserid(Integer userid);

    int updateUserTypeById(User user);

    int updateUserById(User user);

    List<User> searchUsers(User user);

    int checkUsername(String username);
}
