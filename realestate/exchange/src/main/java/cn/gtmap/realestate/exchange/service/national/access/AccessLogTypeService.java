package cn.gtmap.realestate.exchange.service.national.access;

import cn.gtmap.realestate.common.core.dto.exchange.access.MsgNoticeDTO;
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

    /**
     * @param xml           xml文件
     * @param bdcJrDbrzjlDO 日志接入表实体类
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 市级登簿日志上报
     * @date : 2023/1/6 9:23
     */
    void cityAccessLogWeb(String xml, BdcJrDbrzjlDO bdcJrDbrzjlDO);

    /**
     * 上报相关消息通知接口-站内通知或短信通知，可通用
     *
     * @param msgNoticeDTO@return
     * @Date 2022/6/22
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    void sendMsgByMsgType(MsgNoticeDTO msgNoticeDTO);

}
