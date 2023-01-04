package cn.gtmap.realestate.engine.thread;

import cn.gtmap.realestate.common.core.dto.engine.BdcGzZgzDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @description 规则验证处理线程池
 */
@Component
public class RuleCheckThreadPool {
    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RuleCheckThreadPool.class);

    /**
     * 线程池核心线程数
     */
    @Value("${thread.core:10}")
    private Integer coreTheadNum;

    /**
     * 线程池最大线程数
     */
    @Value("${thread.max:20}")
    private Integer maxTheadNum;

    /**
     * 线程池监控时间间隔
     */
    @Value("${thread.threadPoolMonitorTime:10}")
    private Integer threadPoolMonitorTime;

    /**
     * 线程池是否需要监控
     */
    @Value("${thread.needMonitor:false}")
    private Boolean needMonitor;

    /**
     * 线程任务提供者
     */
    @Autowired
    private ThreadProvider threadProvider;

    /**
     * 子规则数据流获取数据共享线程池定义
     */
    private ThreadPoolExecutor threadPool;


    /**
     * 初始化规则数据流处理线程池
     */
    @PostConstruct
    public void init(){
        // 初始化线程池
        initThreadPool();

        // 监控线程池
        if(needMonitor) {
            monitorThreadPool();
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzZgzDTO 子规则信息DTO
     * @param paramMap  验证参数
     * @description 提交任务到线程池
     */
    public Future submitTask(BdcGzZgzDTO bdcGzZgzDTO, Map<String, Object> paramMap){
        return threadPool.submit(threadProvider.getThread(bdcGzZgzDTO, paramMap));
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 线程池定义
     */
    private void initThreadPool(){
        threadPool = new ThreadPoolExecutor(
                // 核心线程数量
                coreTheadNum,
                // 最大线程数
                maxTheadNum,
                // 空闲超时一小时（规则验证调用频繁）
                3600, TimeUnit.SECONDS,
                // 这边需要大于前面 BdcGzZhgzCheckServiceImpl.semaphore 入口的限流量
                new ArrayBlockingQueue<>(20100),
                // 过多任务直接主线程处理
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 线程池监控
     */
    private void monitorThreadPool(){
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

        // 初始等待2分钟，即应用启动完成后开始监控
        // 每隔10秒钟监控一次
        service.scheduleAtFixedRate(new MonitorTask(), 120, threadPoolMonitorTime, TimeUnit.SECONDS);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 线程池监控内容
     */
    private class MonitorTask implements Runnable {
        @Override
        public void run() {
            try {
                if(!threadPool.isShutdown()){
                    LOGGER.debug("活动线程数：" + threadPool.getActiveCount() + "；队列等待任务数：" + threadPool.getQueue().size());
                } else {
                    LOGGER.error("规则验证线程池异常关闭！");
                }
            } catch (Exception e){
                LOGGER.info("获取线程池状态异常！{}", e.toString());
            }
        }
    }
}
