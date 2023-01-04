package cn.gtmap.realestate.exchange.service.rabbitmq;

import cn.gtmap.realestate.common.matcher.ZipkinAuditEventRepositoryMatcher;
import cn.gtmap.realestate.common.config.mq.consumer.MQConsumer;
import cn.gtmap.realestate.common.core.domain.exchange.BdcDwJkDO;
import cn.gtmap.realestate.exchange.core.bo.log.AuditEventBO;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcDwJkMapper;
import cn.gtmap.realestate.exchange.service.impl.inf.log.LogInDbServiceImpl;
import cn.gtmap.realestate.exchange.service.impl.inf.log.LogInEsServiceImpl;
import cn.gtmap.realestate.exchange.service.inf.log.LogService;
import cn.gtmap.realestate.exchange.util.LogUtil;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href=""mailto:zedeqiang@gtmap.cn>zedq</a>
 * @version 1.0, 2020/11/9.
 * @description
 */
@Service
public class InsertAuditLogMQConsumer extends MQConsumer {

    private static final Logger logger = LoggerFactory.getLogger(InsertAuditLogMQConsumer.class);

    @Autowired
    private ZipkinAuditEventRepositoryMatcher zipkinAuditEventRepository;

    @Autowired
    private LogUtil logUtil;

    @Autowired
    private BdcDwJkMapper bdcDwJkMapper;

    @Resource(name = "logInDb")
    private LogInDbServiceImpl logInDbService;

    @Resource(name = "logInEs")
    private LogInEsServiceImpl logInEsService;

    /**
     * @param msg
     * @param channel
     * @param message
     * @return void
     * @author zedq 2020/11/9
     * @description 插入请求日志消息处理
     */
    @RabbitListener(containerFactory = "rabbitListenerContainerFactory", queues = "bdc-exchange-insert-audit-log-queue")
    @RabbitHandler
    public void insertAuditLog(AuditEventBO msg, Channel channel, Message message) throws IOException {
//        logger.debug("开始消费bdc-exchange-insert-audit-log-queue消息的具体信息：msg：{}， channel:{}, message:{}", JSON.toJSONString(msg), JSON.toJSONString(channel), JSON.toJSONString(message));
//        logger.info("bdc-exchange-insert-audit-log-queue消息的具体信息：{}", JSON.toJSONString(message));
//        Map<String, Object> logData = new HashMap<>();
//        logData.put("msg", msg);
//        String logName = RabbitMqEnum.QueueName.BDCDJINSERTAUDITLOGQUEUE.getCode();
//        AuditEvent event = new AuditEvent(logName, logName, logData);
//        zipkinAuditEventRepository.newSpanTag(event, logName);
        try {
            this.processMsg(msg);
            //手动确认消息消费成功
            if (channel != null) {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            }
        } catch (Exception e) {
            LOGGER.error("消息处理失败:{},异常信息:{}", msg, e);
            if (channel != null) {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            }
            saveErrorMsg(message);
        }

    }

    public void processMsg(AuditEventBO message) {
        try {

            if (message != null) {
//            logger.info("bdc-exchange-insert-audit-log-queue消息处理的消息体信息：{}", JSON.toJSONString(message));
            logger.debug("bdc-exchange-insert-audit-log-queue消息处理的消息体信息");
//            AuditEventBO auditEventBO = JSONObject.parseObject(message, AuditEventBO.class);
                if (StringUtils.isNotBlank(message.getRzlx()) && Constants.EXCHANGE_ES_DACX_RZLX.equals(message.getRzlx())) {
                    logger.debug("dachecklog消息处理");
                    //设置允许存储数据量大的出入参
                    message.setBigDataFlag(true);
                    logInEsService.saveAuditLog(message);
                } else if (StringUtils.isNotBlank(message.getRzlx()) && Constants.EXCHANGE_ES_SHIJI_GX_RZLX.equals(message.getRzlx())) {
                    logger.debug("shiji共享消息处理");
                    if(logUtil.getShijigxRzTypes().contains("logInEs")){
                        logInEsService.saveAuditLog(message);
                    }
                    if(logUtil.getShijigxRzTypes().contains("logInDb")){
                        logInDbService.saveAuditLog(message);
                    }
                } else {
                    // 读取当前请求地址 用于读取配置
                    List<LogService> logServiceList = new ArrayList<>(4);
                    if (message.isSimpleInterface()) {
                        BdcDwJkDO queryInterfaceInfoParam = new BdcDwJkDO();
                        queryInterfaceInfoParam.setJkmc(message.getLogBO().getLogEventName());
                        List<BdcDwJkDO> bdcDwJkDOS = bdcDwJkMapper.searchApiInfo(queryInterfaceInfoParam);
                        if (bdcDwJkDOS != null && bdcDwJkDOS.size() > 0) {
                            if (bdcDwJkDOS.get(0).getRzjlfs() == 0) {
                                logServiceList.add(logInDbService);
                            } else {
                                logServiceList.add(logInEsService);
                            }
                        }
                    } else {
                        logServiceList = logUtil.getServiceList(message.getLogBO());
                    }
                    if (CollectionUtils.isNotEmpty(logServiceList)) {
                        for (LogService logService : logServiceList) {
                            logService.saveAuditLog(message);
                        }
                    }
                }

            }
        } catch (Exception e) {
            LOGGER.info("日志落表异常！");
        }
    }

    /**
     * 消费者处理消息
     *
     * @param msg
     */
    @Override
    public void processMsg(String msg) {

    }

    @Override
    public void saveErrorMsg(Message message) {
        logger.error("bdcdj-insert-audit-log-queue消息处理失败：{}", JSON.toJSONString(message));
    }
}
