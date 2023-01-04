package cn.gtmap.realestate.engine.service.impl.dataflow.rpc;

import cn.gtmap.realestate.common.core.domain.engine.BdcGzSjlCsDO;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @description 数据流服务解析工厂
 */
@Component
public class RpcExpressionFactory implements RpcExpression{
    /**
     * 缓存不同类型参数解析类
     */
    private static Map<String, RpcExpression> rpcExpResolverMap = new ConcurrentHashMap<>(4);

    /**
     * 注册服务解析类
     * @param type 参数类型
     * @param rpcExpression 解析类
     */
    public static void registerRpcExpReolver(String type, RpcExpression rpcExpression){
        rpcExpResolverMap.put(type, rpcExpression);
    }

    /**
     * 获取服务解析处理类
     * @param type 参数类型
     * @return {RpcExpression} 服务解析处理类
     */
    public static RpcExpression getRpcExpResoler(String type){
        return rpcExpResolverMap.get(type);
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
    public String resolveRpcExpression(String gzid, String sql, BdcGzSjlCsDO bdcGzSjlCsDO, Map dataResult) {
        RpcExpression rpcExpression = RpcExpressionFactory.getRpcExpResoler(bdcGzSjlCsDO.getSjlcszl());
        return rpcExpression.resolveRpcExpression(gzid, sql, bdcGzSjlCsDO, dataResult);
    }
}
