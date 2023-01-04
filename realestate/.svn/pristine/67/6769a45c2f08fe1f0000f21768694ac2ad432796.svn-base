package cn.gtmap.realestate.inquiry.core.service.log.handler.lcsc;

import cn.gtmap.realestate.common.core.dto.LogRecordDTO;
import cn.gtmap.realestate.common.matcher.ZipkinAuditEventRepositoryMatcher;
import cn.gtmap.realestate.inquiry.core.service.log.handler.LogRecordHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.stereotype.Component;

/**
 * 流程删除Es日志内容记录处理类
 */
@Component
public class LogRecordLcscEsHandler extends LogRecordHandler {

    @Autowired
    private ZipkinAuditEventRepositoryMatcher zipkinAuditEventRepository;

    @Override
    public void handleLogRecordRequest(LogRecordDTO logRecordDTO) {
        try{
            // 记录ES日志
            String logType = StringUtils.isEmpty(logRecordDTO.getLogAction())? "OTHER" : logRecordDTO.getLogAction();
            zipkinAuditEventRepository.add(new AuditEvent(logRecordDTO.getUserName(), logType, logRecordDTO.getParamMap()));
        }catch(Exception e){

        } finally {
            // 获取下一个日志记录处理者
            if (getNext() != null) {
                getNext().handleLogRecordRequest(logRecordDTO);
            }
        }
    }
}
