package cn.gtmap.realestate.engine.service.impl.dataflow.sql;

import cn.gtmap.realestate.common.core.domain.engine.BdcGzSjlCsDO;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @description 数据流SQL解析工厂
 */
@Component
public class SqlExpressionFactory implements SqlExpression{
    /**
     * 缓存不同类型参数解析类
     */
    private static Map<String, SqlExpression> sqlExpResolverMap = new ConcurrentHashMap<>(4);

    /**
     * 注册SQL解析类
     * @param type 参数类型
     * @param sqlExpression 解析类
     */
    public static void registerSqlExpReolver(String type, SqlExpression sqlExpression){
        sqlExpResolverMap.put(type, sqlExpression);
    }

    /**
     * 获取SQL解析处理类
     * @param type 参数类型
     * @return {SqlExpression} SQL解析处理类
     */
    public static SqlExpression getSqlExpResoler(String type){
        return sqlExpResolverMap.get(type);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param gzid         规则ID
     * @param sql          SQL内容
     * @param bdcGzSjlCsDO 数据流参数信息实体
     * @param dataResult   数据流执行结果（当前数据流有可能依赖上一个结果）
     * @description 解析SQL内容，替换参数占位内容
     */
    @Override
    public String resolveSqlExpression(String gzid, String sql, BdcGzSjlCsDO bdcGzSjlCsDO, Map dataResult) {
        SqlExpression sqlExpResolver = SqlExpressionFactory.getSqlExpResoler(bdcGzSjlCsDO.getSjlcszl());
        return sqlExpResolver.resolveSqlExpression(gzid, sql, bdcGzSjlCsDO, dataResult);
    }
}
