package com.example.seckillboot.enums;


/**
 * @Author yangjiayi
 * @Description //TODO
 * @Date 17:24 2019/3/7
 * @Param
 * @return
 */
public enum SeckillStatEnum {

    /**
     * 秒杀成功
     */
    SUCCESS(1,"秒杀成功"),

    /**
     * 秒杀结束
     */
    END(0,"秒杀结束"),

    /**
     * 重复秒杀
     */
    REPEAT_KILL(-1,"重复秒杀"),

    /**
     * 系统异常
     */
    INNER_ERROR(-2,"系统异常"),

    /**
     * 数据篡改
     */
    DATE_REWRITE(-3,"数据篡改");

    /**
     * 状态字符
     */
    private int state;

    /**
     * 状态详情
     */
    private String info;

    SeckillStatEnum(int state, String info) {
        this.state = state;
        this.info = info;
    }

    public int getState() {
        return state;
    }


    public String getInfo() {
        return info;
    }


    public static SeckillStatEnum stateOf(int index)
    {
        for (SeckillStatEnum state : values())
        {
            if (state.getState()==index)
            {
                return state;
            }
        }
        return null;
    }
}
