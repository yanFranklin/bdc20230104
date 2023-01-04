package cn.gtmap.realestate.common.config.mq.sender;

import cn.gtmap.realestate.common.core.dto.CorrelationDataDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0.2019/07/17
 * @description 生产者发送消息，每个生产者需要继承此抽象类
 */
@Component
@AutoConfigureAfter(name = {"cn.gtmap.realestate.common.config.mq.Config.MQConfig"})
public abstract class MQSender implements RabbitTemplate.ConfirmCallback {
    private static final Logger logger = LoggerFactory.getLogger(MQSender.class);

    @Resource(name = "commonRabbitTemplate")
    RabbitTemplate rabbitTemplate;

    /*** @param routeKey 队列和交换机绑定的Key
     * @param exchange Exchange名称
     * @param obj 发送的数据
     * @return
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 发送到 指定交换机、指定routekey的指定queue
     */
    public void send(String exchange, String routeKey, Object obj) {
        send(exchange,routeKey,UUID.randomUUID().toString(),obj);
    }
    public void send(String exchange, String routeKey,String messageid, Object obj) {
        rabbitTemplate.setConfirmCallback(this);
        CorrelationData correlationData = new CorrelationData(messageid);
        logger.debug("消息id:{}", correlationData.getId());
        this.rabbitTemplate.convertAndSend(exchange, routeKey, obj, correlationData);
    }

    /**
     * 如果发送失败，业务系统需要记录日志
     *
     * @param correlationData
     * @param error
     */
    public abstract void saveErrorLog(CorrelationDataDTO correlationData, String error);

    /**
     * 消息回调
     *
     * @param correlationData
     * @param success
     * @param msg
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean success, String msg) {
        if (success) {
            logger.debug("消息发送确认成功");
        } else {
            //生产者需要实现此接口，保存发送错误日志
            saveErrorLog((CorrelationDataDTO) correlationData, msg);
            logger.error("消息发送确认失败:{}", msg);
        }
    }
}
