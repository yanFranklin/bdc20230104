package cn.gtmap.interchange.quartz;

import cn.gtmap.interchange.core.dto.CommonResponse;
import cn.gtmap.interchange.core.vo.BdcXtggVO;
import cn.gtmap.interchange.service.BdcXtggService;
import cn.gtmap.interchange.util.CommonService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0  2021-04-21
 * @description 盐城系统公告信息抽取推送定时任务
 */
@Service
public class BdcXtggQuartzTask {
    private static final Logger logger = LoggerFactory.getLogger(BdcXtggQuartzTask.class);

    @Autowired
    private BdcXtggService bdcXtggService;

    @Autowired
    private CommonService commonService;

    /**
     * 定时从登记系统获取系统公告进行推送
     */
    @Scheduled(cron = "${yancheng.xtggts.cron:0 0 2 1 * ?}")
    public void xtggTask() {
        logger.info("##############################################");
        try {
            logger.info("定时推送公告信息任务开始！");
            BdcXtggVO bdcXtggVO = new BdcXtggVO();
            bdcXtggVO.setStartTime(commonService.startTime());
            bdcXtggVO.setEndTime(commonService.endTime());
            bdcXtggVO.setGglx(commonService.getGglx());
            bdcXtggVO.setSply(commonService.getSply());
            bdcXtggVO.setGzldyId(commonService.getProperties().getGzldyid());

            CommonResponse response = bdcXtggService.pushXtgg(bdcXtggVO);
            if (response.isSuccess()) {
                logger.info("定时推送公告信息任务结束！");
            } else {
                logger.error("定时推送公告信息任务失败，请核查日志！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("定时推送公告信息任务失败，请核查日志！");
        }
        logger.info("##############################################");
    }
}
