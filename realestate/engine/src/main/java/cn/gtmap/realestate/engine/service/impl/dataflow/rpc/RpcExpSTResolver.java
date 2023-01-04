package cn.gtmap.realestate.engine.service.impl.dataflow.rpc;

import cn.gtmap.realestate.common.core.domain.engine.BdcGzSjlCsDO;
import cn.gtmap.realestate.engine.core.enums.BdcGzSjlCsEnum;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @description 数据流服务解析类：实体
 */
@Service
public class RpcExpSTResolver implements RpcExpression {
    /**
     * 注册实体处理类
     */
    @PostConstruct
    public void register(){
        RpcExpressionFactory.registerRpcExpReolver(BdcGzSjlCsEnum.ST.getCode(), this);
    }


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param gzid         规则ID
     * @param url          服务URL
     * @param bdcGzSjlCsDO 数据流参数信息实体
     * @param dataResult   数据流执行结果（当前数据流有可能依赖上一个结果）
     * @description 解析服务请求内容，替换参数占位内容
     */
    @Override
    public String resolveRpcExpression(String gzid, String url, BdcGzSjlCsDO bdcGzSjlCsDO, Map dataResult) {
        // 服务URL地址中占位符内容不支持实体参数
        // 但是不能直接异常，因为实体的参数是作为请求参数实体
        return url;
    }
}
