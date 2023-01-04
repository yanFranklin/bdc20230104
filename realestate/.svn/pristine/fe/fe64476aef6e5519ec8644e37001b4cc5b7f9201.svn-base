package cn.gtmap.realestate.common.core.dto.exchange.bengbu.dzzz;

import com.iflytek.fsp.shield.java.sdk.constant.SdkConstant;
import com.iflytek.fsp.shield.java.sdk.enums.ParamPosition;
import com.iflytek.fsp.shield.java.sdk.http.BaseApp;
import com.iflytek.fsp.shield.java.sdk.model.ApiRequest;
import com.iflytek.fsp.shield.java.sdk.model.ApiResponse;
import com.iflytek.fsp.shield.java.sdk.model.ApiSignStrategy;
import com.iflytek.fsp.shield.java.sdk.model.ResultInfo;
import org.apache.commons.collections.MapUtils;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-10-10
 * @description
 */
public abstract class BengbuSdkApiAbstractApp extends BaseApp {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(BengbuSdkApiAbstractApp.class);


    /**
     * @param requestParamMap
     * @return com.iflytek.fsp.shield.java.sdk.model.ApiResponse
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 执行请求
     */
    public ApiResponse getApiResponse(Map<String, Object> requestParamMap) {
        ApiRequest apiRequest = getRequest();
        initSignStrategy(apiRequest);
        setParam(apiRequest, requestParamMap);
        LOGGER.info("开始执行请求！电子证照请求参数：{}", apiRequest.getBody());

        return syncInvoke(apiRequest);
    }

    /**
     * @param apiRequest
     * @param requestParamMap
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 放参数
     */
    public void setParam(ApiRequest apiRequest, Map<String, Object> requestParamMap) {
        if (MapUtils.isNotEmpty(requestParamMap)) {
            for (String key : requestParamMap.keySet()) {
                apiRequest.addParam(key, requestParamMap.get(key),
                        ParamPosition.QUERY, false);
            }
        }
    }

    public void initSignStrategy(ApiRequest apiRequest) {
        //获取服务安全策略信息
        ApiSignStrategy signStrategy = super.getSignStrategy(apiRequest.getPath());
        //判断是否需要token校验
        if (null != signStrategy && "token".equals(signStrategy.getSignType())) {
            //从本地缓存获取token信息,如果本地缓存存在token信息，验证本地缓存token的有效次数，
            //1.如果验证通过，token次数-1，回写到本地缓存；
            //2.如果验证不通过，从新获取token信息，并写到本地缓存。

            //从token服务获取token信息
            ResultInfo resultInfo = super.getTokenInfo(signStrategy);
            if (null != resultInfo && SdkConstant.SUCCESS.equals(resultInfo.getCode())) {
                apiRequest.setTokenValue(resultInfo.getData().getTokenValue());
                //apiRequest.getHeaders().appendOne(SdkConstant.AUTH_EQUIPMENTNO,equipmentNo);
            } else {
                System.err.println("获取token信息失败");
            }
        }
    }

    public abstract ApiRequest getRequest();
}
