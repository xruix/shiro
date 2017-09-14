package com.xr.service.impl;

import com.xr.entity.Permission;
import com.xr.mapper.PermissionMapper;
import com.xr.service.IPermissionService;
import com.xr.utils.AopTargetUtils;
import com.xr.utils.annotation.PermissionName;
import com.xr.web.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by XR on
 * Date:2017/9/13
 */
@Service
public class PermissionServiceImpl implements IPermissionService, ApplicationContextAware {
    private ApplicationContext ctx;
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public int insert(Permission record) {
        return 0;
    }

    @Override
    public Permission selectByPrimaryKey(Long id) {
        return null;
    }

    @Override
    public List<Permission> selectAll() {
        return null;
    }

    @Override
    public int updateByPrimaryKey(Permission record) {
        return 0;
    }

    @Override
    public void reloadPermission() {
        //查询出所有的权限
        List<Permission> permissions = permissionMapper.selectAll();
        //存放权限表达式
        Set<String> set = new HashSet<>();
        for (Permission per : permissions) {
            set.add(per.getExpression());
        }
        //获取controller中的方法
        Map<String, BaseController> actionMap = ctx.getBeansOfType(BaseController.class);
        for (BaseController action : actionMap.values()) {
            Object target = action;
            //处理代理类
            try {
                target = AopTargetUtils.getTarget(action);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Method[] methods = target.getClass().getMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(RequiresPermissions.class)) {
                    RequiresPermissions annotation = method.getAnnotation(RequiresPermissions.class);
                    String[] exp = annotation.value();
                    StringBuilder sb = new StringBuilder(16);
                    for (int i = 0; i < exp.length; i++) {
                        System.out.println(exp.length);
                        System.out.println(exp[i]);
                        if (i == exp.length - 1) {
                            sb.append(exp[i]);
                        } else {
                            sb.append(exp[i]).append(":");
                        }

                    }
                    String str = sb.toString();
                    //判断数据库中是否存在
                    if (!set.contains(str)) {
                        //判断是否贴有RequiredPermission标签
                        if (method.isAnnotationPresent(RequiresPermissions.class) && method.isAnnotationPresent(PermissionName.class)) {
                            RequiresPermissions rp = method.getAnnotation(RequiresPermissions.class);
                            StringBuilder sbRp = new StringBuilder(16);
                            for (int i = 0; i < exp.length; i++) {
                                if (i == exp.length - 1) {
                                    sbRp.append(exp[i]);
                                } else {
                                    sbRp.append(exp[i]).append(":");
                                }

                            }
                            String sbRpStr = sbRp.toString();
                            PermissionName per = method.getAnnotation(PermissionName.class);
                            Permission pm = new Permission();
                            pm.setAuthorityName(per.value());
                            pm.setExpression(sbRpStr);
                            permissionMapper.insert(pm);
                        }
                    }
                }

            }

        }
    }
}
