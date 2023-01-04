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
 * @description SDKAPI 离婚信息
 */
public class SdkApiLhxxApp extends SdkApiAbstractApp {

    public SdkApiLhxxApp() {
        this.apiClient = new ApiClient();
        this.apiClient.init();
        this.appId = "5ac13827484e43c69789daf8888af0ba";
        this.appSecret = "2A8ADA060751CF7128326A1065CC6C56";
        this.host = "59.203.41.45";
        this.httpPort = 4989;
        this.httpsPort = 443;
        this.stage = "RELEASE";
        this.equipmentNo = "XXX";
        this.signStrategyUrl = "/getSignStrategy";
        this.tokenUrl = "/getTokenUrl";
        this.publicKey = "305C300D06092A864886F70D0101010500034B003048024100A7C8BE23E86BB9F3C651CFD084B71D5CCCD8085BA87FFAAB7C5982FFD5843A6A3A0726E0073129FF56D8DF0AD0E9C360F4653AD09A6C1F3F1B6493C6A5C0A44F0203010001";
        this.icloudlockEnabled = false;//关闭云锁验证
    }

    @Override
    public ApiRequest getRequest() {
        ApiRequest apiRequest = new ApiRequest(HttpConstant.SCHEME_HTTP, Method.POST, "/api/4DEAD88ADDCF4C199AB7FF2E918F82B1", SdkConstant.AUTH_TYPE_ENCRYPT, "1");
        return apiRequest;
    }
}
