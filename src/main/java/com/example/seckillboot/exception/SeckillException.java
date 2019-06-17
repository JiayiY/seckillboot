package com.example.seckillboot.exception;

/**
 * @ClassName SeckillException
 * @Description TODO
 * @Author Administrator
 * @Date 2019/3/7 15:37
 * @Vertion 1.0
 **/
public class SeckillException extends RuntimeException {
    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
