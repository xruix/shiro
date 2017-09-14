package com.xr.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by XR on
 * Date:2017/9/11
 */
public class Shiro {


    private static final transient Logger log = LoggerFactory.getLogger(Shiro.class);

    public static void main(String[] args) {
        System.out.println("My First Apache Shiro Application");
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:xr.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken("admin", "admin");
            token.setRememberMe(true);
            try {
                currentUser.login(token);
            } catch (UnknownAccountException uae) {
                System.out.println("没有此用户" + token.getPrincipal());
            } catch (IncorrectCredentialsException ice) {
                System.out.println("Password for account " + token.getPrincipal() + " was incorrect!");
            } catch (LockedAccountException lae) {
                System.out.println("The account for username " + token.getPrincipal() + " is locked. " +
                        "Please contact your administrator to unlock it.");
            }
            //catch more exceptions here (maybe custom ones specific to your application?
            catch (AuthenticationException ae) {
            //unexpected condition? error?
            }
        }//say who they are:
        //print their identifying principal (in this case, a username):
        System.out.println("User [" + currentUser.getPrincipal() + "] logged in successfully.");
        //test a role:
        if (currentUser.hasRole("schwartz")) {
            System.out.println("May the Schwartz be with you!");
        } else {
            System.out.println("Hello, mere mortal.");
        }
        //test a typed permission (not instance-level)
        if (currentUser.isPermitted("lightsaber:weild")) {
            System.out.println("You may use a lightsaber ring. Use it wisely.");
        } else {
            System.out.println("Sorry, lightsaber rings are for schwartz masters only.");
        }
        //a (very powerful) Instance Level permission:
        if (currentUser.isPermitted("winnebago:drive:eagle5")) {
            System.out.println("You are permitted to 'drive' the winnebago with license plate (id) 'eagle5' . " +
                    "Here are the keys - have fun!");
        } else {
            System.out.println("Sorry, you aren't allowed to drive the 'eagle5' winnebago!");
        }
//all done - log out!
        currentUser.logout();
        System.exit(0);
    }

}
