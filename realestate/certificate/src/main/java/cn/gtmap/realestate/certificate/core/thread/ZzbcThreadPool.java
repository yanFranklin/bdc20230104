package cn.gtmap.realestate.certificate.core.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class ZzbcThreadPool {
    /**
     * 核心线程数 12
     */
    @Value("${dzzz.clsj.thread.corePoolSize:6}")
    private Integer corePoolSize;

    /**
     * 最大线程数
     */
    @Value("${dzzz.clsj.thread.maxPoolSize:12}")
    private Integer maxPoolSize;

    /**
     * 允许线程的空闲时间 60s
     */
    @Value("${dzzz.clsj.thread.keepAliveSeconds:60}")
    private Integer keepAliveSeconds;

    /**
     * 设置阻塞队列大小 500
     */
    @Value("${dzzz.clsj.thread.queueCapacity:500}")
    private Integer queueCapacity;

    // 声明一个线程池(并指定线程池的名字)
    @Bean("zzbcPool")
    public ThreadPoolExecutor zzbcThreadPool() {
        return new ThreadPoolExecutor(
                // 核心线程数量
                corePoolSize,
                // 最大线程数量
                maxPoolSize,
                // 空闲等待
                keepAliveSeconds, TimeUnit.SECONDS,
                // 阻塞队列100
                new ArrayBlockingQueue<>(queueCapacity),
                new ThreadFactoryBuilder().setNameFormat("dzzzbc-pool-%d").build(),
                // 过多任务直接主线程处理
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

}
