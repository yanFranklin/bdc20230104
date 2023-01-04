package cn.gtmap.realestate.exchange.service.national.access;

import cn.gtmap.realestate.exchange.core.domain.BdcJrDbrzjlDO;

public interface AccessLogTypeService {

    /**
     * @param xml   报文
     * @param xmlId 报文id
     * @return
     * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
     * @description ftp上传到服务器并生成本地xml文件
     */
    void generateXmlAndFtp(String xml, String xmlId);


    /**
     * @param xml           xml文件
     * @param bdcJrDbrzjlDO 日志接入表实体类
     * @return
     * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
     * @description 部级日志上报
     **/
    void nationalAccessLogWeb(String xml, BdcJrDbrzjlDO bdcJrDbrzjlDO);


    /**
     * @param xml           xml文件
     * @param bdcJrDbrzjlDO 日志接入表实体类
     * @return
     * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
     * @description 省级日志上报
     **/
    void provinceAccessLogWeb(String xml, BdcJrDbrzjlDO bdcJrDbrzjlDO);


}
