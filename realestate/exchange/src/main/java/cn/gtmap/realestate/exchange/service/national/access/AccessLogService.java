package cn.gtmap.realestate.exchange.service.national.access;

import cn.gtmap.realestate.common.core.dto.exchange.access.SbxzDTO;
import cn.gtmap.realestate.exchange.core.domain.BdcJrCzrzDO;
import cn.gtmap.realestate.exchange.core.dto.AccessLogDTO;
import cn.gtmap.realestate.exchange.core.dto.DbJrDbMxDTO;
import cn.gtmap.realestate.exchange.core.national.BdcAccessLog;
import cn.gtmap.realestate.exchange.core.qo.BdcDbJrXqQO;
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
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param qxtype wsx , hefei
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.String>>
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
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 登簿日志明细预览
     * @date : 2022/10/13 9:19
     */
    Map<String, Object> dbrzmxyl(Date accessDate, String qxdm);

    /**
     * @param pageable
     * @param map
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 分页查询上报日志
     */
    Page<Object> listAccessLogByPages(Pageable pageable, Map map);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param pageable
     * @param map
     * @return org.springframework.data.domain.Page<java.lang.Object>
     * @description 分页查询外市县
     */
    Page<AccessLogDTO> listWsxAccessLogByPage(Pageable pageable, Map map);

    /**
     * @param xmidList
     * @param logType
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 获取响应报文
     */
    String getAccessResponse(List<String> xmidList,String logType);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param ywh
     * @param logType
     * @return java.lang.String
     * @description 读取响应报文XML
     */
    String queryXybw(String ywh,String logType);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param ywh
     * @param logType
     * @return java.lang.String
     * @description 读取业务报文XML
     */
    String queryJrbw(String ywh,String logType);

    /**
     * @param ywh
     * @param logType
     * @return java.lang.String
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 读取业务模型
     */
    BdcAccessLog queryBdcAccessLog(String ywh, String logType);


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param
     * @return java.util.List<cn.gtmap.realestate.exchange.core.national.BdcAccessLog>
     * @description 定时任务获取省级接入日志 并更新处理标志
     */
    List<BdcAccessLog> queryAndUpdateSjjr();
	
	/**
	 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
	 * @param
	 * @return Map
	 * @description 获取登簿日志明细信息
     */
    Map<String, Object> dbrzMx(String id);

    /**
     * 校验上报响应数据并发送短信
     *
     * @param accessType
     */
    void checkAccessResponseAndSendMsg(String accessType);

    /*
     * 查询登簿数据量
     * */
    int queryDbSjl(BdcDbJrXqQO bdcDbJrXqQO);

    /*
     * 查询接入数据量
     * */
    int queryJrSjl(BdcDbJrXqQO bdcDbJrXqQO);

    /*
     * 查询登簿日志明细数据量
     * */
    int queryDbrzXqSjl(BdcDbJrXqQO bdcDbJrXqQO);

    /**
     * @param xmid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询接入操作日志
     * @date : 2022/7/11 16:47
     */
    List<BdcJrCzrzDO> queryJrCzrz(String xmid);


    /**
     * @param bdcDbJrXqQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2022/7/28 10:00
     */
    List<DbJrDbMxDTO> listDbJrDbMxDTO(BdcDbJrXqQO bdcDbJrXqQO);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 例外上报
     * @date : 2022/10/6 11:05
     */
    Object lwsb(SbxzDTO sbxzDTO);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取登簿日志上报的实现类
     * @date : 2022/12/14 11:23
     */
    AccessLogDataService getAccessLogService();
}
