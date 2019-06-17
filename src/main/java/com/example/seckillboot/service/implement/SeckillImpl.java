package com.example.seckillboot.service.implement;

import com.example.seckillboot.dao.SeckillEntity;
import com.example.seckillboot.dao.SuccessKilled;
import com.example.seckillboot.dto.Exposer;
import com.example.seckillboot.dto.SeckillExecution;
import com.example.seckillboot.enums.SeckillStatEnum;
import com.example.seckillboot.exception.RepeatKillException;
import com.example.seckillboot.exception.SeckillCloseException;
import com.example.seckillboot.exception.SeckillException;
import com.example.seckillboot.repository.SeckillRepository;
import com.example.seckillboot.service.SeckillService;
import com.example.seckillboot.service.SuccessKilledBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * @ClassName SeckillImpl
 * @Description TODO
 * @Author Administrator
 * @Date 2019/3/7 15:38
 * @Vertion 1.0
 **/
@Service
public class SeckillImpl implements SeckillService {
    /**
     * 日志对象
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 加入一个混淆字符串(秒杀接口)的salt，为了我避免用户猜出我们的md5值，值任意给，越复杂越好
     */
    private final String salt = "shsdssljdd'l.";

    @Autowired
    private SeckillRepository seckillRepository;

    @Autowired
    private SuccessKilledBase successKilledBase;

    @Override
    public List<SeckillEntity> getSeckillList() {
        return seckillRepository.querySeckills(0,100);
    }

    @Override
    public SeckillEntity getById(long seckillId) {
        return seckillRepository.findBySeckillId(seckillId);
    }

    @Override
    public Exposer exportSeckillUrl(long seckillId) {
        SeckillEntity seckillEntity = seckillRepository.findBySeckillId(seckillId);
        Date startTime = seckillEntity.getStartTime();
        Date endTime = seckillEntity.getEndTime();
        Date nowTime = new Date();
        if (startTime.getTime() > nowTime.getTime() || endTime.getTime() < nowTime.getTime()) {
            return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
        }
        if(seckillEntity==null){
            return new Exposer(false, seckillId);
        }
        String md5 = getMD5(seckillId);
        return new Exposer(true, md5, seckillId);
    }

    /**
     * @Author yangjiayi
     * @Description //秒杀是否成功，成功:减库存，增加明细；失败:抛出异常，事务回滚
     * @Date 14:35 2019/3/10
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return com.example.seckillboot.dto.SeckillExecution
     */
    /**
     * 使用注解控制事务方法的优点:
     * 1.开发团队达成一致约定，明确标注事务方法的编程风格
     * 2.保证事务方法的执行时间尽可能短，不要穿插其他网络操作RPC/HTTP请求或者剥离到事务方法外部
     * 3.不是所有的方法都需要事务，如只有一条修改操作、只读操作不要事务控制
     */
    //@Transactional
    @Override
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException, RepeatKillException, SeckillCloseException {

        Date now = new Date();
        if(md5.isEmpty()|| !md5.equals(getMD5(seckillId))){
            //秒杀数据被重写了
            throw new SeckillException("seckill data rewrite");
        }
        //执行秒杀逻辑:减库存+增加购买明细
        try {
            int count = successKilledBase.insertSuccessKilled(seckillId, userPhone);
            //看是否该明细被重复插入，即用户是否重复秒杀
            if (count <= 0) {
                throw new RepeatKillException("seckill repeated");
            }
            //否则更新了库存，秒杀成功,增加明细
            else {
                int upcount = seckillRepository.reduceNum(seckillId, now);
                if (upcount <= 0) {
                    //没有更新库存记录，说明秒杀结束 rollback
                    throw new SeckillCloseException("seckill is closed");
                }
                else {
                    //秒杀成功,得到成功插入的明细记录,并返回成功秒杀的信息 commit
                    SuccessKilled successKilled = successKilledBase.queryByIdWithSeckill(seckillId,userPhone);
                    return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, successKilled);
                }
            }
        }catch (SeckillCloseException e1) {
            throw e1;
        } catch (RepeatKillException e2) {
            throw e2;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            //所以编译期异常转化为运行期异常
            throw new SeckillException("seckill inner error :" + e.getMessage());
        }
    }

    private String getMD5(long seckillId){
        String base = seckillId + "/" +salt;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    /**
     * 原来执行的流程
     *
     * update(发送在mysql网络时间+gc时间） + insert(发送在mysql网络时间+gc时间)
     *
     * 因为update同一行会导致行级锁，而insert是可以并行执行的。
     *
     * 1.如果先update, update在前面会加锁
     *
     * 锁 + update(发送在mysql网络时间+gc时间） + insert(发送在mysql网络时间+gc时间) + 提交锁
     *
     * 其实的线程就要等，这个锁提交才能执行。
     *
     * 2.如果先insert,
     *
     * insert(发送在mysql网络时间+gc时间） +  锁+ update(发送在mysql网络时间+gc时间) + 提交锁
     *
     * 其实的线程可以并发insert. 这样子会减少锁的时长
     */

    /**
     * 简单来讲就是insert在后面的话就会等锁竞争，放到前面了insert就可以并行执行了，
     *
     * 当中就少了insert占用的时间了，放后面会等两个锁竞争，放前面就等一个锁。
     */
}
