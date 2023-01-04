package cn.gtmap.realestate.exchange.service.national.access;

import cn.gtmap.realestate.common.core.bo.accessnewlog.*;
import cn.gtmap.realestate.exchange.core.dto.accessLog.AccessLogs;

import java.util.Date;
import java.util.Map;

/**
 * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
 * @version 1.0, 2018/12/18
 * @description 接入日志服务
 */
public interface AccessLogDataService {

    /**
     * @param
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 获取每天的日志接入数据
     */
    AccessLogs getAccessLogs(Map map, Date date, boolean virtualFlag);

    /**
     * @param
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 获取登簿信息 virtualFlag: 部分地区存在非省份的自定义的qxdm，并且需要查看登簿日志量，用于区分是否通过xm表的qxdm统计登簿量和接入量
     */
    RegisterInfo getRegisterInfo(Map map, boolean virtualFlag);

    /**
     * @param
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 获取接入信息 virtualFlag: 部分地区存在非省份的自定义的qxdm，并且需要查看登簿日志量，用于区分是否通过xm表的qxdm统计登簿量和接入量
     */
    AccessInfo getAccessInfo(Map map, boolean virtualFlag);

    /**
     * @param
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">huangjian</a>
     * @description 获取每天的日志接入数据  新版登簿日志
     */
    AccessNewLogs getAccessNewLogs(Map map, Date date, boolean virtualFlag);

    /**
     * @param
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 获取登簿信息 virtualFlag: 部分地区存在非省份的自定义的qxdm，并且需要查看登簿日志量，用于区分是否通过xm表的qxdm统计登簿量和接入量
     */
    RegisterInfoNew getRegisterInfoNew(Map map, boolean virtualFlag);

    /**
     * @param
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 获取接入信息 virtualFlag: 部分地区存在非省份的自定义的qxdm，并且需要查看登簿日志量，用于区分是否通过xm表的qxdm统计登簿量和接入量
     */
    AccessInfoNew getAccessInfoNew(Map map, boolean virtualFlag);
}
