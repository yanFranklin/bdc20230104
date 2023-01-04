package cn.gtmap.realestate.common.core.dto.exchange.xuancheng.hhSdk;

import com.iflytek.fsp.shield.java.sdk.constant.HttpConstant;
import com.iflytek.fsp.shield.java.sdk.constant.SdkConstant;
import com.iflytek.fsp.shield.java.sdk.enums.Method;
import com.iflytek.fsp.shield.java.sdk.enums.ParamPosition;
import com.iflytek.fsp.shield.java.sdk.http.ApiClient;
import com.iflytek.fsp.shield.java.sdk.http.BaseApp;
import com.iflytek.fsp.shield.java.sdk.model.ApiRequest;
import com.iflytek.fsp.shield.java.sdk.model.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HhxxSdk extends BaseApp {

    private static Logger LOGGER = LoggerFactory.getLogger(HhxxSdk.class);

    public HhxxSdk() {
        this.apiClient = new ApiClient();
        this.apiClient.init();
        // 管理平台应用查看处获取并修改
        this.appId = "e65e4af7346442ddb28ab536db2ddf34";
        // 管理平台应用查看处获取并修改
        this.appSecret = "503CDD5C2EE68CFCAA8592F1ED9B2C6B";

        // 核心层ip
        this.host = "10.171.16.163";

        // 核心层暴露的http端口
        this.httpPort = 8800;

        // 核心层暴露的https端口
        this.httpsPort = 443;

        // sdk生成时选择的环境 RELEASE=线上  TEST=测试 PRE=预生产
        this.stage = "RELEASE";
        // 此参数暂时无用
        this.equipmentNo = "XXX";
        // 此参数暂时无用
        this.signStrategyUrl = "/getSignStrategy";
        // 此参数暂时无用
        this.tokenUrl = "/getTokenUrl";
        // 管理平台应用查看处获取并修改
        this.publicKey = "305C300D06092A864886F70D0101010500034B0030480241009BF063C6CDD7343C4DC0E3F15BA2B776B5DB787E384182492552DB8EBF1FD94170D4E29DFFEDD168C4D3C5FD5A641A35F14C2508F06EAC170148B4BEE8A587190203010001";



        // 关闭云锁验证
        this.icloudlockEnabled = false;
    }



    /**
    * Version:202208260859354380
    */
    public ApiResponse hhxx(String requestBody) {
        ApiRequest apiRequest = new ApiRequest(HttpConstant.SCHEME_HTTP, Method.POST, "/api/30F63F0067D7456DB13BCFC5036F6D42", SdkConstant.AUTH_TYPE_ENCRYPT,  "0b459fbf847b4d2e8646ac45ac9ae1cb");

        LOGGER.info("宣城获取火化信息地址：{},port:{}", apiRequest.getHost(), apiRequest.getPort());
        apiRequest.addParam("requestBody", requestBody, ParamPosition.QUERY, true);

        return syncInvoke(apiRequest);
    }

};