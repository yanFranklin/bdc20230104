package cn.gtmap.realestate.engine.service.impl.dataflow.rpc;

import cn.gtmap.realestate.common.core.domain.engine.BdcGzSjlCsDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.engine.core.enums.BdcGzSjlCsEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @description 数据流服务解析类：常量
 */
@Service
public class RpcExpCLResolver implements RpcExpression {
    /**
     * 注册常量处理类
     */
    @PostConstruct
    public void register(){
        RpcExpressionFactory.registerRpcExpReolver(BdcGzSjlCsEnum.CL.getCode(), this);
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
        if(null == bdcGzSjlCsDO.getSjlcsz() || StringUtils.isBlank(String.valueOf(bdcGzSjlCsDO.getSjlcsz()))){
            throw new AppException(ErrorCode.RULE_PARAM_NULL_ERROR, "当前规则数据流（" + bdcGzSjlCsDO.getSjlid() + "）参数值为空!");
        }

        return url.replaceFirst("\\#\\{" + bdcGzSjlCsDO.getSjlcsxh()  + "\\}", String.valueOf(bdcGzSjlCsDO.getSjlcsz()));
    }
}
