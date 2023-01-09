package cn.gtmap.realestate.inquiry.rabbitMq.consumer;

import cn.gtmap.realestate.common.config.mq.consumer.MQConsumer;
import cn.gtmap.realestate.common.core.dto.LogRecordDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.util.LogConstans;
import cn.gtmap.realestate.inquiry.core.service.log.LogCustomRecordService;
import cn.gtmap.realestate.inquiry.core.service.log.LogRecordStrategyFactory;
import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0  2022-06-02
 * @description 日志记录MQ消息队列信息接口处理者
 */
@Service
public class LogRecordMsgConsumer extends MQConsumer {

    @Autowired
    LogRecordStrategyFactory logRecordStrategyFactory;

    /**
     * 接收队列中的消息
     */
    @RabbitListener(containerFactory = "rabbitListenerContainerFactory", queues = "log-record-queue")
    @RabbitHandler
    public void receiveLogRecordMsg(String msg, Channel channel, Message message) throws IOException {
        consumer(msg, channel, message);
    }

    /**
     * 处理消息
     */
    @Override
    public void processMsg(String msg) {
        if(StringUtils.isNotBlank(msg)){
            try{
                LogRecordDTO logRecordDTO = JSON.parseObject(msg, LogRecordDTO.class);
                if(StringUtils.isBlank(logRecordDTO.getLogType())){
                    logRecordDTO.setLogType(LogConstans.LOG_TYPE_DEFAULT);
                }
                LogCustomRecordService logCustomRecordService = logRecordStrategyFactory.getCustomLogRecord(logRecordDTO.getLogType());
                if(null == logCustomRecordService){
                    throw new AppException(ErrorCode.MISSING_ARG, "未获到日志类型：" + logRecordDTO.getLogType() + "的自定义实现类。");
                }
                logCustomRecordService.recordLog(logRecordDTO);
            }catch (Exception e){
                LOGGER.error("消费日志记录MQ消息异常, message:{}", msg, e);
            }
        }
    }

    @Override
    public void saveErrorMsg(Message message) {}
}
