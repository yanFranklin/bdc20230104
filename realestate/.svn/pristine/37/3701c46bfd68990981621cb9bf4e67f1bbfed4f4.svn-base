package cn.gtmap.realestate.check.service;

import cn.gtmap.realestate.common.core.domain.check.CheckGzjcLogDO;

import java.util.List;
import java.util.Map;

/**
 * @program: realestate
 * @description: 数据库全表检索sql执行
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-05-07 15:47
 **/
public interface RuleSqlService {

    /**
     * 规则执行方法
     *
     * @param bdcXm    不动产项目对象
     * @param ruleName 规则名
     * @return 根据规则名执行相应规则后返回的记录信息
     * @throws 抛出检测报错
     * @author lst
     */
    List<Map<String, String>> excuteRule(Map map, String ruleName) throws Exception;


    /**
     * 根据返回的限制信息列表返回出对应的规则日志类
     *
     * @param resultMapLst 限制信息列表
     * @param bdcGzjcLog   检查日志实体类包含相关基础信息
     * @return 返回详细日志列表
     * @author lst
     */
    List<CheckGzjcLogDO> mapToBdcGzjcLog(List<Map<String, String>> resultMapLst, CheckGzjcLogDO bdcGzjcLog);
}
