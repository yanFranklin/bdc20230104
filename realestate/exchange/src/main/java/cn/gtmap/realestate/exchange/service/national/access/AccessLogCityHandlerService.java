package cn.gtmap.realestate.exchange.service.national.access;

import cn.gtmap.realestate.exchange.core.domain.BdcJrDbrzjlDO;

/**
 * @program: bdcdj3.0
 * @description: 登簿日志市级上报
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2023-01-06 09:30
 **/
public interface AccessLogCityHandlerService {

    /**
     * @param xml           xml文件
     * @param bdcJrDbrzjlDO 日志接入表实体类
     * @return
     * @author <a href="mailto:zhuruijie@gtmap.cn">gaolining</a>
     * @description 市级登簿日志上报
     **/
    String cityAccessLogWeb(String xml, BdcJrDbrzjlDO bdcJrDbrzjlDO);
}
