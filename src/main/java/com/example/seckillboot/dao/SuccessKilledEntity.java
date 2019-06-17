package com.example.seckillboot.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @ClassName SuccessKilledEntity
 * @Description TODO
 * @Author Administrator
 * @Date 2019/3/7 10:50
 * @Vertion 1.0
 **/
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "success_killed", schema = "seckill_boot", catalog = "")
@IdClass(SuccessKilledEntityPK.class)
public class SuccessKilledEntity {
    private long seckillId;
    private long userPhone;
    private byte state;
    private Timestamp createTime;

    @Id
    @Column(name = "seckill_id")
    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    @Id
    @Column(name = "user_phone")
    public long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(long userPhone) {
        this.userPhone = userPhone;
    }

    @Basic
    @Column(name = "state")
    public byte getState() {
        return state;
    }

    public void setState(byte state) {
        this.state = state;
    }

    @Basic
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SuccessKilledEntity that = (SuccessKilledEntity) o;
        return seckillId == that.seckillId &&
                userPhone == that.userPhone &&
                state == that.state &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seckillId, userPhone, state, createTime);
    }
}
