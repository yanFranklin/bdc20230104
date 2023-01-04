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
 * @description SDKAPI 结婚信息
 */
public class SdkApiJhxxApp extends SdkApiAbstractApp {

    public SdkApiJhxxApp() {
        this.apiClient = new ApiClient();
        this.apiClient.init();
        this.appId = "1bb43260044f4353b6170e42f0ca20d8";
        this.appSecret = "B8BA1E11F97C37601F365B53E935A244";
        this.host = "59.203.41.45";
        this.httpPort = 4989;
        this.httpsPort = 443;
        this.stage = "RELEASE";
        this.equipmentNo = "XXX";
        this.signStrategyUrl = "/getSignStrategy";
        this.tokenUrl = "/getTokenUrl";
        this.publicKey = "305C300D06092A864886F70D0101010500034B0030480241009F5B9F1587E896441D86CD50EBA67C69AAD99195296A3C1081B80A710B74AEEF0A3593BFFC8172B2BA67E9057FEA3B072B6D0F0FC56B75CDDCB95CC137966C5F0203010001";
        this.icloudlockEnabled = false;//关闭云锁验证
    }

    @Override
    public ApiRequest getRequest() {
        ApiRequest apiRequest = new ApiRequest(HttpConstant.SCHEME_HTTP, Method.POST, "/api/D8766186F326403AA655AE77B3C75C0C", SdkConstant.AUTH_TYPE_ENCRYPT, "1");
        return apiRequest;
    }
}
