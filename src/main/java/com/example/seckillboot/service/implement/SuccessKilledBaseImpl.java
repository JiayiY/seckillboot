package com.example.seckillboot.service.implement;

import com.example.seckillboot.dao.SuccessKilled;
import com.example.seckillboot.repository.SeckillRepository;
import com.example.seckillboot.service.SuccessKilledBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * @ClassName SuccessKilledBaseImpl
 * @Description TODO
 * @Author Administrator
 * @Date 2019/3/7 10:45
 * @Vertion 1.0
 **/
@Service
public class SuccessKilledBaseImpl implements SuccessKilledBase {
    @Autowired
    EntityManager entityManager;

    @Autowired
    SeckillRepository seckillRepository;

    @Override
    public int insertSuccessKilled(long seckillId, long userPhone) {
        return seckillRepository.insertSuccessKilled(seckillId, userPhone);
    }

    @Override
    public SuccessKilled queryByIdWithSeckill(long seckillId, long userPhone) {
        StringBuffer jpql =  new StringBuffer("select new com.example.seckillboot.dao.SuccessKilled(");
        jpql.append(" ske.seckillId as seckillId, ske.userPhone as userPhone, ske.state as state, ske.createTime as createTime)")
                //.append( ",new com.example.seckillboot.dao.SeckillEntity(s.seckillId, s.name, s.number, s.startTime, s.endTime, s.createTime))" )
                .append(" FROM SuccessKilledEntity ske")
                .append(" INNER JOIN SeckillEntity s ON ske.seckillId=s.seckillId")
                .append(" WHERE ske.seckillId=:seckillId and ske.userPhone=:userPhone");
        Query query = entityManager.createQuery(jpql.toString());
        query.setParameter("seckillId" , seckillId);
        query.setParameter("userPhone" , userPhone);
        List<SuccessKilled> successKilleds = query.getResultList();
        SuccessKilled successKilled = successKilleds.get(0);
        successKilled.setSeckill(seckillRepository.findBySeckillId(seckillId));
        return successKilled;
    }
}
