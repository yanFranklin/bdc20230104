package cn.gtmap.realestate.common.core.dto.exchange.bengbu.dzzz;

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
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0  2020-01-16
 * @description 获取电子证照的token
 */
public class BengbuSdkApiDzzzZzqzTokenApp extends SdkApiAbstractApp {

    public BengbuSdkApiDzzzZzqzTokenApp() {
        this.apiClient = new ApiClient();
        this.apiClient.init();
        this.appId = "6e0a784a156643d0aff97993ca996325";
        this.appSecret = "0B856C0F1608559943BDD3B1C295891C";
        this.host = "192.168.11.91";
        this.httpPort = 4989;
        this.httpsPort = 443;
        this.stage = "RELEASE";
        this.publicKey = "305C300D06092A864886F70D0101010500034B003048024100A4D54D127A4A366BDE7E81A7A404D56B46E984E51EC52ECA3102DBA0CA76A025BE2C9EF02DD57FDE7454CB0B47BB9E213B9E02721254E9B38B319FE38115EFD10203010001";
        this.equipmentNo = "XXX";
        this.signStrategyUrl = "/getSignStrategy";
        this.tokenUrl = "/getTokenUrl";
        this.icloudlockEnabled = false;//关闭云锁验证
    }

    @Override
    public ApiRequest getRequest() {
        ApiRequest apiRequest = new ApiRequest(HttpConstant.SCHEME_HTTP, Method.POST, "/criterion/certificateMaintain", SdkConstant.AUTH_TYPE_ENCRYPT, "a2b55e65007d45b3bc1e045581ddfec1");
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
