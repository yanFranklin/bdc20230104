package cn.gtmap.realestate.common.core.dto.exchange.xuancheng;

import cn.gtmap.realestate.common.core.dto.exchange.SdkApiAbstractApp;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import com.alibaba.fastjson.JSONObject;
import com.iflytek.fsp.shield.java.sdk.constant.HttpConstant;
import com.iflytek.fsp.shield.java.sdk.constant.SdkConstant;
import com.iflytek.fsp.shield.java.sdk.enums.Method;
import com.iflytek.fsp.shield.java.sdk.enums.ParamPosition;
import com.iflytek.fsp.shield.java.sdk.enums.SignType;
import com.iflytek.fsp.shield.java.sdk.http.ApiClient;
import com.iflytek.fsp.shield.java.sdk.model.ApiRequest;
import com.iflytek.fsp.shield.java.sdk.model.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 1.0  2020-05-08
 * @description (宣城)电子证照信息接口 制证签章接口
 */
public class XuanchengSdkApiDzzzZzqzApp extends SdkApiAbstractApp {
    private static final Logger LOGGER = LoggerFactory.getLogger(XuanchengSdkApiDzzzZzqzApp.class);

    public XuanchengSdkApiDzzzZzqzApp() {
        this.apiClient = new ApiClient();
        this.apiClient.init();
        this.appId = "c8409a57018b4694b1561d32b99dcba7";
        this.appSecret = "9299D17988169E9330A7334239B74FF9";
        // 管理平台应用查看处获取并修改
        this.gmAppSecret = "3B02073BDBAE8BF083E48646C1CB0D04B85C0664BABD1863267959A58103F893";
        this.host = "10.171.16.163";
        this.httpPort = 8800;
        this.httpsPort = 443;
        this.stage = "RELEASE";
        this.publicKey = "305C300D06092A864886F70D0101010500034B003048024100B017A950458D32B665F17D5B5CF6576B491F66A9E84E9740872D2CAB1CDA1B57EB7D2D134D2F35474302007E0987857920180CABA2179C929AFB8989FA5C01E50203010001";
        this.equipmentNo = "XXX";
        this.signStrategyUrl = "/getSignStrategy";
        this.tokenUrl = "/getTokenUrl";
        this.publicKey = "305C300D06092A864886F70D0101010500034B0030480241009CCBB056C7B03FC0115B84B430A78451D654BBB0DF89EE375A4B4CBEC6AA2F41C46B6E0DF84B73ABCCCF2865674B888C81D304278FEE8AA1BFF086A9E207B7490203010001";
        this.icloudlockEnabled = false;//关闭云锁验证
        //核心层上下文
        this.contextPath = "";
        // 管理平台应用查看处获取并修改
        this.gmPublicKey = "3059301306072A8648CE3D020106082A811CCF5501822D03420004348EFC3F55535AC2986D8FC3867A98E6E957CD5C2DBE106F555C3B04B4C6E8B889FE57B946950B9CC6D3C80D1CB86456EE81458295B37677558146D801F41792";


        // 管理平台应用查看处获取并修改
        this.gmPrivateKey = "308193020100301306072A8648CE3D020106082A811CCF5501822D0479307702010104200B8B48EF936461F1444839BEF7125E6C58BE64E36307B8578CC268DCAA17BC8EA00A06082A811CCF5501822DA14403420004348EFC3F55535AC2986D8FC3867A98E6E957CD5C2DBE106F555C3B04B4C6E8B889FE57B946950B9CC6D3C80D1CB86456EE81458295B37677558146D801F41792";

    }

    @Override
    public ApiRequest getRequest() {
//        ApiRequest apiRequest = new ApiRequest(HttpConstant.SCHEME_HTTP, Method.POST, "/api/225C8283A8E74FB99C119A8C89CEBBE6", SdkConstant.AUTH_TYPE_ENCRYPT, SignType.DEFAULT.name(), "0b459fbf847b4d2e8646ac45ac9ae1cb");
        ApiRequest apiRequest = new ApiRequest(HttpConstant.SCHEME_HTTP, Method.POST, "/api/225C8283A8E74FB99C119A8C89CEBBE6", SdkConstant.AUTH_TYPE_ENCRYPT, SignType.DEFAULT.name(), "0b459fbf847b4d2e8646ac45ac9ae1cb");

        return apiRequest;
    }

    @Override
    public ApiResponse getApiResponse(Map<String, Object> requestParamMap) {
        ApiRequest apiRequest = getRequest();
//        initSignStrategy(apiRequest);
//        setParam(apiRequest, requestParamMap);
//        apiRequest.addParam("appId", appId, ParamPosition.HEADER, true);

//        String uuid = UUIDGenerator.generate();
//        apiRequest.addParam("requestId", uuid, ParamPosition.HEADER, true);
        try {
            apiRequest.setBody(JSONObject.toJSONString(requestParamMap).getBytes("UTF-8"));
            LOGGER.info("宣城电子证照接口请求参数bytes：{}", apiRequest.getBody());
            LOGGER.info("宣城电子证照接口请求参数：{}", JSONObject.toJSONString(requestParamMap));
//            LOGGER.info("宣城电子证照接口请求参数requestId：{}", uuid);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return syncInvoke(apiRequest);
    }
}
