package cn.gtmap.realestate.exchange.core.mapper.server.accessLog;


import cn.gtmap.realestate.common.core.domain.exchange.ZttGyQlrDO;
import cn.gtmap.realestate.exchange.core.domain.BdcDbrzjlXqDO;
import cn.gtmap.realestate.exchange.core.domain.BdcJrCzrzDO;
import cn.gtmap.realestate.exchange.core.domain.BdcJrDbrzjlDO;
import cn.gtmap.realestate.exchange.core.domain.JgWxzbwxxDO;
import cn.gtmap.realestate.exchange.core.dto.AccessLogDTO;
import cn.gtmap.realestate.exchange.core.national.BdcAccessLog;
import cn.gtmap.realestate.exchange.core.qo.BdcDbJrXqQO;
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
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param paramMap
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.exchange.ZttGyQlrDO>
     * @description 根据YWH 查询共有权利人
     */
    List<ZttGyQlrDO> listZttGyQlrByYwh(Map paramMap);


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param paramMap
     * @return java.util.List<cn.gtmap.realestate.exchange.core.national.BdcAccessLog>
     * @description 查询 没有响应编码 的日志列表
     */
    List<BdcAccessLog> listAccessLogWithNoXybm(Map paramMap);

    /**
     * 查询省份上报信息
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @param paramMap
     * @return
     */
    List<AccessLogDTO> listAccessLogByPageOrderForBatchAccess(Map paramMap);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param paramMap
     * @return void
     * @description 更新 响应编码为 dealing
     */
    void updateDealingXybm(Map paramMap);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param paramMap
     * @return void
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
     * @param currentDate
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询当天的数据
     * @date : 2022/6/23 15:03
     */
    List<BdcAccessLog> listBdcJrSjjl(@Param("currentDate") Date currentDate);

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


    /*
     * 根据ywlsh查询接入数据
     * */
    List<BdcAccessLog> listBdcJrsj(@Param("ywlsh") String ywlsh);

    /**
     * @param map
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新登簿未接入表数据
     * @date : 2022/6/30 16:03
     */
    void updateWjrClzt(Map map);

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
     * 查询登簿数据量
     * */
    int queryDbsjl(BdcDbJrXqQO bdcDbJrXqQO);

    /*
     * 查询接入数据量
     * */
    int queryJrsjl(BdcDbJrXqQO bdcDbJrXqQO);

    /*
     * 查询登簿日志明细数据量
     * */
    int queryDbrzxqSjl(BdcDbJrXqQO bdcDbJrXqQO);


    /**
     * @param null
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询接入操作日志
     * @date : 2022/7/11 16:54
     */
    List<BdcJrCzrzDO> listBdcJrczrz(@Param("xmid") String xmid);

    /**
     * @param null
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询接入操作日志 时间倒叙
     * @date : 2022/7/11 16:54
     */
    List<BdcJrCzrzDO> listBdcJrczrzDesc(@Param("xmid") String xmid);

    /*
     * 查询登簿日志记录
     * */
    List<BdcJrDbrzjlDO> listDbrzjl(BdcDbrzQO bdcDbrzQO);

    /**
     * 批量查询报文
     *
     * @param List Register
     * @return
     * @Date 2022/11/10
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    List<BdcAccessLog> getProvinceAccessYwbwidByXmidList(Map map);
}
