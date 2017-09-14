package com.xr.shiro;

import com.xr.entity.Permission;
import com.xr.entity.Role;
import com.xr.entity.User;
import com.xr.query.UserQueryObject;
import com.xr.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by XR on
 * Date:2017/9/12
 */
public class MyRealm extends AuthorizingRealm {
    @Override
    public String getName() {
        return "MyRealm";
    }


    @Autowired
    private IUserService userService;
    //清除缓存
    public void clearCached() {
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        super.clearCache(principals);
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        User user = new User();
        user.setName(username);
        User login = userService.login(user);
        String name = login.getName();
        String password = login.getPassword();
        if (!name.equals(username)) {
            return null;
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, password, getName());
        return info;
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        UserQueryObject qo = new UserQueryObject();
        qo.setUsername((String) principalCollection.getPrimaryPrincipal());
        User user = userService.selectByCondition(qo);
        List<Role> roles = user.getRoles();
        Set<String> permissionsSet = new HashSet<>();
        Set<String> roleSet = new HashSet<>();
        for (Role role : roles) {
            roleSet.add(role.getRoleName());
            for (Permission per : role.getPermissionList()) {
                permissionsSet.add(per.getExpression());
            }
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(roleSet);
        info.setStringPermissions(permissionsSet);
        return info;
    }
}
