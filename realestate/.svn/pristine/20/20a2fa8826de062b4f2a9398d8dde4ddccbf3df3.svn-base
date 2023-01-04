package cn.gtmap.realestate.check.service.impl;

import cn.gtmap.realestate.check.service.RuleSqlService;
import cn.gtmap.realestate.check.utils.StringRepUtil;
import cn.gtmap.realestate.common.core.domain.check.CheckGzjcLogDO;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.MyBatisSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @program: realestate
 * @description: 数据库全表检索执行sql
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-05-07 15:48
 **/
@Service("ruleAllSqlService")
public class RuleAllSqlServiceImpl implements RuleSqlService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RuleAllSqlServiceImpl.class);

    @Autowired
    private Repo repository;

    /**
     * 规则执行方法
     *
     * @param map
     * @param ruleName 规则名
     * @return 根据规则名执行相应规则后返回的记录信息
     * @throws 抛出检测报错
     * @author lst
     */
    @Override
    public List<Map<String, String>> excuteRule(Map map, String ruleName) throws Exception {
        List<Map<String, String>> resultMapLst = null;
        if (null != repository) {
            Date startTime = new Date();
            LOGGER.warn("sqlid={}开始执行查询入参{}", ruleName, map);
            try {
                resultMapLst = repository.selectList("cn.gtmap.realestate.check.core.mapper.SsMapper." + ruleName, map);
            } catch (MyBatisSystemException e) {
                resultMapLst = repository.selectList("cn.gtmap.realestate.check.core.mapper.RuleSqlMapper." + ruleName, map);
            } catch (Exception e) {
                throw new Exception(ruleName, e);
            }
            LOGGER.warn("sqlid={}执行结束，耗时{}ms", ruleName, System.currentTimeMillis() - startTime.getTime());
        }
        return resultMapLst;
    }

    /**
     * 根据返回的限制信息列表返回出对应的规则日志类
     *
     * @param resultMapLst 限制信息列表
     * @param bdcGzjcLog   检查日志实体类包含相关基础信息
     * @return 返回详细日志列表
     * @author lst
     */
    @Override
    public List<CheckGzjcLogDO> mapToBdcGzjcLog(List<Map<String, String>> resultMapLst, CheckGzjcLogDO bdcGzjcLog) {
        List<CheckGzjcLogDO> bdcGzjcLogLst = null;
        if (CollectionUtils.isNotEmpty(resultMapLst)) {
            CheckGzjcLogDO newBdcGzjcLog;
            bdcGzjcLogLst = new ArrayList<CheckGzjcLogDO>();
            for (Map<String, String> resultMap : resultMapLst) {
                if (null != resultMap) {
                    newBdcGzjcLog = new CheckGzjcLogDO();
                    //属性转换
                    BeanUtils.copyProperties(bdcGzjcLog, newBdcGzjcLog);
                    String tsxx = bdcGzjcLog.getJcxx();
                    if (StringUtils.isNotBlank(tsxx)) {
                        tsxx = StringRepUtil.replaceTsxxFromMap(tsxx, resultMap);
                    }
                    newBdcGzjcLog.setJcxx(tsxx);
                    if (resultMap.containsKey("XZWH")) {
                        String xzwh = resultMap.get("XZWH");
                        if (StringUtils.isNotBlank(xzwh)) {
                            newBdcGzjcLog.setXzwh(xzwh);
                        }
                    }
                    if (resultMap.containsKey("XZWHXMID")) {
                        String xzwhxmid = resultMap.get("XZWHXMID");
                        if (StringUtils.isNotBlank(xzwhxmid)) {
                            newBdcGzjcLog.setXzwhxmid(xzwhxmid);
                        }
                    }
                    if (resultMap.containsKey("BDCDYH")) {
                        String bdcdyh = resultMap.get("BDCDYH");
                        if (StringUtils.isNotBlank(bdcdyh)) {
                            newBdcGzjcLog.setBdcdyh(bdcdyh);
                        }
                    }
                    if (resultMap.containsKey("XMID")) {
                        newBdcGzjcLog.setXmid(MapUtils.getString(resultMap, "XMID", ""));
                    }
                    if (resultMap.containsKey("SLBH")) {
                        newBdcGzjcLog.setSlbh(MapUtils.getString(resultMap, "SLBH", ""));
                    }
                    bdcGzjcLogLst.add(newBdcGzjcLog);
                }
            }
        }
        return bdcGzjcLogLst;
    }
}
