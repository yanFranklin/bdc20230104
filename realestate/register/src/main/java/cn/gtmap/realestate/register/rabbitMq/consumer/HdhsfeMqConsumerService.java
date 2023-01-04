package cn.gtmap.realestate.register.rabbitMq.consumer;

import cn.gtmap.realestate.common.matcher.ZipkinAuditEventRepositoryMatcher;
import cn.gtmap.realestate.common.config.mq.enums.RabbitMqEnum;
import cn.gtmap.realestate.common.core.domain.BdcXmFbDO;
import cn.gtmap.realestate.common.core.dto.register.BdcHdhsfeDTO;
import cn.gtmap.realestate.common.core.qo.init.BdcDjxxUpdateQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmfbFeignService;
import com.alibaba.fastjson.JSON;
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
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a></a>
 * @version 1.0  2019-10-23
 * @description 同步业务系统锁定状态场景
 */
@Service
public class HdhsfeMqConsumerService {

    /**
     * 日志组件
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(HdhsfeMqConsumerService.class);

    @Autowired
    BdcXmfbFeignService bdcXmfbFeignService;

    @Autowired
    private ZipkinAuditEventRepositoryMatcher zipkinAuditEventRepository;

    /**
     * 接收队列中的消息
     */
    @RabbitListener(queues = "sync-hdhsfe-queue")
    @RabbitHandler
    public void receiveBdcdysdZt(String msg, Channel channel, Message message) throws IOException {
        Map<String,Object> logData = new HashMap<>();
        logData.put("msg",msg);
        String logName = RabbitMqEnum.QueueName.SYNCHDHSFEQUEUE.getCode();
        AuditEvent event = new AuditEvent(logName,logName,logData);
        zipkinAuditEventRepository.newSpanTag(event,logName);

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

    /**
     * 处理消息，进行业务逻辑处理
     */
    public void processMsg(String msg) {
        LOGGER.info("处理核定户室份额队列：{}", msg);
        if(StringUtils.isNotBlank(msg)){
            try{
                BdcHdhsfeDTO bdcHdhsfeDTO = JSONObject.parseObject(msg, BdcHdhsfeDTO.class);
                Double tdsyqfe = this.computeTdsyqfe(bdcHdhsfeDTO);

                // 将土地使用权份额 保存到项目附表中
               this.saveTdsyqfe(bdcHdhsfeDTO.getXmid(), tdsyqfe);
            }catch (Exception e){
                LOGGER.error("消费核定户室份额队列消息失败, message:{}", msg, e);
            }
        }
    }

    /**
     * 错误保存错误信息
     */
    public void saveErrorMsg(Message message) {

    }

    /**
     * 计算土地使用权份额
     */
    private Double computeTdsyqfe(BdcHdhsfeDTO bdcHdhsfeDTO) {
        if(bdcHdhsfeDTO.getTotalJzmj() > 0 && bdcHdhsfeDTO.getJzmj() > 0){
            BigDecimal totalJzmj = new BigDecimal(String.valueOf(bdcHdhsfeDTO.getTotalJzmj()));
            BigDecimal jzmj = new BigDecimal(String.valueOf(bdcHdhsfeDTO.getJzmj()));
            BigDecimal tdsyqfe = jzmj.divide(totalJzmj,6,BigDecimal.ROUND_HALF_UP);
            return tdsyqfe.doubleValue();
        }
        return 0.0;
    }

    /**
     * 将土地使用权份额，保存到项目附表中
     */
    private void saveTdsyqfe(String xmid, Double tdsyqfe) throws Exception {
        BdcDjxxUpdateQO bdcDjxxUpdateQO = new BdcDjxxUpdateQO();
        Map whereMap = new HashMap<>(1);
        whereMap.put("xmids", Arrays.asList(xmid));
        bdcDjxxUpdateQO.setWhereMap(whereMap);
        BdcXmFbDO xmfbxx = new BdcXmFbDO();
        xmfbxx.setTdsyqfe(tdsyqfe);
        bdcDjxxUpdateQO.setJsonStr(JSON.toJSONString(xmfbxx));
        this.bdcXmfbFeignService.updateBatchBdcxmFb(bdcDjxxUpdateQO);
    }


}
