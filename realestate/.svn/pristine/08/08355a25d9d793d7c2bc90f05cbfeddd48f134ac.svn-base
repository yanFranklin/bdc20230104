package cn.gtmap.realestate.check.core.thread;

import cn.gtmap.realestate.check.utils.StringRepUtil;
import cn.gtmap.realestate.common.core.domain.check.CheckGzjcLogDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/7/30.
 * @description
 */
@Component
public class CheckPlanJob {
    private static Logger LOGGER = LoggerFactory.getLogger(CheckPlanJob.class);

    @Value("${excute.quart:false}")
    private boolean excuteQuart;
    @Value("${excute.check.plan:false}")
    private boolean excuteCheckPlan;
    /**
     * 规则引擎对象
     * @author lst
     * @description 规则引擎对象(单例模式)
     */
    @Autowired
    private RuleEngine ruleEngine;

    /**
     * 判断上次定时任务是够结束的标识状态
     * @author lst
     * @description 判断上次定时任务是够结束的标识状态(true:已结束,false:未结束)，未结束不会开启第二次任务
     */
    private  boolean isFinish = true;

    @Autowired
    private CheckJob checkJob;
    /**
     * 检查计划定时器执行方法
     * @author ccx
     * @description 定时器执行方法 config-quartz.xml中配置定时器执行时间和间隔
     */
    public void execute(){
        if (!excuteQuart && excuteCheckPlan) {
            LOGGER.warn("上次定时任务是否结束: {}", isFinish);
            if (isFinish) {
                LOGGER.warn("开始检查计划定时服务: {}", StringRepUtil.showLocalDate());
                List<CheckGzjcLogDO> result = null;
                boolean success = true;
                try {
                    isFinish = false;
                    result = ruleEngine.excutePlanTask();
                } catch (Exception e) {
                    success = false;
                    LOGGER.error(null, e);
                } finally {
                    isFinish = true;
                    checkJob.sendMsg(success, result);
                    LOGGER.warn("结束检查计划定时服务: {}", StringRepUtil.showLocalDate());
                }
            }
        }
    }

}
