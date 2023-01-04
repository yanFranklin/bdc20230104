package cn.gtmap.realestate.exchange.core.thread;

import cn.gtmap.realestate.exchange.core.support.spring.EnvironmentConfig;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/6/13.
 * @description
 */
@Component
@Scope("prototype")
@Import({EnvironmentConfig.class})
public class ThreadTaskExecutor extends ThreadPoolTaskExecutor {


    /**
     * 处理线程参数  构造函数
     *
     * @param
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    public ThreadTaskExecutor() {
        Integer maxSize = EnvironmentConfig.getEnvironment().getProperty("max.poolsize", Integer.class, 50);
        //lst 默认50  优先走max.poolsize配置 最大不能超过200或小于0
        if (maxSize < 1) {
            maxSize = 50;
        }
        //最大设置200
        if (maxSize > 200) {
            maxSize = 200;
        }
        super.setCorePoolSize(maxSize);
        super.setMaxPoolSize(maxSize);
        super.setWaitForTasksToCompleteOnShutdown(true);
    }


}
