package cn.gtmap.realestate.etl.quartz;

import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.FwYchsDO;
import cn.gtmap.realestate.common.core.domain.etl.HtbaSpfDO;
import cn.gtmap.realestate.common.core.qo.etl.HtbaxxQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcBhFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.BdcdyFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.FwHsFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.FwYcHsFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcdyZtFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.vo.etl.FgHtfyVO;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.etl.core.mapper.exchange.QuartzMapper;
import cn.gtmap.realestate.etl.service.FcjyDataConvertService;
import cn.gtmap.realestate.etl.service.FgFyService;
import cn.gtmap.realestate.etl.service.HtbaSpfService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @version 1.0  2020/02/19
 * @description 定时同步备案数据到数据库
 */
@Component
@EnableScheduling
public class SchedulerTask {

    /**
     * 是否开启定时同步FG_FYXX数据
     */
    @Value("${tbfhbaxx.enable:false}")
    private Boolean tbfhbaxxEnable;

    /**
     * 是否开启定时同步房产备案库：REALTYPRESALE_STORAGE数据
     */
    @Value("${tbspfhtbaxx.enable:false}")
    private Boolean tbspfhtbaxxEnable;

    /**
     * 是否开启定时合肥定时任务开关
     */
    @Value("${dsjj.sszx.switch:false}")
    private Boolean dsjjsszxSwitch;

    /**
     * 是否开启定时同步Htba_Spf撤销状态任务
     */
    @Value("${tbHtbaSpfCxzt.enable:false}")
    private Boolean tbHtbaSpfCxztEnable;

