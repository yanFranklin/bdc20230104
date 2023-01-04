package cn.gtmap.realestate.accept.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 初始化公共线程池
 */
public class LocalThreadPool {
    public static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
            // 核心线程数量
            30,
            // 最大线程数量
            100,
            // 空闲等待
            600L, TimeUnit.SECONDS,
            // 阻塞队列
            new ArrayBlockingQueue<>(10000),
            // 过多任务直接主线程处理
            new ThreadPoolExecutor.CallerRunsPolicy());
}
