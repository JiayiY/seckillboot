package com.example.seckillboot.dao;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 * @ClassName SuccessKilledEntityPK
 * @Description TODO
 * @Author Administrator
 * @Date 2019/3/7 10:50
 * @Vertion 1.0
 **/
public class SuccessKilledEntityPK implements Serializable {
    private long seckillId;
    private long userPhone;

    @Column(name = "seckill_id")
    @Id
    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    @Column(name = "user_phone")
    @Id
    public long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(long userPhone) {
        this.userPhone = userPhone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SuccessKilledEntityPK that = (SuccessKilledEntityPK) o;
        return seckillId == that.seckillId &&
                userPhone == that.userPhone;
    }

    @Override
    public int hashCode() {
        return Objects.hash(seckillId, userPhone);
    }
}
