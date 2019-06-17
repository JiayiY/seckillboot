package com.example.seckillboot.dao.cache;

import com.example.seckillboot.dao.SeckillEntity;
import com.example.seckillboot.service.SeckillService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisDaoTest {

    private long id = 1008;

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    private RedisDao redisDao = new RedisDao(host, port);

    @Autowired
    private SeckillService seckillService;

    @Test
    public void testSeckill() {
        //get and put

        SeckillEntity seckill = redisDao.getSeckill(id);
        if (seckill == null) {
            seckill = seckillService.getById(id);
            if (seckill != null) {
                String result = redisDao.putSeckill(seckill);
                System.out.println(result);
                seckill = redisDao.getSeckill(id);
                System.out.println(seckill);
            }
        }
    }
}