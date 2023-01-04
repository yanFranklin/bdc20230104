package cn.gtmap.realestate.check.service;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.check.CheckGzjcLogDO;

import java.util.List;
import java.util.Map;

/**
 * 规则检查定义接口
 *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 *@version 1.0
 *@description
 */
public interface RuleService {

    /**
     * 规则执行方法
     * @author lst
     * @param bdcXm 不动产项目对象
     * @param ruleName 规则名
     * @exception 抛出检测报错
     * @return 根据规则名执行相应规则后返回的记录信息
     */
   List<Map<String,String>> excuteRule(BdcXmDO bdcXm, String ruleName) throws Exception;

    /**
     * 根据返回的限制信息列表返回出对应的规则日志类
     * @author lst
     * @param resultMapLst 限制信息列表
     * @param bdcGzjcLog 检查日志实体类包含相关基础信息
     * @return 返回详细日志列表
     */
    List<CheckGzjcLogDO> mapToBdcGzjcLog(List<Map<String, String>> resultMapLst, CheckGzjcLogDO bdcGzjcLog);

}
