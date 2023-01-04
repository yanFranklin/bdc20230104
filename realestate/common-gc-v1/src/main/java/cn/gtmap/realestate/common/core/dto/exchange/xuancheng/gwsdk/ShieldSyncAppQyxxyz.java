package cn.gtmap.realestate.common.core.dto.exchange.xuancheng.gwsdk;

import com.iflytek.fsp.shield.java.sdk.constant.HttpConstant;
import com.iflytek.fsp.shield.java.sdk.constant.SdkConstant;
import com.iflytek.fsp.shield.java.sdk.enums.Method;
import com.iflytek.fsp.shield.java.sdk.enums.ParamPosition;
import com.iflytek.fsp.shield.java.sdk.http.ApiClient;
import com.iflytek.fsp.shield.java.sdk.http.BaseApp;
import com.iflytek.fsp.shield.java.sdk.model.ApiRequest;
import com.iflytek.fsp.shield.java.sdk.model.ApiResponse;
import com.iflytek.fsp.shield.java.sdk.model.ApiSignStrategy;
import com.iflytek.fsp.shield.java.sdk.model.ResultInfo;

import java.util.Arrays;


/**
 * 企业基本信息验证接口
 */
public class ShieldSyncAppQyxxyz extends BaseApp {

    public ShieldSyncAppQyxxyz() {
        this.apiClient = new ApiClient();
        this.apiClient.init();
        this.appId = "a2b008e4f07346a89daebd5b872cd584";
        this.appSecret = "35050C55763750F939E932F4010D2FDE";
        this.host = "59.203.5.92";
        this.httpPort = 4989;
        this.httpsPort = 443;
        this.stage = "RELEASE";
        this.equipmentNo = "XXX";
        this.signStrategyUrl = "/getSignStrategy";
        this.tokenUrl = "/getTokenUrl";
        this.publicKey = "305C300D06092A864886F70D0101010500034B0030480241009C307BD1BBBA45B804FDE6E5DEBC59398DBDE898CC916F87E723DA2951DE2AE360601351D6E99A908D92912ACBAF06BC6A168985D8B5506394C8207080FC97630203010001";
    }


    private void initSignStrategy(ApiRequest apiRequest) {
        //获取服务安全策略信息
        ApiSignStrategy signStrategy = super.getSignStrategy(apiRequest.getPath());
        //判断是否需要token校验
        if (null != signStrategy && "token".equals(signStrategy.getSignType())) {
            //从本地缓存获取token信息,如果本地缓存存在token信息，验证本地缓存token的有效次数，
            //1.如果验证通过，token次数-1，回写到本地缓存；
            //2.如果验证不通过，从新获取token信息，并写到本地缓存。

            //从token服务获取token信息
            ResultInfo resultInfo = super.getTokenInfo(signStrategy);
            if (null != resultInfo && SdkConstant.SUCCESS.equals(resultInfo.getCode())) {
                apiRequest.setTokenValue(resultInfo.getData().getTokenValue());
                apiRequest.getHeaders().put(SdkConstant.AUTH_EQUIPMENTNO, Arrays.asList(equipmentNo));
            } else {
                System.err.println("获取token信息失败");
            }
        }
    }

    /**
     * Version:202007291119561910
     */
    public ApiResponse Qyxxyz(String entname, String uniscid, String authcode) {
        ApiRequest apiRequest = new ApiRequest(HttpConstant.SCHEME_HTTP, Method.POST, "/api/466828F9C3704FC5AF14D2FECA1BE12F", SdkConstant.AUTH_TYPE_ENCRYPT, "1");
        initSignStrategy(apiRequest);


        apiRequest.addParam("entname", entname, ParamPosition.FORM, true);

        apiRequest.addParam("uniscid", uniscid, ParamPosition.FORM, true);

        apiRequest.addParam("authcode", authcode, ParamPosition.HEADER, false);

        return syncInvoke(apiRequest);
    }

};