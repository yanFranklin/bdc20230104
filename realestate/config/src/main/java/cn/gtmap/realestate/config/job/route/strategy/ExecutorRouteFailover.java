package cn.gtmap.realestate.config.job.route.strategy;

import cn.gtmap.realestate.common.job.biz.ExecutorBiz;
import cn.gtmap.realestate.common.core.dto.ReturnT;
import cn.gtmap.realestate.common.job.biz.model.TriggerParam;
import cn.gtmap.realestate.config.job.route.ExecutorRouter;
import cn.gtmap.realestate.config.job.scheduler.XxlJobScheduler;
import cn.gtmap.realestate.config.job.util.I18nUtil;

import java.util.List;

/**
 * Created by  on 17/3/10.
 */
public class ExecutorRouteFailover extends ExecutorRouter {

    @Override
    public ReturnT<String> route(TriggerParam triggerParam, List<String> addresslist) {

        StringBuffer beatResultSB = new StringBuffer();
        for (String address : addresslist) {
            // beat
            ReturnT<String> beatResult = null;
            try {
                ExecutorBiz executorBiz = XxlJobScheduler.getExecutorBiz(address);
                beatResult = executorBiz.beat();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                beatResult = new ReturnT<String>(ReturnT.FAIL_CODE, ""+e );
            }
            beatResultSB.append( (beatResultSB.length()>0)?"<br><br>":"")
                    .append(I18nUtil.getString("jobconf_beat") + "：")
                    .append("<br>address：").append(address)
                    .append("<br>code：").append(beatResult.getCode())
                    .append("<br>msg：").append(beatResult.getMsg());

            // beat success
            if (beatResult.getCode() == ReturnT.SUCCESS_CODE) {

                beatResult.setMsg(beatResultSB.toString());
                beatResult.setContent(address);
                return beatResult;
            }
        }
        return new ReturnT<String>(ReturnT.FAIL_CODE, beatResultSB.toString());

    }
}
