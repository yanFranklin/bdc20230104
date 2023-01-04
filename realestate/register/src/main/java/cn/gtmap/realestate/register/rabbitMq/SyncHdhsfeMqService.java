package cn.gtmap.realestate.register.rabbitMq;

import cn.gtmap.realestate.common.config.mq.sender.MQSender;
import cn.gtmap.realestate.common.core.dto.CorrelationDataDTO;
import cn.gtmap.realestate.common.matcher.ZipkinAuditEventRepositoryMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.stereotype.Service;


/**
 * 核定户室份额MQ生成者服务
 * @author <a href="mailto:yaoyi@gtmap.cn>yaoyi</a>"
 * @version 1.0, 2022/1/27
 */
@Service
public class SyncHdhsfeMqService extends MQSender {

    @Autowired
    ZipkinAuditEventRepositoryMatcher zipkinAuditEventRepository;
    /**
     * 如果发送失败，业务系统需要记录日志
     *
     * @param correlationData
     * @param error
     */
    @Override
    public void saveErrorLog(CorrelationDataDTO correlationData, String error) {
        AuditEvent auditEvent = new AuditEvent(correlationData.getId(), "消息发送失败", error);
        zipkinAuditEventRepository.add(auditEvent);
    }

}
