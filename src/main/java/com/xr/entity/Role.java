package com.xr.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Role {
    private Long id;

    private String roleName;

    private Long parent;
    private List<Permission> permissionList=new ArrayList<>();


}