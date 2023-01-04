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
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2022/04/15/14:21
 * @Description:
 */
@Component
public class CheckZlsjJob {
    private static Logger LOGGER = LoggerFactory.getLogger(CheckZlsjJob.class);

    @Value("${excute.quart:false}")
    private boolean excuteQuart;
    @Value("${excute.check.plan:false}")
    private boolean excuteCheckPlan;
    @Value("${excute.check.zlsj:false}")
    private boolean excuteCheckZlsj;

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
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @date 2022/4/15 14:32
     * @description 检查增量数据定时器执行方法
     **/
    public void execute(){
        if(excuteCheckZlsj){
            LOGGER.warn("上次定时任务是否结束: {}", isFinish);
            if (isFinish) {
                LOGGER.warn("开始检查增量数据定时服务: {}", StringRepUtil.showLocalDate());
                List<CheckGzjcLogDO> result = null;
                boolean success = true;
                try {
                    isFinish = false;
                    result = ruleEngine.excuteCheckZlsjTask();
                } catch (Exception e) {
                    success = false;
                    LOGGER.error(null, e);
                } finally {
                    isFinish = true;
                    checkJob.sendMsg(success, result);
                    LOGGER.warn("结束检查增量数据定时服务: {}", StringRepUtil.showLocalDate());
                }
            }

        }

    }
}
