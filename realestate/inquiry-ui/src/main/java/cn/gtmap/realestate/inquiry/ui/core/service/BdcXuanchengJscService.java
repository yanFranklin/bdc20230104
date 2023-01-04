package cn.gtmap.realestate.inquiry.ui.core.service;

import cn.gtmap.realestate.common.core.qo.inquiry.jsc.JscCommomQO;
import cn.gtmap.realestate.inquiry.ui.web.rest.jsc.BdcXuanchengJscController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author wyh
 * @version 1.0
 * @date 2022/8/20 11:41
 */
@Component
public class BdcXuanchengJscService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcXuanchengJscController.class);

    @Autowired
    BdcXuanchengJscController bdcXuanchengJscController;

    @Value("${jsc.cache.enable:false}")
    private Boolean cacheEnable;

    /**
     * 定时查询数据获取缓存
     */
    @Scheduled(cron = "* 0/5 * * * ?")
    public void ScheduledCacheQueryData() {
        if (!cacheEnable) {
            return;
        }
        try {
            LOGGER.info("刷新驾驶舱请求数据");
            JscCommomQO jscCommomQO = new JscCommomQO();
            bdcXuanchengJscController.queryJscTransaction(jscCommomQO);
            bdcXuanchengJscController.queryJscMapData(jscCommomQO);
            jscCommomQO.setTimeFrame("1");
            bdcXuanchengJscController.queryJscSummary(jscCommomQO);
            bdcXuanchengJscController.queryJscDjZmj(jscCommomQO);
            bdcXuanchengJscController.queryJscQl(jscCommomQO);
            jscCommomQO.setSummaryDimension("MONTH");
            jscCommomQO.setTimeFrame(null);
            bdcXuanchengJscController.queryJscTrend(jscCommomQO);
            jscCommomQO.setSummaryDimension("YEAR");
            jscCommomQO.setTimeFrame("5");
            bdcXuanchengJscController.queryJscDjslList(jscCommomQO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
