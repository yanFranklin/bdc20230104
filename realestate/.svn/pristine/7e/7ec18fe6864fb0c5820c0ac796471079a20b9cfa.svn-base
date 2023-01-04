package cn.gtmap.realestate.exchange.quartz;


import cn.gtmap.realestate.exchange.core.domain.BdcXmDO;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcXmMapper;
import cn.gtmap.realestate.exchange.core.qo.BdcXmextendQO;
import cn.gtmap.realestate.exchange.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.exchange.service.AccessModelHandlerService;
import cn.gtmap.realestate.exchange.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.util.CommonUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0  2022-03-01
 * @description 定时上报
 */
@Component
@EnableScheduling
public class AccessQuartzService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccessQuartzService.class);

    /**
     * 是否开启推送共享数据定时任务开关
     */
    @Value("${access.report.enable:false}")
    private boolean accessReportEnable;

    @Autowired
    BdcXmMapper bdcXmMapper;

    @Autowired
    private Repo repository;

    @Autowired
    AccessModelHandlerService accessModelHandlerService;

    /**
     * 定义一次性查询出来1000条记录
     */
    public static final int INTERVAL_SIZE = 1000;

    /**
     * 推送共享数据定时任务
     */
    @Scheduled(cron = "${access.report.quartztime:0 0 22 15 * ?}")
    public void AccessReportQuartz() {
        if (!accessReportEnable) {
            return;
        }
        LOGGER.info("#################### 开启执行定时上报任务，当前时间：{} ###################", new Date());
        String nyr = CommonUtil.getCurrDay();
//        String nyr = "2020-01-06";
        BdcXmextendQO xmextendQO = new BdcXmextendQO();
        xmextendQO.setQszts(Arrays.asList(CommonConstantUtils.QSZT_HISTORY, CommonConstantUtils.QSZT_VALID));
        xmextendQO.setKssj(nyr + " 00:00:00");
        xmextendQO.setJssj(nyr + " 23:59:59");
        List<BdcXmDO> bdcXmDOList = repository.selectList("listUnReportData", xmextendQO);
        LOGGER.info("总共{}条数据", bdcXmDOList.size());
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            for (int i = 0; i < bdcXmDOList.size(); i++) {
                String xmid = bdcXmDOList.get(i).getXmid();
                Integer count = bdcXmMapper.listSbqkCount(xmid);
                if (count > 0) {
                    LOGGER.info("#################### 定时上报任务，当前xmid：{} ###################", xmid);
                    accessModelHandlerService.access(bdcXmDOList.get(i), new ArrayList<>());
                }
            }
            LOGGER.info("#################### 定时上报任务完成，当前时间：{} ###################", new Date());
        }

    }
}
