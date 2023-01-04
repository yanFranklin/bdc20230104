
package cn.gtmap.realestate.exchange.core.thread;

import cn.gtmap.realestate.exchange.core.support.spring.Container;
import cn.gtmap.realestate.exchange.core.support.spring.EnvironmentConfig;
import cn.gtmap.realestate.exchange.util.DateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;


/**
 * 线程池容器处理
 *
 * @author lst
 * @version v1.0, 18-06-12
 * @description 控制线程池 将批量的逻辑分批次处理
 */
@Component
@Import({ThreadTaskExecutor.class, Container.class})
public class ThreadEngine {

    /**
     * log日志对象
     *
     * @author lst
     * @description log日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadEngine.class);


    /**
     * 多线程调用主方法
     *
     * @param list
     * @param wait
     * @return
     */
    public <T> List<T> excuteThread(List<T> list, boolean wait) {
        return excuteThread(list, wait, null);
    }


    /**
     * 多线程调用主方法
     *
     * @param list
     * @param wait
     * @param taskMap 需要对线程池做处理的使用此参数存储对象
     * @return
     */
    public <T> List<T> excuteThread(List<T> list, boolean wait, Map<String, Object> taskMap) {
        if (CollectionUtils.isNotEmpty(list)) {
            //存储线程对象
            List<Future> listThread = new ArrayList<>();
            ThreadPoolTaskExecutor taskExecutor = Container.getBean(ThreadTaskExecutor.class);
            if (taskMap != null) {
                taskMap.put("taskExecutor", taskExecutor);
            }
            String name = StringUtils.EMPTY;
            if (list.get(0) != null) {
                name = list.get(0).getClass().getSimpleName();
            }
            String dateStr = DateUtils.formateTimeYmdhms(new Date());
            taskExecutor.setThreadNamePrefix(dateStr + name + ":总数" + list.size());
            int countSize = list.size();
            //判定是否不计数
            boolean sfbjs = ((CommonThread) list.get(0)).isSfbjs();
            if (!sfbjs) {
                //验证当前所剩线程数 走线程分配规则
                threadRule(countSize, 0, taskExecutor);
            }
            for (int i = 0; i < countSize; i++) {
                Future future = taskExecutor.submit((Runnable) list.get(i));
                listThread.add(future);
            }
            //是否需要等待
            if (wait) {
                shutDownThread(taskExecutor, sfbjs, listThread);
            }
        }
        return list;
    }

    /**
     * 等待线程池中所有线程结束后关闭
     */
    private void shutDownThread(ThreadPoolTaskExecutor taskExecutor, boolean sfbjs, List<Future> list) {
        //线程池
        ThreadPoolExecutor threadPoolExecutor = taskExecutor.getThreadPoolExecutor();
        //lst 队列任务
        BlockingQueue<Runnable> queue = threadPoolExecutor.getQueue();
        //等待时常
        long millis = 50L;
        int times = 0;
        while (true) {
            int count = threadPoolExecutor.getActiveCount();
            int queueSize = queue.size();
            if (queueSize == 0) {
                for (Future future : list) {
                    //shutDown后不做判断
                    if (threadPoolExecutor.isShutdown()) {
                        break;
                    }
                    //没取消也没完成的时候
                    while (!future.isDone() && !future.isCancelled()) {
                        LOGGER.info(taskExecutor.getThreadNamePrefix() + "-线程池还有在工作的线程");
                        try {
                            Thread.sleep(millis);
                        } catch (Exception e) {
                            LOGGER.error(null, e);
                        }
                    }
                }
                taskExecutor.destroy();
                break;
            } else {
                LOGGER.info(taskExecutor.getThreadNamePrefix() + "-线程池还在工作中，当前触发{}个线程，队列中存在{}个任务排队", count, queueSize);
                //计数的话时间变化
                if (!sfbjs) {
                    if (times > 20) {
                        millis = 200L;
                    } else if (times > 10) {
                        millis = 100L;
                    }
                }
                try {
                    Thread.sleep(millis);
                } catch (Exception e) {
                    LOGGER.error(null, e);
                }
                times++;
            }
        }
    }

    /**
     * 线程分配规则
     *
     * @param size               任务数量
     * @param count              循环数量
     * @param threadTaskExecutor 线程池对象
     * @return 返回线程池对象
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    private ThreadPoolTaskExecutor threadRule(Integer size, int count, ThreadPoolTaskExecutor threadTaskExecutor) {
        /**
         * 定义最大支持的线程数
         */
        Integer maxSize = EnvironmentConfig.getEnvironment().getProperty("thread.max", Integer.class, 150);
        //剩余线程数
        Integer surplusSize = maxSize - CommonThread.getSumThread();
        //线程池
        ThreadPoolExecutor threadPoolExecutor = threadTaskExecutor.getThreadPoolExecutor();
        //是否还有剩余线程可用
        boolean hasThread = surplusSize > 0;
        //线程池maxSize
        Integer maxPoolSize = threadPoolExecutor.getMaximumPoolSize();
        //有的情况
        if (hasThread) {
            //不足最大线程时 改变线程池最大线程数
            Integer setSize = maxPoolSize;
            if (size < maxPoolSize) {
                if (size > surplusSize) {
                    setSize = surplusSize;
                } else {
                    setSize = size;
                }
            } else {
                if (surplusSize < maxPoolSize) {
                    setSize = surplusSize;
                }
            }
            threadTaskExecutor.setCorePoolSize(setSize);
            threadPoolExecutor.setCorePoolSize(setSize);
            threadTaskExecutor.setMaxPoolSize(setSize);
            threadPoolExecutor.setMaximumPoolSize(setSize);
        } else {
            //无空余线程时 所需线程数较少时直接走单线程 否则等待
            if (size < 10) {
                threadTaskExecutor.setCorePoolSize(1);
                threadPoolExecutor.setCorePoolSize(1);
                threadTaskExecutor.setMaxPoolSize(1);
                threadPoolExecutor.setMaximumPoolSize(1);
            } else {
                //等待时间超过5s的话给予5个线程进行处理
                if (count > 10) {
                    threadTaskExecutor.setCorePoolSize(5);
                    threadPoolExecutor.setCorePoolSize(5);
                    threadTaskExecutor.setMaxPoolSize(5);
                    threadPoolExecutor.setMaximumPoolSize(5);
                } else {
                    try {
                        Thread.sleep(500L);
                        ++count;
                        threadRule(size, count, threadTaskExecutor);
                    } catch (Exception e) {
                        LOGGER.error(null, e);
                    }
                }
            }
        }
        return threadTaskExecutor;
    }


}

