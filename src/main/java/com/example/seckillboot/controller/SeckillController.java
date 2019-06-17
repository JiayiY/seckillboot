package com.example.seckillboot.controller;

import com.example.seckillboot.dao.SeckillEntity;
import com.example.seckillboot.dao.SuccessKilled;
import com.example.seckillboot.dto.Exposer;
import com.example.seckillboot.dto.SeckillExecution;
import com.example.seckillboot.dto.SeckillResult;
import com.example.seckillboot.enums.SeckillStatEnum;
import com.example.seckillboot.exception.RepeatKillException;
import com.example.seckillboot.exception.SeckillCloseException;
import com.example.seckillboot.service.SeckillBase;
import com.example.seckillboot.service.SeckillService;
import com.example.seckillboot.service.SuccessKilledBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

/**
 * @ClassName SeckillController
 * @Description TODO
 * @Author Administrator
 * @Date 2019/3/7 10:43
 * @Vertion 1.0
 **/
@RestController()
/**
 * url:模块/资源/{}/细分
 */
@RequestMapping("/seckill")
public class SeckillController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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


    @RequestMapping(value = "list")
    public ModelAndView list(Model model) {
        //list.jsp+mode=ModelAndView
        //获取列表页
        ModelAndView mode = new ModelAndView();
        List<SeckillEntity> seckillEntities = seckillService.getSeckillList();
        model.addAttribute("list", seckillEntities);
        mode.setViewName("list");
        return mode;
    }

    @RequestMapping(value = "{seckillId}/detail")
    public ModelAndView detail(@PathVariable("seckillId") Long seckillId, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        SeckillEntity seckillEntity = seckillService.getById(seckillId);
        model.addAttribute("seckill", seckillEntity);
        modelAndView.setViewName("detail");
        if (seckillId == null || seckillEntity == null) {
            modelAndView.setViewName("redirect:/seckill/list");
        }
        return modelAndView;
    }

    /**
     * @return com.example.seckillboot.dto.SeckillResult
     * @Author yangjiayi
     * @Description //ajax ,json暴露秒杀接口的方法
     * @Date 13:10 2019/3/9
     * @Param [seckillId]
     */
    @RequestMapping(value = "{seckillId}/exposer", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public SeckillResult<Exposer> exposer(@PathVariable("seckillId") Long seckillId) {
        SeckillResult<Exposer> seckillResult;
        try {
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            seckillResult = new SeckillResult<>(true, exposer);

        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            e.printStackTrace();
            seckillResult = new SeckillResult<Exposer>(false, e.getMessage());
        }
        return seckillResult;
    }


    @RequestMapping(value = "{seckillId}/{md5}/execution",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,
                                                   @PathVariable("md5") String md5,
                                                   @CookieValue(value = "userPhone",required = false) Long userPhone)
    {
        if (userPhone==null)
        {
            return new SeckillResult<SeckillExecution>(false,"未注册");
        }
        SeckillResult<SeckillExecution> result;

        try {
            SeckillExecution execution = seckillService.executeSeckill(seckillId, userPhone, md5);
            return new SeckillResult<SeckillExecution>(true, execution);
        }catch (RepeatKillException e1)
        {
            SeckillExecution execution=new SeckillExecution(seckillId, SeckillStatEnum.REPEAT_KILL);
            return new SeckillResult<SeckillExecution>(true,execution);
        }catch (SeckillCloseException e2)
        {
            SeckillExecution execution=new SeckillExecution(seckillId, SeckillStatEnum.END);
            return new SeckillResult<SeckillExecution>(true,execution);
        }
        catch (Exception e)
        {
            SeckillExecution execution=new SeckillExecution(seckillId, SeckillStatEnum.INNER_ERROR);
            return new SeckillResult<SeckillExecution>(true,execution);
        }

    }


    /**
     * @Author yangjiayi
     * @Description //获取系统时间
     * @Date 13:29 2019/3/9
     * @Param []
     * @return com.example.seckillboot.dto.SeckillResult<java.lang.Long>
     */
    @RequestMapping(value = "time/now",method = RequestMethod.GET)
    @ResponseBody
    public SeckillResult<Long> time()
    {
        Date now=new Date();
        return new SeckillResult<>(true,now.getTime());
    }
}
