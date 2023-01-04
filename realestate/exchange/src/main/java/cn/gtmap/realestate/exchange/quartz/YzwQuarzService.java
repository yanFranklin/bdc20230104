package cn.gtmap.realestate.exchange.quartz;


import cn.gtmap.realestate.common.core.annotations.RedissonLock;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.exchange.core.dto.yzw.YzwCheckAndTsResultDTO;
import cn.gtmap.realestate.exchange.core.mapper.exchange.YzwCheckMapper;
import cn.gtmap.realestate.exchange.service.inf.yzw.YzwCheckService;
import cn.gtmap.realestate.exchange.service.inf.yzw.YzwService;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2020-11-26
 * @description 一张网 相关定时任务
 */
@Component
@EnableScheduling
public class YzwQuarzService {

    @Value("#{'${yzw.checkQlyg.yzlxList:}'.split(',')}")
    private  List<String> yzlxList;

    /**
     * 推送数据到一张网定时任务开关
     */
    @Value("${yzw.sharedata.quartz.enable:false}")
    private Boolean shareDataEnable;

    /**
     * 推送数据到一张网查询项目表n天内数据
     */
    @Value("${yzw.sharedata.days:2}")
    private Integer shareDataDays;

    // 中间库验证 定时任务处理是否结束标志
    private static boolean yzwChecked = true;


    private static final Logger logger = LoggerFactory.getLogger(YzwQuarzService.class);

    @Autowired
    private YzwCheckService yzwCheckService;

    @Autowired
    private YzwCheckMapper yzwCheckMapper;

    @Autowired
    private YzwService yzwService;

    @Autowired
    @Qualifier("entityMapper")
    private EntityMapper entityMapper;


    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  定时一张网验证
     */
    @Scheduled(cron = "${yzw.checkQlyg.cron:0 0 23 * * ?}")
    @RedissonLock(lockKey = Constants.YZW_CHECK_TASK_JOB_NAME, description = "定时一张网验证定时任务", waitTime = 60L, leaseTime = 60L)
    public void yzwCheck(){
        if(StringUtils.equals(EnvironmentConfig.getEnvironment()
                .getProperty("yzw.checkQlyg.switch"),"true") && yzwChecked){
            logger.info("定时检查一张网中间库数据开始");
            //查询结果未推送的一张网数据
            List<String> yzwbhList=yzwCheckMapper.listWtsjgYzwbh();
            if(CollectionUtils.isEmpty(yzwbhList)){
                logger.info("不存在需要验证的一张网数据,检查结束");
            }
            logger.info("需要验证的一张网数据共：{}条",yzwbhList.size());
            List<String> failyzwbhList=new ArrayList<>();
            List<YzwCheckAndTsResultDTO> yzwCheckAndTsResultDTOList =new ArrayList<>();
            for(String yzwbh:yzwbhList) {
                YzwCheckAndTsResultDTO yzwCheckAndTsResultDTO =yzwCheckService.checkYzwData(yzwbh, yzlxList);
                if(yzwCheckAndTsResultDTO != null && yzwCheckAndTsResultDTO.getSblx() != null){
                    failyzwbhList.add(yzwbh);
                    yzwCheckAndTsResultDTO.setTszt(Constants.YZW_TSZT_TSSB);
                }else if(yzwCheckAndTsResultDTO != null){
                    yzwCheckAndTsResultDTO.setTszt(Constants.YZW_TSZT_WTS);
                }
                yzwCheckAndTsResultDTOList.add(yzwCheckAndTsResultDTO);
            }
            //保存
            yzwService.saveYzwCheckAndTsResultDTO(yzwCheckAndTsResultDTOList,true);

            logger.info("验证一张网问题数据共：{}条,具体一张网编号如下:{}",failyzwbhList.size(),failyzwbhList);
            logger.info("定时检查一张网中间库数据结束");
        }
    }

    /**
     * @description 一张网数据推送定时任务【连云港】
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/11/11 10:18
     */
    @Scheduled(cron = "${yzw.sharedata.cron:0 0 23 * * ?}")
    @RedissonLock(lockKey = Constants.YZW_SHAREDATA_TASK_JOB_NAME, description = "一张网数据推送定时任务", waitTime = 60L, leaseTime = 60L)
    public void yzwShareData(){
        if(Boolean.TRUE.equals(shareDataEnable)){
            logger.info("定时推送办结数据到一张网中间库数据开始");
            // 查询不动产登记已办结数据
            Example example = new Example(BdcXmDO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("ajzt", 2);
            criteria.andGreaterThanOrEqualTo("djsj", getShareDateStartDate());
            criteria.andIsNull("zfxzspbh");
            List<BdcXmDO> bdcXmDOList = entityMapper.selectByExample(example);
            if (CollectionUtils.isEmpty(bdcXmDOList)) {
                logger.info("不存在需要推送一张网数据,定时结束结束");
                return;
            }
            List<String> gzlslidList = bdcXmDOList.stream().map(bdcXmDO -> bdcXmDO.getGzlslid()).distinct().collect(Collectors.toList());
            if (CollectionUtils.isEmpty(gzlslidList)) {
                logger.info("不存在需要推送一张网数据,定时结束结束");
                return;
            }
            logger.info("推送一张网问题数据共：{}条,具体工作流实例ID如下:{}",gzlslidList.size(),gzlslidList);
            for (String gzlslid : gzlslidList) {
                try {
                    if (StringUtils.isNotBlank(gzlslid)) {
                        yzwService.shareYzwData(gzlslid, true);
                    }
                } catch (Exception e) {
                    logger.error("工作流实例ID：{}推送一张网数据失败", gzlslid, e);
                }

            }
            logger.info("定时推送办结数据到一张网中间库数据结束");
        }
    }

    /**
     * 获取推送一张网数据查询开始日期
     * @return
     */
    private Date getShareDateStartDate() {
        if(null == shareDataDays || shareDataDays < 1 || shareDataDays > 7) {
            // 避免查询范围过大导致应用OOM
            shareDataDays = 1;
        }

        // 天数往前算
        Date preDay = org.apache.commons.lang3.time.DateUtils.addDays(new Date(), - shareDataDays);
        return DateUtils.getStartDate(preDay);
    }

}
