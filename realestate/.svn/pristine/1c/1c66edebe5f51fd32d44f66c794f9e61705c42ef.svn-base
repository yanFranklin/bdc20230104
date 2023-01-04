package cn.gtmap.realestate.common.core.dto.exchange.hefei.sdkApiApp;

import cn.gtmap.realestate.common.core.dto.exchange.SdkApiAbstractApp;
import com.alibaba.fastjson.JSONObject;
import com.iflytek.fsp.shield.java.sdk.constant.HttpConstant;
import com.iflytek.fsp.shield.java.sdk.constant.SdkConstant;
import com.iflytek.fsp.shield.java.sdk.enums.Method;
import com.iflytek.fsp.shield.java.sdk.http.ApiClient;
import com.iflytek.fsp.shield.java.sdk.model.ApiRequest;
import com.iflytek.fsp.shield.java.sdk.model.ApiResponse;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2020-01-16
 * @description 获取电子证照的token
 */
public class SdkApiDzzzZzqzTokenApp extends SdkApiAbstractApp {

    public SdkApiDzzzZzqzTokenApp() {
        this.apiClient = new ApiClient();
        this.apiClient.init();
        this.appId = "c2375ef2774349fcaa57481fa6d04af7";
        this.appSecret = "BD4458358EF509570999162BB6819189";
        this.host = "59.203.41.45";
        this.httpPort = 4989;
        this.httpsPort = 443;
        this.stage = "RELEASE";
        this.equipmentNo = "XXX";
        this.signStrategyUrl = "/getSignStrategy";
        this.tokenUrl = "/getTokenUrl";
        this.publicKey = "305C300D06092A864886F70D0101010500034B0030480241008AA917DF29D479F0ED69826D191D5C8FD43BC51FFA8CF314B2A325FB14ACC7ABCBB58066CD8895E30CDDB91F4213A548C4C8C46C101FBDF33CF431304FE9DE030203010001";
        this.icloudlockEnabled = false;//关闭云锁验证
    }

    @Override
    public ApiRequest getRequest() {
        ApiRequest apiRequest = new ApiRequest(HttpConstant.SCHEME_HTTP, Method.POST, "/2.0/zzwk-service/criterion/certificateMaintain", SdkConstant.AUTH_TYPE_ENCRYPT, "1");
        return apiRequest;
    }

    @Override
    public ApiResponse getApiResponse(Map<String, Object> requestParamMap) {
        ApiRequest apiRequest = getRequest();
//        initSignStrategy(apiRequest);
//        setParam(apiRequest, requestParamMap);
        try {
            apiRequest.setBody(JSONObject.toJSONString(requestParamMap).getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return syncInvoke(apiRequest);
    }
}
