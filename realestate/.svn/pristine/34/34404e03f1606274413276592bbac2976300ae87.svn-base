package cn.gtmap.realestate.exchange.core.thread;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/8/7.
 * @description
 */
public abstract class CommonThread implements Runnable {

    /**
     * 线程池
     */
    private Map<String, Object> taskMap;
    /**
     * 是否不计数的逻辑
     */
    private boolean sfbjs;

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonThread.class);
    /**
     * 线程数量
     */
    private static AtomicInteger sumThread = new AtomicInteger(0);

    public static Integer getSumThread() {
        return sumThread.get();
    }


    @Override
    public void run() {
        //自增  增加当前线程总数
        if (!sfbjs) {
            sumThread.incrementAndGet();
        }
        //默认处理方法
        try {
            execute();
        } catch (Exception e) {
            //不等待任务结束 直接停止所有任务
            if (taskMap != null && taskMap.get("taskExecutor") != null && !taskMap.containsKey("msg")) {
                //传递错误异常信息
                taskMap.put("msg", StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "");
                ThreadPoolTaskExecutor threadPoolTaskExecutor = (ThreadPoolTaskExecutor) taskMap.get("taskExecutor");
                threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(false);
                threadPoolTaskExecutor.destroy();
                LOGGER.error(threadPoolTaskExecutor.getThreadNamePrefix(), e);
            } else if (MapUtils.isEmpty(taskMap)) {
                LOGGER.error(null, e);
            }
        } finally {
            //自减 减少当前线程总数
            if (!sfbjs) {
                sumThread.decrementAndGet();
            }
        }
    }

    /**
     * 默认执行方法
     */
    public abstract void execute() throws Exception;

    public void setTaskMap(Map<String, Object> taskMap) {
        this.taskMap = taskMap;
    }

    public void setSfbjs(boolean sfbjs) {
        this.sfbjs = sfbjs;
    }

    public boolean isSfbjs() {
        return sfbjs;
    }
}
