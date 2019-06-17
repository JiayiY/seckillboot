package com.example.seckillboot.controller;

import com.example.seckillboot.dao.SeckillEntity;
import com.example.seckillboot.dao.SuccessKilled;
import com.example.seckillboot.dto.Exposer;
import com.example.seckillboot.dto.SeckillResult;
import com.example.seckillboot.service.SeckillBase;
import com.example.seckillboot.service.SeckillService;
import com.example.seckillboot.service.SuccessKilledBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @ClassName HelloController
 * @Description TODO
 * @Author Administrator
 * @Date 2019/3/7 10:16
 * @Vertion 1.0
 **/
@RestController
public class HelloController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("/helloBoot")
    public String helloBoot() {
        return "Hello Boot-JPA";
    }

    @RequestMapping("/getdb")
    public List<Map<String, Object>> getDbType() {
        String sql = "select * from product";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        for (Map<String, Object> map : list) {
            Set<Map.Entry<String, Object>> entries = map.entrySet();
            if (entries != null) {
                Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
                    Object key = entry.getKey();
                    Object value = entry.getValue();
                    System.out.println(key + ":" + value);
                }
            }
        }
        return list;
    }

    @Autowired
    private SeckillBase seckillBase;

    @Autowired
    private SuccessKilledBase successKilledBase;

    @Autowired
    private SeckillService seckillService;

    @RequestMapping(value = "/id", method = RequestMethod.POST)
    public SeckillEntity queryOne(long test) {
        return seckillBase.queryById(test);
    }

    @RequestMapping(value = "/t", method = RequestMethod.POST)
    public List<SeckillEntity> querySeckills(int offset, int limit) {
        return seckillBase.queryAll(offset, limit);
    }

    @RequestMapping(value = "/tt", method = RequestMethod.POST)
    public int reduceNumber(long seckillId) {
        Date date = new Date();
        /*Calendar calendar= Calendar.getInstance();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        System.out.println(date);
        System.out.println(calendar);
        System.out.println(dateFormat.format(date));*/
        return seckillBase.reduceNumber(seckillId, date);
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public SuccessKilled queryByIdWithSeckill(long seckillId, long userPhone) {
        return successKilledBase.queryByIdWithSeckill(seckillId, userPhone);
    }
    /**
     * @return com.example.seckillboot.dto.SeckillResult
     * @Author yangjiayi
     * @Description //ajax ,json暴露秒杀接口的方法
     * @Date 13:10 2019/3/9
     * @Param [seckillId]
     */
    @RequestMapping(value = "{seckillId}/exposer", produces = {"application/json;charset=UTF-8"})
    public SeckillResult<Exposer> exposer(Long seckillId) {
        SeckillResult<Exposer> seckillResult;
        try {
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            seckillResult = new SeckillResult<>(true, exposer);

        } catch (Exception e) {
            e.printStackTrace();
            seckillResult = new SeckillResult<Exposer>(false, e.getMessage());
        }
        return seckillResult;
    }

}
