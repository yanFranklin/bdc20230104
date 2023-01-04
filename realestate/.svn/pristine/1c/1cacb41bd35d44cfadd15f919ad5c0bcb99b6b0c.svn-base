package cn.gtmap.realestate.init.service.rabbitmq;

import cn.gtmap.realestate.common.matcher.ZipkinAuditEventRepositoryMatcher;
import cn.gtmap.realestate.common.config.mq.consumer.MQConsumer;
import cn.gtmap.realestate.common.config.mq.enums.RabbitMqEnum;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.init.config.InitBeanFactory;
import cn.gtmap.realestate.init.core.service.BdcQllxService;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
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
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/9/4.
 * @description
 */
@Service
public class HxQlFjMQConsumer extends MQConsumer {
    @Autowired
    ZipkinAuditEventRepositoryMatcher zipkinAuditEventRepository;
    @Autowired
    BdcQllxService bdcQllxService;
    @Autowired
    InitBeanFactory initBeanFactory;
    @Autowired
    EntityMapper entityMapper;
      /**
      * @author chenchunxue 2020/9/7
      * @param msg
      * @param channel
      * @param message
      * @return void
      * @description 批量生成证书回写权利附记
      */
    @RabbitListener(containerFactory = "rabbitListenerContainerFactory",queues = "hxqlfj-queue")
    @RabbitHandler
    public void hxQlFj(String msg, Channel channel, Message message) throws IOException {
        Map<String,Object> logData = new HashMap<>();
        logData.put("msg",msg);
        String logName = RabbitMqEnum.QueueName.HXQLFJQUEUE.getCode();
        AuditEvent event = new AuditEvent(logName,logName,logData);
        zipkinAuditEventRepository.newSpanTag(event,logName);
        consumer(msg,channel,message);
    }
    @Override
    public void processMsg(String message) {
        if(StringUtils.isNotBlank(message)){
            Map<String,Object> messageMap = JSONObject.parseObject(message,Map.class);
            String zsfj = MapUtils.getString(messageMap,"zsfj");
            String xmid = MapUtils.getString(messageMap,"xmid");
            if(StringUtils.isNotBlank(zsfj) && StringUtils.isNotBlank(xmid)){
                BdcQl bdcQl = bdcQllxService.queryQllxDO(xmid);
                if(StringUtils.isNotBlank(initBeanFactory.getFjWz()) || StringUtils.isBlank(bdcQl.getFj())){
                    bdcQl.setFj(zsfj);
                }else{
                    bdcQl.setFj(bdcQl.getFj()+"\n"+zsfj);
                }
                entityMapper.updateByPrimaryKeySelective(bdcQl);
            }
        }
    }

    @Override
    public void saveErrorMsg(Message message) {

    }
}
