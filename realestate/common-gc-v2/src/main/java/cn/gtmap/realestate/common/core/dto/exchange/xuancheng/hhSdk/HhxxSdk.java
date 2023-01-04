package cn.gtmap.realestate.common.core.dto.exchange.xuancheng.hhSdk;

import cn.gtmap.realestate.common.core.dto.exchange.xuancheng.zwsdk.XuanchengZwSdkAPi;
import com.iflytek.fsp.shield.java.sdk.constant.HttpConstant;
import com.iflytek.fsp.shield.java.sdk.constant.SdkConstant;
import com.iflytek.fsp.shield.java.sdk.enums.Method;
import com.iflytek.fsp.shield.java.sdk.enums.ParamPosition;
import com.iflytek.fsp.shield.java.sdk.enums.SignType;
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

        // 管理平台应用查看处获取并修改
        this.gmAppSecret = "6C74F0AB6490D2AE76915E21F9C571A7A27BE9424339821AF7B38751B885EDB6";

        // 核心层ip
        this.host = "10.171.16.163";
        //核心层上下文
        this.contextPath ="";

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

        // 管理平台应用查看处获取并修改
        this.gmPublicKey = "3059301306072A8648CE3D020106082A811CCF5501822D0342000424A163687A0AB523F489459DBBC595974B9675AE5EEA17072CD41361203E6248A0B6D561AE281F14F6E5E9F976BFF865823D00D9A999621A66E9EA896B7B71E8";


        // 管理平台应用查看处获取并修改
        this.gmPrivateKey = "308193020100301306072A8648CE3D020106082A811CCF5501822D047930770201010420F0BCDBD8CC8B81F155E67CA0B18287D4A74E65AB0B682B8C75174D4020080E73A00A06082A811CCF5501822DA1440342000424A163687A0AB523F489459DBBC595974B9675AE5EEA17072CD41361203E6248A0B6D561AE281F14F6E5E9F976BFF865823D00D9A999621A66E9EA896B7B71E8";

        // 关闭云锁验证
        this.icloudlockEnabled = false;
    }



    /**
    * Version:202208260859354380
    */
    public ApiResponse hhxx(String requestBody) {
        ApiRequest apiRequest = new ApiRequest(HttpConstant.SCHEME_HTTP, Method.POST, "/api/30F63F0067D7456DB13BCFC5036F6D42", SdkConstant.AUTH_TYPE_ENCRYPT, SignType.DEFAULT.name(), "0b459fbf847b4d2e8646ac45ac9ae1cb");

        LOGGER.info("宣城获取火化信息地址：{},port:{}", apiRequest.getHost(), apiRequest.getPort());
        apiRequest.addParam("requestBody", requestBody, ParamPosition.QUERY, true);

        return syncInvoke(apiRequest);
    }

};