package cn.gtmap.realestate.building.rabbitmq;


import cn.gtmap.realestate.common.matcher.ZipkinAuditEventRepositoryMatcher;
import cn.gtmap.realestate.building.core.service.BdcdyZtService;
import cn.gtmap.realestate.common.config.mq.consumer.MQConsumer;
import cn.gtmap.realestate.common.config.mq.enums.RabbitMqEnum;
import cn.gtmap.realestate.common.core.dto.building.BatchBdcdyhSyncZtRequestDTO;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-04-29
 * @description 同步业务系统权利状态场景
 */
@Service
public class BdcdyztSyncMqService extends MQConsumer {

    /**
     * 日志组件
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcdyztSyncMqService.class);

    @Autowired
    private BdcdyZtService bdcdyZtService;

    @Autowired
    private ZipkinAuditEventRepositoryMatcher zipkinAuditEventRepository;

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param message
     * @return void
     * @description 同步业务系统权利状态（主要应用于业务系统匹配与取消匹配的逻辑）
     */
    @RabbitListener(containerFactory = "rabbitListenerContainerFactory",queues = "sync-bdcdyzt-queue")
    @RabbitHandler
    public void receiveSyncBdcdyZt(String msg, Channel channel, Message message) throws IOException {
        Map<String,Object> logData = new HashMap<>();
        logData.put("msg",msg);
        String logName = RabbitMqEnum.QueueName.SYNCBDCDYZTQUEUE.getCode();
        AuditEvent event = new AuditEvent(logName,logName,logData);
        zipkinAuditEventRepository.newSpanTag(event,logName);
        consumer(msg,channel,message);
    }

    @Override
    public void processMsg(String message) {
        LOGGER.info("receiveSyncBdcdyZt:{}",message);
        if(StringUtils.isNotBlank(message)){
            try{
                BatchBdcdyhSyncZtRequestDTO batchDTO = JSONObject.parseObject(message,BatchBdcdyhSyncZtRequestDTO.class);
                bdcdyZtService.saveZtWithDTO(batchDTO);
            }catch (Exception e){
                LOGGER.error("同步状态异常,message:{}",message,e);
            }
        }
    }

    @Override
    public void saveErrorMsg(Message message) {

    }
}
