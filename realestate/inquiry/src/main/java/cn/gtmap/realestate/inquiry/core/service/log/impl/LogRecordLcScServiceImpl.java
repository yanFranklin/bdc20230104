package cn.gtmap.realestate.inquiry.core.service.log.impl;

import cn.gtmap.realestate.common.core.dto.LogRecordDTO;
import cn.gtmap.realestate.common.util.LogConstans;
import cn.gtmap.realestate.inquiry.core.service.log.LogCustomRecordService;
import cn.gtmap.realestate.inquiry.core.service.log.handler.lcsc.LogRecordLcscDbHandler;
import cn.gtmap.realestate.inquiry.core.service.log.handler.lcsc.LogRecordLcscEsHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0  2022-06-07
 * @description 流程删除自定义日志记录实现类
 */
@Service(value = LogConstans.LOG_TYPE_LCSC)
public class LogRecordLcScServiceImpl implements LogCustomRecordService {

    @Autowired
    LogRecordLcscEsHandler logRecordLcscEsHandler;

    @Autowired
    LogRecordLcscDbHandler logRecordLcscDbHandler;

    @Override
    public void recordLog(LogRecordDTO logRecordDTO) {
        // 使用职责链模式，设置链条 1、记录流程删除 ES 日志 2、记录流程删除 DB 日志
        logRecordLcscEsHandler.setNext(logRecordLcscDbHandler);
        // 执行日志记录方法
        logRecordLcscEsHandler.handleLogRecordRequest(logRecordDTO);
    }
}
