package cn.gtmap.realestate.building.rabbitmq;

import cn.gtmap.realestate.common.matcher.ZipkinAuditEventRepositoryMatcher;
import cn.gtmap.realestate.building.service.BdcdyxxService;
import cn.gtmap.realestate.common.config.mq.consumer.MQConsumer;
import cn.gtmap.realestate.common.config.mq.enums.RabbitMqEnum;
import cn.gtmap.realestate.common.core.dto.building.BdcdyxxPlRequestDTO;
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
 * @version 1.0  2018-12-13
 * @description 同步业务系统不动产单元信息服务
 */
@Service
public class BdcdyxxMqService extends MQConsumer {
    /**
     * 日志组件
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcdyxxMqService.class);

    @Autowired
    private BdcdyxxService bdcdyxxService;

    @Autowired
    private ZipkinAuditEventRepositoryMatcher zipkinAuditEventRepository;

    @RabbitListener(containerFactory = "rabbitListenerContainerFactory",queues = "update-bdcdyxx-queue")
    @RabbitHandler
    public void receiveBdcdyxx(String msg, Channel channel, Message message) throws IOException {
        Map<String,Object> logData = new HashMap<>();
        logData.put("msg",msg);
        String logName = RabbitMqEnum.QueueName.BDCDYXXQUEUE.getCode();
        AuditEvent event = new AuditEvent(logName,logName,logData);
        zipkinAuditEventRepository.newSpanTag(event,logName);
        consumer(msg,channel,message);
    }

    @Override
    public void processMsg(String message) {
        LOGGER.info("receiveBdcdyxx:{}",message);
        if(StringUtils.isNotBlank(message)){
            try{
                BdcdyxxPlRequestDTO requestDTO = JSONObject.parseObject(message,BdcdyxxPlRequestDTO.class);
                bdcdyxxService.updateBdcdyxxPl(requestDTO);
            }catch (Exception e){
                LOGGER.error("同步业务系统不动产单元信息服务异常:{}",message,e);
            }
        }
    }

    @Override
    public void saveErrorMsg(Message message) {
    }
}
