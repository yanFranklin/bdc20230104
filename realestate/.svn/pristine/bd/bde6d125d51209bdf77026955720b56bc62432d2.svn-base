package cn.gtmap.realestate.common.matcher;

import cn.gtmap.gtc.start.config.audit.ZipkinAuditEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2022/03/24
 * @description  大云日志存储接口类V2.x版本适配
 */
@Component
public class ZipkinAuditEventRepositoryMatcher {
    @Autowired
    private ZipkinAuditEventRepository zipkinAuditEventRepository;

    public void add(AuditEvent auditEvent) {
        zipkinAuditEventRepository.add(auditEvent);
    }

    public void newSpanTag(AuditEvent event, String logName) {
        zipkinAuditEventRepository.newSpanTag(event, logName);
    }
}
