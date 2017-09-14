package com.xr.mapper;

import com.xr.entity.User;
import com.xr.query.UserQueryObject;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    User selectByPrimaryKey(Long id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

    User login(String name);

    User selectByCondition(UserQueryObject qo);
}