package cn.gtmap.realestate.inquiry.ui.config;

import cn.gtmap.realestate.inquiry.ui.core.service.BdcXuanchengJscService;
import cn.gtmap.realestate.inquiry.ui.web.rest.jsc.BdcXuanchengJscController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2022/06/21/9:37
 * @Description:
 */
@Component
public class QueryJscResult implements ApplicationListener<ApplicationEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueryJscResult.class);

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if (applicationEvent instanceof ApplicationReadyEvent) {
            ApplicationContext context = ((ApplicationReadyEvent) applicationEvent).getApplicationContext();
            BdcXuanchengJscService initService = context.getBean(BdcXuanchengJscService.class);
            if (Objects.nonNull(initService)) {
                initService.ScheduledCacheQueryData();
                LOGGER.info("初始化刷新驾驶舱请求数据");
            }
            return;
        }
    }


}
