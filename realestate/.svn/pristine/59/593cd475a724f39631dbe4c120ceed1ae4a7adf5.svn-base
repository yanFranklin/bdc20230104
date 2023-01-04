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
public class SdkApiDzzzTokenApp extends SdkApiAbstractApp {

    public SdkApiDzzzTokenApp() {
        this.apiClient = new ApiClient();
        this.apiClient.init();
        this.appId = "4c83bcc3df9f4f18a6aaeab2010301e2";
        this.appSecret = "818CF99A3877237EC57671AEDC3D1777";
        this.host = "59.203.5.92";
        this.httpPort = 8800;
        this.httpsPort = 443;
        this.stage = "RELEASE";
        this.publicKey = "305C300D06092A864886F70D0101010500034B003048024100D31D22EE699377D53A298A9304442F06626CAFB87B681BD98B4AC109ED1BE0ACEC9300267E24DF132E4A07B888D87862DEC91335A87CDF5AE6949E701E6CF01D0203010001";
        this.equipmentNo = "XXX";
        this.signStrategyUrl = "/getSignStrategy";
        this.tokenUrl = "/getTokenUrl";
        this.publicKey = "305C300D06092A864886F70D0101010500034B003048024100D31D22EE699377D53A298A9304442F06626CAFB87B681BD98B4AC109ED1BE0ACEC9300267E24DF132E4A07B888D87862DEC91335A87CDF5AE6949E701E6CF01D0203010001";
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
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return syncInvoke(apiRequest);
    }
}
