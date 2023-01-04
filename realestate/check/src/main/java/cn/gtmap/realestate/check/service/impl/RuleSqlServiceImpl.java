package cn.gtmap.realestate.check.service.impl;


import cn.gtmap.realestate.check.service.RuleService;
import cn.gtmap.realestate.check.utils.StringRepUtil;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.check.CheckGzjcLogDO;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
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
 * SQL类型的规则统一的实现类（后期规则实现统一继承该方法并重写excuteRule方法）
 *
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0
 * @description
 */
@Service("ruleSqlService")
public class RuleSqlServiceImpl implements RuleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RuleSqlServiceImpl.class);

    /**
     * 动态SQL执行对象
     * @author lst
     * @description 动态SQL执行对象
     */
    @Autowired
    private Repo repository;

    /**
     * SQL类型规则执行方法 根据规则名动态查找SQL并执行
     * @author lst
     * @param bdcXm 不动产项目对象
     * @param ruleName 规则名
     * @exception 抛出检测报错
     * @return 根据规则名执行相应规则后返回的记录信息
     */
    @Override
    public List<Map<String, String>> excuteRule(BdcXmDO bdcXm, String ruleName) throws Exception {
        List<Map<String,String>> resultMapLst = null;
        if (null != repository) {
            Date startTime = new Date();
            LOGGER.warn("sqlid={}开始执行", ruleName);
            Map paramMap = JSON.parseObject(JSON.toJSONString(bdcXm), Map.class);
            try {
                resultMapLst = repository.selectList("cn.gtmap.realestate.check.core.mapper.SsMapper." + ruleName, paramMap);
            } catch (MyBatisSystemException e) {
                resultMapLst = repository.selectList("cn.gtmap.realestate.check.core.mapper.RuleSqlMapper." + ruleName, paramMap);
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
            for (Map<String,String> resultMap : resultMapLst) {
                if (null != resultMap) {
                    newBdcGzjcLog = new CheckGzjcLogDO();
                    //属性转换
                    BeanUtils.copyProperties(bdcGzjcLog,newBdcGzjcLog);
                    String tsxx = bdcGzjcLog.getJcxx();
                    if (StringUtils.isNotBlank(tsxx)) {
                        tsxx = StringRepUtil.replaceTsxxFromMap(tsxx,resultMap);
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
                    bdcGzjcLogLst.add(newBdcGzjcLog);
                }
            }
        }
        return bdcGzjcLogLst;
    }
}
