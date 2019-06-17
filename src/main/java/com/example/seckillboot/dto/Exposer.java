package com.example.seckillboot.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @ClassName Exposer
 * @Description TODO
 * @Author Administrator
 * @Date 2019/3/7 15:34
 * @Vertion 1.0
 **/
@AllArgsConstructor
@Data
public class Exposer {

    private long seckillId;

    /**
     * 是否开启秒杀
     */
    private boolean exposed;

    /**
     * 加密措施
     */
    private String md5;

    /**
     * 系统当前时间(毫秒)
     */
    private long now;

    /**
     * 秒杀的开启时间
     */
    private long start;

    /**
     * 秒杀的结束时间
     */
    private long end;

    public Exposer(boolean exposed, long seckillId) {
        this.exposed = exposed;
        this.seckillId = seckillId;
    }

    public Exposer(boolean exposed, String md5, long seckillId) {
        this.exposed = exposed;
        this.md5 = md5;
        this.seckillId = seckillId;
    }

    public Exposer(boolean exposed, long seckillId, long now, long start, long end) {
        this.exposed = exposed;
        this.seckillId = seckillId;
        this.now = now;
        this.start = start;
        this.end = end;
    }
}
