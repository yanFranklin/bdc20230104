package cn.gtmap.realestate.engine.service.impl.dataflow.sql;

import cn.gtmap.realestate.common.core.domain.engine.BdcGzSjlCsDO;

import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @description 数据流SQL解析工厂
 */
public interface SqlExpression<T> {
    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param gzid 规则ID
     * @param sql  SQL内容
     * @param bdcGzSjlCsDO 数据流参数信息实体
     * @param dataResult 数据流执行结果（当前数据流有可能依赖上一个结果）
     * @description
     *       解析SQL内容，替换参数占位内容
     */
    String resolveSqlExpression(String gzid, String sql, BdcGzSjlCsDO bdcGzSjlCsDO, Map<String, T> dataResult);
}
