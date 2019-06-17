package com.example.seckillboot.service;

import com.example.seckillboot.dao.SuccessKilled;

public interface SuccessKilledBase {

    /**
     * @Author yangjiayi
     * @Description //插入购买明细，可过滤重复
     * @Date 18:20 2019/3/6
     * @Param [seckillId, userPhone]
     * @return int
     */
    int insertSuccessKilled(long seckillId, long userPhone);

    /**
     * @Author yangjiayi
     * @Description //根据Id查询SuccessKilled并携带秒杀产品对象实体
     * @Date 14:27 2019/3/10
     * @param seckillId
     * @param userPhone
     * @return com.example.seckillboot.dao.SuccessKilled
     */
    SuccessKilled queryByIdWithSeckill(long seckillId, long userPhone);
}
