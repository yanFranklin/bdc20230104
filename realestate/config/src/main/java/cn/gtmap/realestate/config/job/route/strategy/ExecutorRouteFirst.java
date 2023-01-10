package cn.gtmap.realestate.config.job.route.strategy;

import cn.gtmap.realestate.common.job.biz.model.ReturnT;
import cn.gtmap.realestate.common.job.biz.model.TriggerParam;
import cn.gtmap.realestate.config.job.route.ExecutorRouter;

import java.util.List;

/**
 * Created by  on 17/3/10.
 */
public class ExecutorRouteFirst extends ExecutorRouter {

    @Override
    public ReturnT<String> route(TriggerParam triggerParam, List<String> addresslist){
        return new ReturnT<String>(addresslist.get(0));
    }

}
