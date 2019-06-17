package com.example.seckillboot.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.example.seckillboot.dao.SeckillEntity;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @ClassName RedisDao
 * @Description TODO
 * @Author Administrator
 * @Date 2019/3/9 19:25
 * @Vertion 1.0
 **/
public class RedisDao {
    private final JedisPool jedisPool;

    public RedisDao(String ip, int port) {
        jedisPool = new JedisPool("127.0.0.1", 6379);
    }

    private RuntimeSchema<SeckillEntity> schema = RuntimeSchema.createFrom(SeckillEntity.class);
    /**
     * @Author yangjiayi
     * @Description //从redis获取信息
     * @Date 19:34 2019/3/9
     * @Param [seckillId, jedis]
     * @return com.example.seckillboot.dao.SeckillEntity  如果不存在，则返回null
     */
    public SeckillEntity getSeckill(long seckillId) {
        try {
                Jedis jedis = jedisPool.getResource();
            try {
                String key = getSeckillRedisKey(seckillId);
                //并没有实现哪部序列化操作
                //采用自定义序列化
                //protostuff: pojo.
                byte[] bytes = jedis.get(key.getBytes());
                //缓存重获取到
                if (bytes != null) {
                    SeckillEntity seckill = schema.newMessage();
                    ProtostuffIOUtil.mergeFrom(bytes, seckill, schema);
                    //seckill被反序列化

                    return seckill;
                }
            } finally {
                    jedis.close();
            }
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * @Author yangjiayi
     * @Description //根据id获取redis的key
     * @Date 19:34 2019/3/9
     * @Param [seckillId]
     * @return java.lang.String  redis的key
     */
    private String getSeckillRedisKey(long seckillId) {
        return "seckill:" + seckillId;
    }



    public String putSeckill(SeckillEntity seckill) {
        try {
                Jedis jedis = jedisPool.getResource();
            try {
                String key = getSeckillRedisKey(seckill.getSeckillId());
                byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, schema,
                        LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                //超时缓存，1小时
                int timeout = 60 * 60;
                String result = jedis.setex(key.getBytes(), timeout, bytes);
                return result;
            } finally {
                    jedis.close();
            }
        } catch (Exception e) {

        }
        return null;
    }
}
