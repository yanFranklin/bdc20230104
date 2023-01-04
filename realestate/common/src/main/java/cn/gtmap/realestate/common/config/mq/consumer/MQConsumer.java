package cn.gtmap.realestate.common.config.mq.consumer;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;

import java.io.IOException;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0.2019/07/17
 * @description 消费者接收信息，每个消费者需要继承此抽象类(手动应答）
 * 1.消费者接收信息
 * 2 消费者处理接收得信息
 */
public abstract class MQConsumer {

    protected static final Logger LOGGER = LoggerFactory.getLogger(MQConsumer.class);

    /**
     * 消费者处理消息
     *
     * @param msg
     */
    public abstract void processMsg(String msg);

    /**
     * 处理异常记录消息
     *
     * @param message
     */
    public abstract void saveErrorMsg(Message message);

    /**
     * 消费者处理消息，并确认消息
     *
     * @param msg
     * @param channel
     * @param message
     */
    public void consumer(String msg, Channel channel, Message message) throws IOException {
        try {
            LOGGER.debug("接收到消息:{}", msg);
            processMsg(msg);
            if(channel != null){
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            }

        } catch (Exception e) {
            LOGGER.error("消息处理失败:{},异常信息:{}", msg, e);
            if(channel != null){
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            }
            saveErrorMsg(message);
        }
    }
}
