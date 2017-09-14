package com.xr.vo;

import lombok.Data;

/**
 * Created by XR on
 * Date:2017/9/14
 */
@Data
public class ResultVo {
    private String msg;
    private Boolean success=true;
    public  void fail(String msg){
        this.msg=msg;
        this.success=false;
    }
}
