package com.example.seckillboot.service;

import com.example.seckillboot.dao.SeckillEntity;

import java.util.Date;
import java.util.List;

public interface SeckillBase {
    /**
     * @Author yangjiayi
     * @Description //减库存
     * @Date 17:23 2019/3/6
     * @Param [seckillId, killTime]
     * @return int
     */
    int reduceNumber(long seckillId, Date killTime);

    /**
     * @Author yangjiayi
     * @Description //根据Id查询秒杀对象
     * @Date 17:23 2019/3/6
     * @Param [seckillId]
     * @return com.example.seckilldemo.dao.Seckill
     */
    SeckillEntity queryById(long seckillId);

    /**
     * @Author yangjiayi
     * @Description //根据偏移量查询秒杀商品列表
     * @Date 17:24 2019/3/6
     * @Param [offet, limit]
     * @return java.util.List<com.example.seckilldemo.dao.Seckill>
     */
    List<SeckillEntity> queryAll(int offset, int limit);

}
