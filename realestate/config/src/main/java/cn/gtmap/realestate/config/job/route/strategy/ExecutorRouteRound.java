package cn.gtmap.realestate.config.job.route.strategy;

import cn.gtmap.realestate.common.job.biz.model.ReturnT;
import cn.gtmap.realestate.common.job.biz.model.TriggerParam;
import cn.gtmap.realestate.config.job.route.ExecutorRouter;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by xuxueli on 17/3/10.
 */
public class ExecutorRouteRound extends ExecutorRouter {

    private static ConcurrentMap<Integer, AtomicInteger> routeCountEachJob = new ConcurrentHashMap<>();
    private static long CACHE_VALID_TIME = 0;

    private static int count(int jobId) {
        // cache clear
        if (System.currentTimeMillis() > CACHE_VALID_TIME) {
            routeCountEachJob.clear();
            CACHE_VALID_TIME = System.currentTimeMillis() + 1000*60*60*24;
        }

        AtomicInteger count = routeCountEachJob.get(jobId);
        if (count == null || count.get() > 1000000) {
            // 初始化时主动Random一次，缓解首次压力
            count = new AtomicInteger(new Random().nextInt(100));
        } else {
            // count++
            count.addAndGet(1);
        }
        routeCountEachJob.put(jobId, count);
        return count.get();
    }

    @Override
    public ReturnT<String> route(TriggerParam triggerParam, List<String> addresslist) {
        String address = addresslist.get(count(triggerParam.getJobId())%addresslist.size());
        return new ReturnT<String>(address);
    }

}
