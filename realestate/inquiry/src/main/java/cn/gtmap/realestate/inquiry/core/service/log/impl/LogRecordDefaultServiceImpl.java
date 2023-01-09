package cn.gtmap.realestate.inquiry.core.service.log.impl;

import cn.gtmap.realestate.common.core.dto.LogRecordDTO;
import cn.gtmap.realestate.common.util.LogConstans;
import cn.gtmap.realestate.inquiry.core.service.log.LogCustomRecordService;
import cn.gtmap.realestate.inquiry.core.service.log.handler.LogRecordDefaultDbHandler;
import cn.gtmap.realestate.inquiry.core.service.log.handler.LogRecordDefaultEsHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0  2022-11-20
 * @description 默认日志记录实现类
 * <p> </p>
 */
@Service(value = LogConstans.LOG_TYPE_DEFAULT)
public class LogRecordDefaultServiceImpl implements LogCustomRecordService {

    /**
     * Es 日志记录链条
     */
    @Autowired
    LogRecordDefaultEsHandler logRecordDefaultEsHandler;

    /**
     * 数据库日志记录链条
     */
    @Autowired
    LogRecordDefaultDbHandler logRecordDefaultDbHandler;


    @Override
    public void recordLog(LogRecordDTO logRecordDTO) {
        // 设置日志记录链条 1、记录默认的 ES 日志 2、记录默认的 DB 日志
        logRecordDefaultEsHandler.setNext(logRecordDefaultDbHandler);
        // 执行日志记录方法
        logRecordDefaultEsHandler.handleLogRecordRequest(logRecordDTO);
    }
}
