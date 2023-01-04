package cn.gtmap.realestate.exchange.service.rabbitmq;

import cn.gtmap.realestate.common.config.mq.sender.MQSender;
import cn.gtmap.realestate.common.core.dto.CorrelationDataDTO;
import cn.gtmap.realestate.common.matcher.ZipkinAuditEventRepositoryMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.stereotype.Service;

/**
 * @author <a href=""mailto:zedeqiang@gtmap.cn>zedq</a>
 * @version 1.0, 2020/9/4.
 * @description
 */
@Service
public class InsertAuditLogMQSender extends MQSender {
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
