package cn.gtmap.realestate.exchange.core.mapper.server.accessLog;


import cn.gtmap.realestate.exchange.core.domain.BdcJrDbrzjlDO;
import cn.gtmap.realestate.exchange.core.domain.exchange.BdcDbrzjlXqDO;
import cn.gtmap.realestate.exchange.core.domain.exchange.BdcJrCzrzDO;
import cn.gtmap.realestate.exchange.core.domain.exchange.JgWxzbwxxDO;
import cn.gtmap.realestate.exchange.core.domain.exchange.ZttGyQlrDO;
import cn.gtmap.realestate.exchange.core.dto.AccessLogDTO;
import cn.gtmap.realestate.exchange.core.national.BdcAccessLog;
import cn.gtmap.realestate.exchange.core.qo.BdcDbrzQO;
import cn.gtmap.realestate.exchange.core.qo.BdcJrrzQO;
import cn.gtmap.realestate.exchange.core.qo.JgWxzBwxxQO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public interface AccessLogMapper {

    /**
     * @return
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 通过Xmid获取省级报文id
     */
    BdcAccessLog getProvinceAccessYwbwidByXmid(String xmid);


    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询接入表数据且业务报文id 不是当前xmid 的
     * @date : 2022/8/19 16:34
     */
    BdcAccessLog getAccessLogNotXmid(String xmid);

    /**
     * @return
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 通过Xmid获取国家报文id
     */
    BdcAccessLog getNationalAccessYwbwidByXmid(String xmid);


    /**
     * @param paramMap
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.exchange.ZttGyQlrDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据YWH 查询共有权利人
     */
    List<ZttGyQlrDO> listZttGyQlrByYwh(Map paramMap);


    /**
     * @param paramMap
     * @return java.util.List<cn.gtmap.realestate.exchange.core.national.BdcAccessLog>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 查询 没有响应编码 的日志列表
     */
    List<BdcAccessLog> listAccessLogWithNoXybm(Map paramMap);

    /**
     * 查询省份上报信息
     *
     * @param paramMap
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
    List<AccessLogDTO> listAccessLogByPageOrderForBatchAccess(Map paramMap);

    /**
     * @param paramMap
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 更新 响应编码为 dealing
     */
    void updateDealingXybm(Map paramMap);

    /**
     * @param paramMap
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 回滚更新的 响应编码 dealing
     */
    void rollbackDealingXybm(Map paramMap);

    /**
     * @param currentDate
     * @return 上报登薄日志记录
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    List<BdcJrDbrzjlDO> listBdcJrDbrzjl(Date currentDate);

    /**
     * @param currentDate
     * @return 上报登薄日志记录
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description
     */
    List<BdcJrDbrzjlDO> listBdcJrDbrzjlList(@Param("qxdm") String qxdm, @Param("currentDate") Date currentDate);

    /**
     * @param id
     * @return 上报登薄日志记录
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description
     */
    BdcJrDbrzjlDO queryBdcJrDbrzjl(String id);

    /**
     * @param jgWxzBwxxQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询未销账报文信息表
     * @date : 2022/6/21 17:43
     */
    List<JgWxzbwxxDO> queryWxzBwxx(JgWxzBwxxQO jgWxzBwxxQO);

    /**
     * @param xmidList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新销账状态
     * @date : 2022/7/25 15:44
     */
    void updateXzzt(@Param("xmidList") List<String> xmidList, @Param("xzzt") String xzzt);

    /**
     * @param bdcDbrzQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询登簿日志表信息
     * @date : 2022/7/4 17:24
     */
    List<BdcDbrzjlXqDO> listBdcDbrzjlXq(BdcDbrzQO bdcDbrzQO);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询接入日志数据
     * @date : 2022/7/5 16:36
     */
    List<BdcAccessLog> listBdcJrrz(BdcJrrzQO bdcJrrzQO);

    /*
     * 查询登簿日志记录
     * */
    List<BdcJrDbrzjlDO> listDbrzjl(BdcDbrzQO bdcDbrzQO);

    /**
     * @param null
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询接入操作日志
     * @date : 2022/7/11 16:54
     */
    List<BdcJrCzrzDO> listBdcJrczrz(@Param("xmid") String xmid);

    /**
     * @param currentDate
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询当天的数据
     * @date : 2022/6/23 15:03
     */
    List<BdcAccessLog> listBdcJrSjjl(@Param("currentDate") Date currentDate);
}
