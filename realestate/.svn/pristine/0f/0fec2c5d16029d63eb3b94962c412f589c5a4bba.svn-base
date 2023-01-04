package cn.gtmap.realestate.inquiry.core.service.log.handler;

import cn.gtmap.realestate.common.core.dto.LogRecordDTO;

/**
 * 日志查询链条抽线类
 */
public abstract class LogRecordHandler {

    private LogRecordHandler next;

    public LogRecordHandler getNext() {
        return next;
    }

    public void setNext(LogRecordHandler next) {
        this.next = next;
    }

    public abstract void handleLogRecordRequest(LogRecordDTO logRecordDTO);
}
