package com.xr.web.controller;

import com.xr.entity.User;
import com.xr.service.IPermissionService;
import com.xr.service.IUserService;
import com.xr.utils.annotation.PermissionName;
import com.xr.vo.ResultVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by XR on
 * Date:2017/9/12
 */
@Controller
public class UserController extends BaseController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IPermissionService permissionService;

    @RequestMapping("/user/login")
    @ResponseBody
    public ResultVo login(User user, HttpServletRequest request) {
        ResultVo vo=new ResultVo();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getName(), user.getPassword());
        try {
            subject.login(token);
            HttpSession session = request.getSession();
            session.setAttribute("USER_SESSION", user);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            vo.fail("账号密码不匹配！");
        }
        return vo;
    }
    @RequestMapping("/user/list")
    @RequiresPermissions("admin:user:list")
    @PermissionName("用户列表")
    public String list() {
        Subject currentUser = SecurityUtils.getSubject();
        permissionService.reloadPermission();
        System.out.println(currentUser.isAuthenticated());
        //System.out.println(currentUser.isPermitted("user:list"));
        return "user/list";
    }
    @RequestMapping("/user/delete")
    @RequiresPermissions("admin:user:delete")
    @PermissionName("用户删除")
    public String delete() {
        Subject currentUser = SecurityUtils.getSubject();
        //permissionService.reloadPermission();
        //System.out.println(currentUser.isPermitted("user:list"));
        return "user/list";
    }
    @RequestMapping("/index")
    public String  index(){
        return "index";
    }

}
