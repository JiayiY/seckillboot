package com.example.seckillboot.exception;

/**
 * @ClassName RepeatKillException
 * @Description TODO
 * @Author Administrator
 * @Date 2019/3/7 15:37
 * @Vertion 1.0
 **/
public class RepeatKillException extends SeckillException {

    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
