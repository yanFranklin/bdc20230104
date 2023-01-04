package cn.gtmap.realestate.engine.service.impl.dataflow;

import cn.gtmap.realestate.common.core.domain.engine.BdcGzSjlCsDO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzSjlDTO;
import cn.gtmap.realestate.common.core.ex.EntityException;
import cn.gtmap.realestate.common.util.RestRpcUtils;
import cn.gtmap.realestate.engine.core.enums.BdcGzSjllyEnum;
import cn.gtmap.realestate.engine.core.enums.HttpMethodEnum;
import cn.gtmap.realestate.engine.service.BdcGzSjlService;
import cn.gtmap.realestate.engine.service.impl.dataflow.rpc.RpcExpressionFactory;
import cn.gtmap.realestate.engine.util.DataUtil;
import cn.gtmap.realestate.engine.util.TokenUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/3/5
 * @description 不动产规则子规则 服务数据流 处理逻辑
 */
@Service
public class BdcGzSjlRpcResolver implements BdcGzSjlService {
    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcGzSjlRpcResolver.class);

    /**
     * 用户权限
     */
    @Autowired
    private TokenUtil tokenUtil;

    /**
     * RPC请求
     */
    @Autowired
    private RestRpcUtils restRpcUtils;

    /**
     * 服务内容处理工厂
     */
    @Autowired
    private RpcExpressionFactory rpcExpressionFactory;

    /**
     * 注册实例
     */
    @PostConstruct
    public void register() {
        BdcGzSjlResolverFactory.registerResolver(BdcGzSjllyEnum.FW.getCode(), this);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzSjlDTO  数据流
     * @param sjlResultMap 数据流执行结果集
     * @return {Map} 数据流执行结果
     * @description 获取子规则服务类型数据流执行结果
     */
    @Override
    public <T> T getSjlExecuteResult(BdcGzSjlDTO bdcGzSjlDTO, Map<String, T> sjlResultMap) throws Exception {
        if(null == bdcGzSjlDTO || StringUtils.isBlank(bdcGzSjlDTO.getSjnr())){
            throw new EntityException("子规则数据流执行服务异常：数据流对象或者服务内容为空");
        }

        // 获取请求地址
        String httpUrl = this.getHttpUrl(bdcGzSjlDTO, sjlResultMap);

        // 执行请求
        if(StringUtils.equals(HttpMethodEnum.POST.getCode(), bdcGzSjlDTO.getFwff())) {
            return restRpcUtils.postRpcRequest(bdcGzSjlDTO.getQqyy(), httpUrl, DataUtil.getPostedObj(bdcGzSjlDTO, sjlResultMap));
        }
        return restRpcUtils.getRpcRequest(bdcGzSjlDTO.getQqyy(), httpUrl);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzSjlDTO  数据流
     * @param sjlResultMap 数据流执行结果集
     * @return {String} URL地址
     * @description 获取服务执行的URL地址
     *
     *   如果有参数需要有序遍历，依次替换服务地址中占位变量
     *   例如 /realestate-init/rest/v1.0/bdcdyh/#{1}?name=#{2}
     */
    private <T> String getHttpUrl(BdcGzSjlDTO bdcGzSjlDTO, Map<String, T> sjlResultMap) throws URISyntaxException {
        if(CollectionUtils.isEmpty(bdcGzSjlDTO.getBdcGzSjlCsDOList())){
            return bdcGzSjlDTO.getSjnr();
        }

        String url = bdcGzSjlDTO.getSjnr();
        for(BdcGzSjlCsDO bdcGzSjlCsDO : bdcGzSjlDTO.getBdcGzSjlCsDOList()){
            url = rpcExpressionFactory.resolveRpcExpression(bdcGzSjlDTO.getGzid(), url, bdcGzSjlCsDO, sjlResultMap);
        }

        // 设置Token，避免权限问题
        url = new URIBuilder(url).addParameter("access_token", tokenUtil.getAccessToken()).build().toString();
        String requestUrl = "http://" + bdcGzSjlDTO.getQqyy().trim() + url.trim();
        LOGGER.debug("规则子系统数据流服务请求地址：{}", requestUrl);
        return requestUrl;
    }
}
