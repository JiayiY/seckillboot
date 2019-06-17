package com.example.seckillboot.exception;

/**
 * @ClassName SeckillCloseException
 * @Description TODO
 * @Author Administrator
 * @Date 2019/3/7 15:37
 * @Vertion 1.0
 **/
public class SeckillCloseException extends SeckillException{
    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
