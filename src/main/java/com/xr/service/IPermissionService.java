package com.xr.service;

import com.xr.entity.Permission;

import java.util.List;

/**
 * Created by XR on
 * Date:2017/9/13
 */
public interface IPermissionService {
    int deleteByPrimaryKey(Long id);

    int insert(Permission record);

    Permission selectByPrimaryKey(Long id);

    List<Permission> selectAll();

    int updateByPrimaryKey(Permission record);

    /**
     * 加载权限
     */
    void reloadPermission();
}
