package com.xr.service.impl;

import com.xr.entity.User;
import com.xr.mapper.UserMapper;
import com.xr.query.UserQueryObject;
import com.xr.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by XR on
 * Date:2017/9/12
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public int insert(User record) {
        return 0;
    }

    @Override
    public User selectByPrimaryKey(Long id) {
        return null;
    }

    @Override
    public List<User> selectAll() {
        return null;
    }

    @Override
    public int updateByPrimaryKey(User record) {
        return 0;
    }

    @Override
    public User login(User record) {
        if(record==null){
            return null;
        }
        User login = userMapper.login(record.getName());
        return login;
    }

    @Override
    public User selectByCondition(UserQueryObject qo) {
        User user = userMapper.selectByCondition(qo);
        return user;
    }
}
