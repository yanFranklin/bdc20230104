package cn.gtmap.realestate.common.core.dto.exchange.shucheng.dzzz;

import cn.gtmap.realestate.common.core.dto.exchange.SdkApiAbstractApp;
import com.alibaba.fastjson.JSONObject;
import com.iflytek.fsp.shield.java.sdk.constant.HttpConstant;
import com.iflytek.fsp.shield.java.sdk.constant.SdkConstant;
import com.iflytek.fsp.shield.java.sdk.enums.Method;
import com.iflytek.fsp.shield.java.sdk.http.ApiClient;
import com.iflytek.fsp.shield.java.sdk.model.ApiRequest;
import com.iflytek.fsp.shield.java.sdk.model.ApiResponse;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2020-01-16
 * @description 获取电子证照的token
 */
public class ShuchengSdkApiDzzzTokenApp extends SdkApiAbstractApp {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ShuchengSdkApiDzzzTokenApp.class);

    public ShuchengSdkApiDzzzTokenApp() {
        this.apiClient = new ApiClient();
        this.apiClient.init();
        this.appId = "175b76cbc4db4d43ae6b164a656869a7";
        this.appSecret = "7710C1F84A6E56F240963C97782DE9D6";
        this.host = "59.203.234.225";
        this.httpPort = 4989;
        this.httpsPort = 443;
        this.stage = "RELEASE";
        this.equipmentNo = "XXX";
        this.signStrategyUrl = "/getSignStrategy";
        this.tokenUrl = "/getTokenUrl";
        this.publicKey = "305C300D06092A864886F70D0101010500034B0030480241008A64C8D7B46B9D89ADD095CFE67ED476F572952ECB79292E9EC47C3EE53D120C427C0A670859B245065E2B9ED06EEA5ABE06E041BB28327850A35F9EE47798390203010001";
        this.icloudlockEnabled = false;//关闭云锁验证
    }

    @Override
    public ApiRequest getRequest() {
        ApiRequest apiRequest = new ApiRequest(HttpConstant.SCHEME_HTTP, Method.POST, "/api-0.8.20/zzwk-service/criterion/getUnAuthToken", SdkConstant.AUTH_TYPE_ENCRYPT, "1");
        return apiRequest;
    }

    @Override
    public ApiResponse getApiResponse(Map<String, Object> requestParamMap) {
        ApiRequest apiRequest = getRequest();
//        initSignStrategy(apiRequest);
        setParam(apiRequest, requestParamMap);
        try {
            apiRequest.setBody(JSONObject.toJSONString(requestParamMap).getBytes("UTF-8"));
            LOGGER.info("舒城获取电子证照的token请求参数：{}", apiRequest.getBody());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return syncInvoke(apiRequest);
    }
}
