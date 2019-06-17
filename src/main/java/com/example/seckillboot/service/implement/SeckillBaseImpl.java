package com.example.seckillboot.service.implement;

import com.example.seckillboot.dao.SeckillEntity;
import com.example.seckillboot.repository.SeckillRepository;
import com.example.seckillboot.service.SeckillBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @ClassName SeckillBaseImpl
 * @Description TODO
 * @Author Administrator
 * @Date 2019/3/7 10:45
 * @Vertion 1.0
 **/
@Service
public class SeckillBaseImpl implements SeckillBase {

    @Autowired
    SeckillRepository seckillRepository;

    @Override
    public int reduceNumber(long seckillId, Date killTime) {
       return seckillRepository.reduceNum(seckillId, killTime);
    }

    @Override
    public SeckillEntity queryById(long seckillId) {
        return seckillRepository.findBySeckillId(seckillId);
    }

    @Override
    public List<SeckillEntity> queryAll(int offset, int limit) {
        return seckillRepository.querySeckills(offset,limit);
    }
}
