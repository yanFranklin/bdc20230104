package cn.gtmap.realestate.inquiry.util;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 公共的线程池
 */
public class ThreadPoolUtils {

    public static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
            // 核心线程数量
            10,
            // 最大线程数
            50,
            // 空闲超时时间60s
            60, TimeUnit.SECONDS,
            // 阻塞队列
            new ArrayBlockingQueue<>(100),
            // 线程工厂
            new ThreadFactoryBuilder().setNameFormat("inquiry-pool-%d").build(),
            // 过多任务直接主线程处理
            new ThreadPoolExecutor.CallerRunsPolicy()
    );
}
