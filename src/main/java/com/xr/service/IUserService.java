package com.xr.service;

import com.xr.entity.User;
import com.xr.query.UserQueryObject;

import java.util.List;

/**
 * Created by XR on
 * Date:2017/9/12
 */
public interface IUserService {int deleteByPrimaryKey(Long id);

    int insert(User record);

    User selectByPrimaryKey(Long id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

    User login(User record);
    User selectByCondition(UserQueryObject qo);

}
