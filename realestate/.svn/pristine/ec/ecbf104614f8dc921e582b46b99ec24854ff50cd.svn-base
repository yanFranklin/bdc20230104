package cn.gtmap.realestate.etl.config;

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
import cn.gtmap.realestate.common.util.ListUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.etl.service.FgFyService;
import cn.gtmap.realestate.etl.service.HtbaSpfService;
import cn.gtmap.realestate.etl.util.Constants;
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

import java.util.*;
import java.util.concurrent.*;

/**
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @version 1.0  2020/02/19
 * @description 定时同步备案数据到数据库
 */
@Component
@EnableScheduling
public class SchedulerTask {
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
    EntityMapper entityMapper;
    @Autowired
    BdcdyZtFeignService bdcdyZtFeignService;

    private ExecutorService executor = new ThreadPoolExecutor(
            // 核心线程数量
            5,
            // 最大线程数
            10,
            // 超时30秒
            30, TimeUnit.SECONDS,
            // 最大允许等待200个任务
            new ArrayBlockingQueue<>(200),
            // 过多任务直接主线程处理
            new ThreadPoolExecutor.CallerRunsPolicy()
    );


    /**
     * 是否开启定时同步Htba_Spf撤销状态任务
     */
    @Value("${tbHtbaSpfCxzt.enable:true}")
    private Boolean tbHtbaSpfCxztEnable;

    private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerTask.class);

    @Scheduled(cron = "${tbfgbaxx.cron:0 0/5 * * * ?}")
    public void tbFgBaxx() {
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
     * @description 盐城同步Htba_Spf撤销状态任务
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @date 2022/11/14
     * @return void
     */
    @Scheduled(cron = "${tbHtbaSpfCxzt.cron:0 0/5 * * * ?}")
    public void tbHtbaSpfCxzt() throws Exception {
        if (!Boolean.TRUE.equals(tbHtbaSpfCxztEnable)) {
            LOGGER.info("未开启定时同步Htba_Spf撤销状态任务");
            return;
        }
        List<Future<Object>> futureList = new ArrayList<>();
        HashMap map = new HashMap(2);
        map.put("htbazt", "已撤销");
        List<FgHtfyVO> fgfyList = fgFyService.selectYcxFghtfy(map);
        LOGGER.warn("定时同步Htba_Spf撤销状态数据开始,数据量：{}", fgfyList.size());
        List<List> partList = ListUtils.subList(fgfyList, 200);
        for(List list : partList){
            futureList.add(executor.submit(new TbHtbaSpfCxztThread(list)));
        }
        for (Future<Object> future : futureList) {

            future.get();
        }
        LOGGER.warn("定时同步Htba_Spf撤销状态数据结束,数据量：{}", fgfyList.size());
    }

    class TbHtbaSpfCxztThread implements Callable<Object> {
        private List<FgHtfyVO>  fgfyList;
        public TbHtbaSpfCxztThread(List<FgHtfyVO>  fgfyList){
            this.fgfyList = fgfyList;
        }

        @Override
        public Object call() {
            tbcxzt(fgfyList);
            return 1;
        }
    }

    public void tbcxzt(List<FgHtfyVO>  fgfyList){
        try {
            if (CollectionUtils.isNotEmpty(fgfyList)) {
                List<HtbaSpfDO> htbaSpfDOS = new ArrayList<>();
                for (FgHtfyVO fgHtfyVO : fgfyList) {
                    HtbaxxQO htbaxxQO = new HtbaxxQO();
                    htbaxxQO.setHtbh(fgHtfyVO.getHtbabm());
                    List<HtbaSpfDO> htbaSpfList = htbaSpfService.listHtbaSpf(htbaxxQO);
                    //LOGGER.info("合同备案表查询到的数据内容为{}", JSON.toJSONString(htbaSpfList));
                    if (CollectionUtils.isNotEmpty(htbaSpfList)) {
                        for (HtbaSpfDO htbaSpfDO : htbaSpfList) {
                            htbaSpfDO.setBazt(0);
                            htbaSpfDO.setCxsj(fgHtfyVO.getCxsj());
                            htbaSpfDOS.add(htbaSpfDO);
                        }
                    }
                }
                if(CollectionUtils.isNotEmpty(htbaSpfDOS)){
                    LOGGER.info("批量更新合htbaSpfDOS为数量为：{}", JSON.toJSONString(htbaSpfDOS.size()));
                    entityMapper.batchSaveSelective(htbaSpfDOS);
                }
            }
        }catch (Exception e){
            LOGGER.error("定时同步Htba_Spf撤销状态异常", e);
        }
    }
}
