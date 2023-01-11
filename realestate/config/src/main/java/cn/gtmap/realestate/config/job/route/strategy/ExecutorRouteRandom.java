package cn.gtmap.realestate.config.job.route.strategy;

import cn.gtmap.realestate.common.core.dto.ReturnT;
import cn.gtmap.realestate.common.job.biz.model.TriggerParam;
import cn.gtmap.realestate.config.job.route.ExecutorRouter;

import java.util.List;
import java.util.Random;

/**
 * Created by  on 17/3/10.
 */
public class ExecutorRouteRandom extends ExecutorRouter {

    private static Random localRandom = new Random();

    @Override
    public ReturnT<String> route(TriggerParam triggerParam, List<String> addresslist) {
        String address = addresslist.get(localRandom.nextInt(addresslist.size()));
        return new ReturnT<String>(address);
    }

}
