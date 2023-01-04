package cn.gtmap.realestate.exchange.service.national.access;

import cn.gtmap.realestate.exchange.core.domain.BdcJrDbrzjlDO;

public interface AccessLogNationalHandlerService {
    /**
     * @param xml           xml文件
     * @param bdcJrDbrzjlDO 日志接入表实体类
     * @return
     * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
     * @description 部级日志上报
     **/
    void nationalAccessLogWeb(String xml, BdcJrDbrzjlDO bdcJrDbrzjlDO);

}
