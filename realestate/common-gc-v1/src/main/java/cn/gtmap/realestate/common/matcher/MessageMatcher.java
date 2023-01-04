package cn.gtmap.realestate.common.matcher;

import cn.gtmap.gtc.feign.common.util.ObjectMapperUtils;
import cn.gtmap.gtc.msg.domain.dto.AnonymousContentDto;
import cn.gtmap.gtc.msg.domain.dto.ProduceMsgDto;
import cn.gtmap.gtc.msg.domain.enums.ConsumerType;
import cn.gtmap.gtc.msg.domain.enums.MsgReadFlag;
import cn.gtmap.gtc.msg.domain.enums.OperationType;
import cn.gtmap.gtc.msg.domain.enums.ProducerType;
import cn.gtmap.gtc.msg.rabbitmq.produce.MessageProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2022/03/28
 * @description  处理短信发送 V1.x版本适配
 */
@Component
@Import({MessageProducer.class})
public class MessageMatcher {
    private static final Logger logger = LoggerFactory.getLogger(MessageMatcher.class);

    @Autowired
    private MessageProducer messageProducer;

    /**
     * 发送短信
     * @param clientId 应用clientid
     * @param data 待发送数据
     * @param phone 电话号
     * @param msgType 类型
     */
    public void sendMsg(String clientId, Map<String, String> data, String phone, String msgType) {
        ProduceMsgDto msgDto = new ProduceMsgDto();
        try {
            msgDto.setClientId(clientId).setMsgCode(System.currentTimeMillis() + "")
                    .setMsgType(msgType).setMsgTypeName("短信发送")
                    .setProducer(clientId).setProducerType(ProducerType.SYSTEM.getName())
                    .setRead(MsgReadFlag.UNREAD.getValue()).setOptions(OperationType.SMS.getName());
            msgDto.setConsumer(phone).setConsumerType(ConsumerType.ANONYMOUS.getName());
            AnonymousContentDto contentDto = new AnonymousContentDto();
            contentDto.setMobile(phone).setContent(ObjectMapperUtils.toJson(data));
            msgDto.setMsgContent(ObjectMapperUtils.toJson(contentDto));
            messageProducer.send(msgDto);
        } catch (Exception e) {
            logger.error("短信发送失败", e);
        }
    }
}
