package com.example.seckillboot.repository;

import com.example.seckillboot.dao.SeckillEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface SeckillRepository extends JpaRepository<SeckillEntity, Long> {

    @Query("select s from SeckillEntity s where s.seckillId = ?1")
    SeckillEntity findBySeckillId(long id);

    @Transactional
    @Modifying
    @Query("update SeckillEntity s set s.number = s.number-1 where s.seckillId = ?1 and s.startTime <= ?2 and s.endTime >= ?2 and s.number >0")
    int reduceNum(long id, Date killTime);

    @Query(nativeQuery = true, value = "select * from seckill s order by s.create_time desc limit ?1, ?2 ")
    List<SeckillEntity> querySeckills(int offset, int limit);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "insert ignore INTO success_killed(seckill_id, user_phone, state) VALUES (?1, ?2, 0)")
    int insertSuccessKilled(long seckillId, long userPhone);
}
