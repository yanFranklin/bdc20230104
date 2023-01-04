package cn.gtmap.realestate.check.utils;

import cn.gtmap.realestate.check.core.mapper.BdcXmMapper;
import cn.gtmap.realestate.check.service.RuleSqlService;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.check.CheckGzjcLogDO;
import cn.gtmap.realestate.common.core.domain.check.CheckGzxxDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.spring.Container;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @param null
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @description 全库扫描检查所有的项目信息, 当前方法里只有sql 相关的规则信息
 * @date : 2022/5/7 13:43
 */
@Component
@Scope("prototype")
public class CheckAllXmThread implements Runnable {

    /**
     * log日志对象
     *
     * @author hqz
     * @description log日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckAllXmThread.class);

    private List<BdcXmDO> bdcXmLst;

    private List<CheckGzxxDO> gzxxLst;

    private List<CheckGzjcLogDO> logList;

    private String name;

    private Map<String, Object> queryMap;

    /**
     * entity对象Mapper
     *
     * @author hqz
     * @description entity对象Mapper
     */
    private EntityMapper entityMapper;

    /**
     * bdcXm对象Mapper
     *
     * @author hqz
     * @description bdcXm对象Mapper
     */
    private BdcXmMapper bdcXmMapper;

    /**
     * sql类型规则服务的beanName
     *
     * @author lst
     * @description sql类型规则服务的beanName
     */
    private static final String RULE_SQL_BEAN = "ruleAllSqlService";

    @Override
    public void run() {
        excute();
    }

    private void excute() {
        CheckGzjcLogDO bdcGzjcLog;
        RuleSqlService sqlRuleService = (RuleSqlService) Container.getBean(RULE_SQL_BEAN);
        //全库检查项目，此时不需要循环执行，查询的项目信息按照查询条件过滤，且只负责sql部分，其余部分仍然使用循环方式
        //日志记录只记录失败或者异常的数据，正常数据不再记录
        List<CheckGzjcLogDO> bdcGzjcLogLst = new ArrayList<>();
        List<Map<String, String>> ruleMaps = null;
        for (CheckGzxxDO bdcGzxx : gzxxLst) {
            if (null != bdcGzxx) {
                LOGGER.warn("当前检查线程{}、规则：{},开始时间：{},检查的项目数量{}", name, bdcGzxx.getGzcode(), new Date(), CollectionUtils.size(bdcXmLst));
                Integer gzType = bdcGzxx.getGzlx();
                String gzName = bdcGzxx.getGzcode();
                if (StringUtils.isNotBlank(gzName) && gzType != null) {
                    bdcGzjcLog = new CheckGzjcLogDO();
                    bdcGzjcLog.setGzid(bdcGzxx.getGzid());
                    bdcGzjcLog.setJczt(Constants.GZJC_JCZT_ZC);
                    try {
                        queryMap.put("bhlc", bdcGzxx.getBhlc());
                        queryMap.put("qclc", bdcGzxx.getQclc());
                        // 执行规则--加入去除流程和包含流程
                        ruleMaps = sqlRuleService.excuteRule(queryMap, gzName);
                        if (null != ruleMaps && !ruleMaps.isEmpty()) {
                            bdcGzjcLog.setJcdj(bdcGzxx.getGzdj());
                            bdcGzjcLog.setJcxx(bdcGzxx.getTsxx());
                            bdcGzjcLog.setJjzt(Constants.GZJC_WJJZT);
                            bdcGzjcLogLst.addAll(sqlRuleService.mapToBdcGzjcLog(ruleMaps, bdcGzjcLog));
                            LOGGER.warn("当前检查线程{}、规则{}、检查问题数量{}，当前总问题数量{}", name, bdcGzxx, CollectionUtils.size(ruleMaps), CollectionUtils.size(bdcGzjcLogLst));
                        }
                    } catch (Exception e) {
                        // 如果检查异常 直接基本信息存储到日志表
                        bdcGzjcLog.setJczt(Constants.GZJC_JCZT_YC);
                        bdcGzjcLogLst.add(bdcGzjcLog);
                        LOGGER.error("执行规则异常，规则code:{}", bdcGzxx.getGzcode());
                        LOGGER.error("异常信息:", e);
                    }
                }
            }
        }
        if (CollectionUtils.isNotEmpty(bdcGzjcLogLst)) {
            LOGGER.warn("当前检查线程{}日志入库开始{}数据量{}", name, new Date(), CollectionUtils.size(bdcGzjcLogLst));
            GzjcLogUtil.addGzjcLog(bdcGzjcLogLst, entityMapper, bdcXmMapper, logList);
            LOGGER.warn("当前检查线程{}日志入库结束{}入库数据量{}", name, new Date(), CollectionUtils.size(logList));
        }
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setBdcXmLst(List<BdcXmDO> bdcXmLst) {
        this.bdcXmLst = bdcXmLst;
    }

    public void setGzxxLst(List<CheckGzxxDO> gzxxLst) {
        this.gzxxLst = gzxxLst;
    }


    public void setEntityMapper(EntityMapper entityMapper) {
        this.entityMapper = entityMapper;
    }

    public void setBdcXmMapper(BdcXmMapper bdcXmMapper) {
        this.bdcXmMapper = bdcXmMapper;
    }

    public void setLogList(List<CheckGzjcLogDO> logList) {
        this.logList = logList;
    }

    public void setQueryMap(Map<String, Object> queryMap) {
        this.queryMap = queryMap;
    }
}
