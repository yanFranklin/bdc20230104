package cn.gtmap.realestate.common.config.mq.sender;

import cn.gtmap.realestate.common.core.dto.CorrelationDataDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 日志记录MQ消息队列信息发送者
 */
@Component
public class LogRecordMsgSender extends MQSender {

    private static Logger logger = LoggerFactory.getLogger(LogRecordMsgSender.class);

    @Override
    public void saveErrorLog(CorrelationDataDTO correlationData, String error) {
        logger.error("MQ发送日志记录消息失败：{}", error);
    }
}
