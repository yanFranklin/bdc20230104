package cn.gtmap.realestate.check.core.thread;

import cn.gtmap.gtc.msg.client.MessageClient;
import cn.gtmap.gtc.msg.domain.dto.ProduceMsgDto;
import cn.gtmap.realestate.check.utils.StringRepUtil;
import cn.gtmap.realestate.common.core.domain.check.CheckGzjcLogDO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author lisongtao
 * @version 1.0, 2020-01-1
 * @description 定时器任务执行器
 */
@Component
public class CheckJob {

    @Value("${excute.quart:false}")
    private boolean excuteQuart;
    @Value("${excute.check.plan:false}")
    private boolean excuteCheckPlan;
    @Value("#{'${sendMsg.users:}'.split(',')}")
    private List<String> userNames;
    @Autowired
    private MessageClient messageClient;

    /**
     * log日志对象
     * @author lst
     * @description log日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(CheckJob.class);

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


    /**
     * 定时器执行方法
     * @author lst
     * @description 定时器执行方法 config-quartz.xml中配置定时器执行时间和间隔
     */
    public void execute()  {
        if (excuteQuart && !excuteCheckPlan) {
            logger.warn("上次定时任务是否结束: {}", isFinish);
            if (isFinish) {
                logger.warn("开始定时服务: {}", StringRepUtil.showLocalDate());
                List<CheckGzjcLogDO> result = null;
                boolean success = true;
                try {
                    isFinish = false;
                    result = ruleEngine.excuteTask();
                } catch (Exception e) {
                    success = false;
                    logger.error(null, e);
                } finally {
                    isFinish = true;
                    sendMsg(success, result);
                    logger.warn("结束定时服务: {}", StringRepUtil.showLocalDate());
                }
            }
        }
    }


    /**
     * 发送信息
     * @param success
     * @param result 结果信息
     */
    public void sendMsg(boolean success,List<CheckGzjcLogDO> result){
        if(CollectionUtils.isNotEmpty(userNames) && StringUtils.isNotBlank(userNames.get(0))){
            ProduceMsgDto produceMsgDto = new ProduceMsgDto();
            produceMsgDto.setClientId("bpmInstance");
            produceMsgDto.setMsgCode("CHECK_SJZJ");
            produceMsgDto.setMsgType("CUSTOM");
            produceMsgDto.setMsgTypeName("系统消息");
            produceMsgDto.setMsgTitle("成果检测通知");
            produceMsgDto.setConsumer(StringUtils.join(userNames, CommonConstantUtils.ZF_YW_DH));
            produceMsgDto.setConsumerType("batch");
            produceMsgDto.setProducer("system");
            produceMsgDto.setProducerType("personal");
            produceMsgDto.setNotifyType("rabbitmq");
            if (success) {
                int wtsl = 0;
                if (CollectionUtils.isNotEmpty(result)) {
                    wtsl=result.size();
                }
                produceMsgDto.setMsgContent("已完成数据检测任务,检测出 "+wtsl+" 个问题,请查看!");
            }else{
                produceMsgDto.setMsgContent("数据检测任务失败,请查看系统错误日志!");
            }
            produceMsgDto.setRead(0);
            produceMsgDto.setOptions("save");
            messageClient.saveProduceMsg(produceMsgDto);
        }
    }
}