    @Autowired
    FgFyService fgFyService;
    @Autowired
    BdcdyFeignService bdcdyFeignService;
    @Autowired
    HtbaSpfService htbaSpfService;
    @Autowired
    FwHsFeignService fwHsFeignService;
    @Autowired
    FwYcHsFeignService fwYcHsFeignService;
    @Autowired
    BdcBhFeignService bdcBhFeignService;
    @Autowired
    UserManagerUtils userManagerUtils;
    @Autowired
    BdcdyZtFeignService bdcdyZtFeignService;
    @Autowired
    FcjyDataConvertService fcjyDataConvertService;
    @Autowired
    private QuartzMapper quartzMapper;
    @Autowired
    EntityMapper entityMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerTask.class);

    /**
     * @description 盐城定时任务
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/11/3 17:19
     * @return void
     */
    @Scheduled(cron = "${tbfgbaxx.cron:0 0/5 * * * ?}")
    public void tbFgBaxx() {

        if (!Boolean.TRUE.equals(tbfhbaxxEnable)) {
            LOGGER.info("未开启同步FG_FYXX定时任务");
            return;
        }
        HashMap map = new HashMap();
        String basj = DateFormatUtils.format(new Date(), "yyyy-MM-dd"); //备案时间取当天时间
        map.put("basj", basj);
        map.put("htbazt", "已备案");
        List<FgHtfyVO> fgfyList = fgFyService.selectFgHtfyxx(map);
        LOGGER.warn("定时同步FG_FYXX数据开始{}数据量：{}详细内容为{}", basj, fgfyList.size(), JSON.toJSONString(fgfyList));
        try {
            int count = 0;
            if (CollectionUtils.isNotEmpty(fgfyList)) {
                for (int i = 0; i < fgfyList.size(); i++) {
                    FgHtfyVO fgHtfyVO = fgfyList.get(i);
                    if (StringUtils.isNotBlank(fgHtfyVO.getBdcdyh())) {
                        String bdcdyh = fgHtfyVO.getBdcdyh();
                        HtbaxxQO htbaxxQO = new HtbaxxQO();
                        htbaxxQO.setBdcdyh(bdcdyh);
                        List<HtbaSpfDO> htbaSpfList = htbaSpfService.listHtbaSpf(htbaxxQO);
                        //先查实测再查预测
                        FwHsDO fwhs = fwHsFeignService.queryFwhsByBdcdyh(bdcdyh,"");
                        if (Objects.isNull(fwhs)) {
                            fwhs = new FwHsDO();
                            FwYchsDO fwYchsDO = fwYcHsFeignService.queryFwYcHsByBdcdyh(bdcdyh,"");
                            if (Objects.nonNull(fwYchsDO)) {
                                BeanUtils.copyProperties(fwYchsDO, fwhs);
                            }
                        }
                        if (CollectionUtils.isNotEmpty(htbaSpfList)) {
                            LOGGER.info("合同备案表已查询到的数据量{},数据内容为{},若数据量大于1 只取第一条数据更新", htbaSpfList.size(), JSON.toJSONString(htbaSpfList));
                            HtbaSpfDO htbaSpfDO = htbaSpfList.get(0);

                            /**
                             * @param
                             * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
                             * @description 修改更新备案逻辑，如果已存在备案信息 且备案时间时当天之前的，更新备案数据，备案状态为1 ，更新权籍状态
                             * @date : 2021/5/25 9:07
                             */
                            if (Objects.requireNonNull(DateUtils.formatDate(DateFormatUtils.format(htbaSpfDO.getBasj(), "yyyy-MM-dd"))).getTime() < Objects.requireNonNull(DateUtils.formatDate(basj)).getTime()) {
                                //1.删除原有数据备案数据，新增
                                htbaSpfService.deleteBaxxList(htbaSpfDO.getBaid());
                                count += htbaSpfService.dealBaxx(fwhs, fgHtfyVO, bdcdyh);
                            }
                        } else {
                            count += htbaSpfService.dealBaxx(fwhs, fgHtfyVO, bdcdyh);
                        }
                    }
                }
            }
            LOGGER.warn("定时同步FG_FYXX数据结束,同步数据量为{}", count);
        } catch (Exception e) {
            LOGGER.error("定时同步备案信息异常", e);
        }
    }

    /**
     * @description 连云港同步房产备案库商品房备案合同信息
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/10/27 19:09
     * @return void
     */
    @Scheduled(cron = "${tbspfhtbaxx.cron:0 0 23 * * ? }")
    public void tbSpfHtbaxx() {

        if (!Boolean.TRUE.equals(tbspfhtbaxxEnable)) {
            LOGGER.info("未开启同步商品房合同备案定时任务");
            return;
        }
        try {
            fcjyDataConvertService.convertFcjyHtbaxxAndImoprtBdcDj();
        } catch (Exception e) {
            LOGGER.error("定时同步商品房合同备案信息异常", e);
        }
    }


    /**
     * @description 合肥定时任务
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/11/3 17:19
     * @return void
     */
    @Scheduled(cron = "${dsjj.sszx.cron:0 0 20 * * ?}")
    public void listDjtDjSlSqQuarz() {

        if (!Boolean.TRUE.equals(dsjjsszxSwitch)) {
            LOGGER.info("未开启合肥定时任务");
            return;
        }
        //将未处理的数据存入到redis
        try {
            quartzMapper.updateZsSsxz();
            quartzMapper.updateZmSsxz();
        } catch (Exception e) {
            LOGGER.error("定时任务处理异常", e);
        }
    }

    /**
     * @description 盐城同步Htba_Spf撤销状态任务
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @date 2022/11/14
     * @return void
     */
    @Scheduled(cron = "${tbHtbaSpfCxzt.cron:0 0/5 * * * ?}")
    public void tbHtbaSpfCxzt(){
        if (!Boolean.TRUE.equals(tbHtbaSpfCxztEnable)) {
            LOGGER.info("未开启定时同步Htba_Spf撤销状态任务");
            return;
        }
        HashMap map = new HashMap(2);
        map.put("htbazt", "已撤销");
        List<FgHtfyVO> fgfyList = fgFyService.selectFgHtfyxx(map);
        LOGGER.warn("定时同步Htba_Spf撤销状态数据开始,数据量：{}", fgfyList.size());
        try {
            if (CollectionUtils.isNotEmpty(fgfyList)) {
                for (FgHtfyVO fgHtfyVO : fgfyList) {
                    HtbaxxQO htbaxxQO = new HtbaxxQO();
                    htbaxxQO.setHtbh(fgHtfyVO.getHtbabm());
                    List<HtbaSpfDO> htbaSpfList = htbaSpfService.listHtbaSpf(htbaxxQO);
                    LOGGER.info("合同备案表已查询到的数据内容为{}", JSON.toJSONString(htbaSpfList));
                    if (CollectionUtils.isNotEmpty(htbaSpfList)) {
                        for (HtbaSpfDO htbaSpfDO : htbaSpfList) {
                            if (!htbaSpfDO.getBazt().equals(0)) {
                                htbaSpfDO.setBazt(0);
                                htbaSpfDO.setCxsj(fgHtfyVO.getCxsj());
                                entityMapper.saveOrUpdate(htbaSpfDO, htbaSpfDO.getBaid());
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            LOGGER.error("定时同步Htba_Spf撤销状态异常", e);
        }
    }
}
