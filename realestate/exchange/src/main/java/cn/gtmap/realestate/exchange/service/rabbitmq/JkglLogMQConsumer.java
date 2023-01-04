package cn.gtmap.realestate.exchange.service.rabbitmq;

import cn.gtmap.realestate.common.config.mq.consumer.MQConsumer;
import cn.gtmap.realestate.exchange.core.bo.log.AuditEventBO;
import cn.gtmap.realestate.exchange.core.bo.log.ZdyjkLogBO;
import cn.gtmap.realestate.exchange.service.impl.inf.log.LogInDbServiceImpl;
import cn.gtmap.realestate.exchange.service.impl.inf.log.LogInEsServiceImpl;
import cn.gtmap.realestate.exchange.util.LogUtil;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author <a href=""mailto:yaoyi@gtmap.cn>yaoyi</a>
 * @version 1.0, 2022/08/23.
 * @description 接口管理日志内容消费者
 * <p>用于接口管理生成的接口日志内容，存放于MQ中的zdygjlog队列，消费者用于消费队列中的日志内容并记录</p>
 */
@Service
public class JkglLogMQConsumer {

    private static final Logger logger = LoggerFactory.getLogger(JkglLogMQConsumer.class);

    /**
     * 共享部门标志
     */
    private static final String JKGL_GXBMBZ = "JKGL";

    @Autowired
    private LogUtil logUtil;

    @Resource(name = "logInDb")
    private LogInDbServiceImpl logInDbService;

    @Resource(name = "logInEs")
    private LogInEsServiceImpl logInEsService;

    /**
     * @param msg  消息内容
     * @param channel 通道
     * @param message 消息对象
     * @return void
     * @author zedq 2020/11/9
     * @description 插入请求日志消息处理
     */
    @RabbitListener(queues = "zdygjlog")
    @RabbitHandler
    public void insertJkglLog(String msg, Channel channel, Message message) throws IOException {
        logger.info("开始消费 zdygjlog 队列中的具体信息：{}", msg);
        try {
            this.processMsg(msg);
            //手动确认消息消费成功
            if (channel != null) {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            }
        } catch (Exception e) {
            logger.error("消息处理失败:{},异常信息:{}", msg, e);
            if (channel != null) {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            }
            saveErrorMsg(message);
        }

    }

    /**
     * 处理消息，进行业务逻辑处理
     */
    public void processMsg(String msg) {
        if(StringUtils.isBlank(msg)){
            return;
        }
        try {
            String decodeMsg = URLDecoder.decode(msg, "UTF-8");
            ZdyjkLogBO zdyjkLogBO = JSON.parseObject(decodeMsg, ZdyjkLogBO.class);
            logger.info("zdygjlog 消息处理的消息体信息");
            AuditEventBO message = this.convertToAuditEvent(zdyjkLogBO);
            if(logUtil.getDefaultService().equals("logInEs")){
                logInEsService.saveAuditLog(message);
            }
            if(logUtil.getDefaultService().equals("logInDb")){
                logInDbService.saveAuditLog(message);
            }
        } catch (Exception e) {
            logger.info("日志落表异常，{}", e.getMessage());
        }
    }

    /**
     * 将接口管理日志对象转换为登记系统所需的日志对象
     */
    private AuditEventBO convertToAuditEvent(ZdyjkLogBO zdyjkLogBO){
        AuditEventBO auditEventBO  = new AuditEventBO();
        // 设置接口请求名称
        auditEventBO.setViewTypeName(Optional.ofNullable(zdyjkLogBO.getJkmc()).orElse(""));
        // 设置接口请求参数
        Map<String, String> requestParam = new HashMap<>(2);
        requestParam.put("head", Optional.ofNullable(zdyjkLogBO.getHead()).orElse(""));
        requestParam.put("body", Optional.ofNullable(zdyjkLogBO.getBody()).orElse(""));
        auditEventBO.setRequest(JSON.toJSONString(requestParam));
        // 设置接口返回值
        auditEventBO.setResponse(Optional.ofNullable(zdyjkLogBO.getResult()).orElse(""));
        auditEventBO.setRequestr(Optional.ofNullable(zdyjkLogBO.getUserid()).orElse(""));
        auditEventBO.setResponser("BDCDJ");
        auditEventBO.setUsername(Optional.ofNullable(zdyjkLogBO.getUserid()).orElse(""));
        // 日志类型
        auditEventBO.setRzlx(Constants.EXCHANGE_ES_RZLX);
        // 部门共享标志
        auditEventBO.setDsfFlag(JKGL_GXBMBZ);
        // 接口地址
        auditEventBO.setDsfdz(Optional.ofNullable(zdyjkLogBO.getCrlj()).orElse(""));
        return auditEventBO;
    }

    /**
     * 错误保存错误信息
     */
    public void saveErrorMsg(Message message) {

    }

}
