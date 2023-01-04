package cn.gtmap.realestate.common.core.dto.exchange.hefei.sdkApiApp;

import cn.gtmap.realestate.common.core.dto.exchange.SdkApiAbstractApp;
import com.iflytek.fsp.shield.java.sdk.constant.HttpConstant;
import com.iflytek.fsp.shield.java.sdk.constant.SdkConstant;
import com.iflytek.fsp.shield.java.sdk.enums.Method;
import com.iflytek.fsp.shield.java.sdk.http.ApiClient;
import com.iflytek.fsp.shield.java.sdk.model.ApiRequest;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-10-10
 * @description SDKAPI 获取TOKEN
 */
public class SdkApiTokenApp extends SdkApiAbstractApp {

    public SdkApiTokenApp() {
        this.apiClient = new ApiClient();
        this.apiClient.init();
        this.appId = "d80368865ce046e7a5d6bb4ede9dc983";
        this.appSecret = "F7BF8E43B3D0B24AC6EFB9A98DD59430";
        this.host = "200.1.1.168";
        this.httpPort = 8030;
        this.httpsPort = 443;
        this.stage = "RELEASE";
        this.equipmentNo = "XXX";
        this.signStrategyUrl = "/getSignStrategy";
        this.tokenUrl = "/getTokenUrl";
        this.publicKey = "305C300D06092A864886F70D0101010500034B0030480241008D857D6B1182980A87944546666F64D83F70A1D9C8A588EE6BBA336594B7A3B18E74B211D38D6D229C9AC089F1BD365FB902FF2C089B0EEC598576A90A76DABB0203010001";
        this.icloudlockEnabled = false;//关闭云锁验证
    }

    @Override
    public ApiRequest getRequest() {
        ApiRequest apiRequest = new ApiRequest(HttpConstant.SCHEME_HTTP,
                Method.POST, "/api/077BEEA9035F4119900A87DFDDE1C253",
                SdkConstant.AUTH_TYPE_ENCRYPT, "1");
        return apiRequest;
    }

    @Override
    public void initSignStrategy(ApiRequest apiRequest) {
    }
}
