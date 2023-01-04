package cn.gtmap.realestate.engine.service.impl.dataflow.rpc;

import cn.gtmap.realestate.common.core.domain.engine.BdcGzSjlCsDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.engine.core.enums.BdcGzSjlCsEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @description 数据流服务解析类：入参
 */
@Service
public class RpcExpRCResolver implements RpcExpression {
    /**
     * 参数处理策略：没有传参，是否异常处理； true ：抛异常 、false : 采用默认值处理
     */
    @Value("${rpc.isNeedException:true}")
    private Boolean isNeedException;

    /**
     * 注册入参处理类
     */
    @PostConstruct
    public void register(){
        RpcExpressionFactory.registerRpcExpReolver(BdcGzSjlCsEnum.RC.getCode(), this);
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
        if(this.isParamNullOrEmpty(bdcGzSjlCsDO) && isNeedException) {
            throw new AppException(ErrorCode.RULE_PARAM_NULL_ERROR, "当前规则数据流（" + bdcGzSjlCsDO.getSjlid() + "）参数值为空!");
        }

        String value = CommonConstantUtils.BDC_GZ_RPC_RC_MRZ;
        if(!this.isParamNullOrEmpty(bdcGzSjlCsDO)) {
            value = String.valueOf(bdcGzSjlCsDO.getSjlcsz());
        }

        return url.replaceFirst("\\#\\{" + bdcGzSjlCsDO.getSjlcsxh()  + "\\}", value);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzSjlCsDO 数据流参数信息实体
     * @description 判断参数是否为空
     */
    private boolean isParamNullOrEmpty(BdcGzSjlCsDO bdcGzSjlCsDO){
        if(null == bdcGzSjlCsDO.getSjlcsz() || StringUtils.isBlank(String.valueOf(bdcGzSjlCsDO.getSjlcsz()))) {
            return true;
        }
        return false;
    }
}
