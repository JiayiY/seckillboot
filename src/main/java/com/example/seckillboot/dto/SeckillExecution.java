package com.example.seckillboot.dto;

import com.example.seckillboot.dao.SuccessKilled;
import com.example.seckillboot.enums.SeckillStatEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @ClassName SeckillExecution
 * @Description TODO
 * @Author Administrator
 * @Date 2019/3/7 15:35
 * @Vertion 1.0
 **/
@Data
@AllArgsConstructor
public class SeckillExecution {

    private long seckillId;

    /**
     * 秒杀执行结果的状态
     */
    private int state;

    /**
     * 状态的明文标识
     */
    private String stateInfo;

    /**
     * 当秒杀成功时，需要传递秒杀成功的对象回去
     */
    private SuccessKilled successKilled;

    /**
     * 秒杀成功返回所有信息
     */
    public SeckillExecution(long seckillId, SeckillStatEnum seckillStatEnum, SuccessKilled successKilled) {
        this.seckillId = seckillId;
        this.state = seckillStatEnum.getState();
        this.stateInfo = seckillStatEnum.getInfo();
        this.successKilled = successKilled;
    }

    /**
     * 秒杀失败
     */
    public SeckillExecution(long seckillId, SeckillStatEnum statEnum) {
        this.seckillId = seckillId;
        this.state = statEnum.getState();
        this.stateInfo = statEnum.getInfo();
    }
}
