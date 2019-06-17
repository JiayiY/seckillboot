package com.example.seckillboot.dao;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName SuccessKilled
 * @Description TODO
 * @Author Administrator
 * @Date 2019/3/7 10:50
 * @Vertion 1.0
 **/
@AllArgsConstructor
@Data
public class SuccessKilled {
    private long seckillId;
    private long userPhone;
    private byte state;
    private Date createTime;
    private SeckillEntity seckill ;
    public SuccessKilled(long seckillId, long userPhone, byte state, Date createTime) {
        this.seckillId = seckillId;
        this.userPhone = userPhone;
        this.state = state;
        this.createTime = createTime;
    }

    public SuccessKilled() {
    }

    @Override
    public String toString() {
        return "SuccessKilled{" +
                "seckillId=" + seckillId +
                ", userPhone=" + userPhone +
                ", state=" + state +
                ", createTime=" + createTime +
                ", seckill=" + seckill +
                '}';
    }
}
