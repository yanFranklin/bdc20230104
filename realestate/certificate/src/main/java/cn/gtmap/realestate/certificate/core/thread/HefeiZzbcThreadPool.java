package cn.gtmap.realestate.certificate.core.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
/**
 * @description 合肥存量电子证照制证线程池
 * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
 * @date 2023/1/3 9:44
 */
@Component
public class HefeiZzbcThreadPool {
    /**
     * 核心线程数 5
     */
    @Value("${eCertificate.hfbcsj.clzz.thread.corePoolSize:5}")
    private Integer corePoolSize;

    /**
     * 最大线程数 10
     */
    @Value("${eCertificate.hfbcsj.clzz.thread.maxPoolSize:10}")
    private Integer maxPoolSize;

    /**
     * 允许线程的空闲时间 60s
     */
    @Value("${eCertificate.hfbcsj.clzz.thread.keepAliveSeconds:60}")
    private Integer keepAliveSeconds;

    /**
     * 设置阻塞队列大小 1000，可以按照eCertificate.hfbcsj.clzz.num的大小进行调整
     */
    @Value("${eCertificate.hfbcsj.clzz.thread.queueCapacity:1000}")
    private Integer queueCapacity;

    // 声明一个线程池(并指定线程池的名字)
    @Bean("hefeiZzbcPool")
    public ThreadPoolExecutor hefeiZzbcThreadPool() {
        return new ThreadPoolExecutor(
                // 核心线程数量
                corePoolSize,
                // 最大线程数量
                maxPoolSize,
                // 空闲等待
                keepAliveSeconds, TimeUnit.SECONDS,
                // 阻塞队列100
                new ArrayBlockingQueue<>(queueCapacity),
                new ThreadFactoryBuilder().setNameFormat("hefeidzzzbc-pool-%d").build(),
                // 过多任务直接主线程处理
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

}
