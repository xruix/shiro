package com.xr.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XR on
 * Date:2017/9/11
 */
@Data
public class User {
    private Long id;
    private String name;
    private Integer userType;
    private Integer userStatus;
    private String password;
    private List<Role> roles=new ArrayList<>();

}
