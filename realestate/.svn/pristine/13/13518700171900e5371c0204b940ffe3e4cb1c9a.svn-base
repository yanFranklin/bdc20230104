package cn.gtmap.realestate.exchange.service.national.access;

import cn.gtmap.realestate.exchange.core.domain.exchange.BdcJrCzrzDO;
import cn.gtmap.realestate.exchange.core.dto.AccessLogDTO;
import cn.gtmap.realestate.exchange.core.national.BdcAccessLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
 * @version 1.0  2018-12-17
 * @description 日志上报
 */
public interface AccessLogService {

    /**
     * @param qxtype wsx , hefei
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.String>>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 读取配置文件中的 外市县列表
     */
    List<Map<String, String>> queryAccessWsxList(String qxtype);

    /**
     * @param accessDate 上报时间
     * @param qxdm       上报地区代码
     * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
     * @description 上报登簿日志
     */
    void accessLog(Date accessDate, String qxdm);

    /**
     * @param pageable
     * @param map
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 分页查询上报日志
     */
    Page<Object> listAccessLogByPages(Pageable pageable, Map map);

    /**
     * @param pageable
     * @param map
     * @return org.springframework.data.domain.Page<java.lang.Object>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 分页查询外市县
     */
    Page<AccessLogDTO> listWsxAccessLogByPage(Pageable pageable, Map map);

    /**
     * @param xmidList
     * @param logType
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 获取响应报文
     */
    String getAccessResponse(List<String> xmidList, String logType);

    /**
     * @param ywh
     * @param logType
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 读取响应报文XML
     */
    String queryXybw(String ywh, String logType);

    /**
     * @param ywh
     * @param logType
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 读取业务报文XML
     */
    String queryJrbw(String ywh, String logType);

    /**
     * @param ywh
     * @param logType
     * @return java.lang.String
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 读取业务模型
     */
    BdcAccessLog queryBdcAccessLog(String ywh, String logType);


    /**
     * @param
     * @return java.util.List<cn.gtmap.realestate.exchange.core.national.BdcAccessLog>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 定时任务获取省级接入日志 并更新处理标志
     */
    List<BdcAccessLog> queryAndUpdateSjjr();

    /**
     * @param
     * @return Map
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 获取登簿日志明细信息
     */
    Map<String, Object> dbrzMx(String id);

    /**
     * 校验上报响应数据并发送短信
     *
     * @param accessType
     */
//    void checkAccessResponseAndSendMsg(String accessType);

    /**
     * @param xmidList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新未销账报文信息
     * @date : 2022/7/25 15:43
     */
    void updateXzzt(List<String> xmidList, String xzzt);

    /**
     * @param xmid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询接入操作日志
     * @date : 2022/7/11 16:47
     */
    List<BdcJrCzrzDO> queryJrCzrz(String xmid);

}
