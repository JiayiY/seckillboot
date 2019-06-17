package com.example.seckillboot.dto;

import lombok.Data;

/**
 * @ClassName SeckillResult
 * @Description TODO
 * @Author Administrator
 * @Date 2019/3/9 10:53
 * @Vertion 1.0
 **/
@Data
public class SeckillResult<T> {
    private boolean success;
    private String errormsg;
    private T data;

    public SeckillResult(boolean success, String errormsg) {
        this.success = success;
        this.errormsg = errormsg;
    }

    public SeckillResult(boolean success, T data) {
        this.success = success;
        this.data = data;
    }
}
