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
 * 企业基本信息查询接口
 */
public class ShieldSyncAppQyxxcx extends BaseApp {

    public ShieldSyncAppQyxxcx() {
        this.apiClient = new ApiClient();
        this.apiClient.init();
        this.appId = "e393a37467a94471a76343cc75314e10";
        this.appSecret = "D28CF33CADCD87DEC97CEA99A8EA69C5";
        this.host = "59.203.5.92";
        this.httpPort = 4989;
        this.httpsPort = 443;
        this.stage = "RELEASE";
        this.equipmentNo = "XXX";
        this.signStrategyUrl = "/getSignStrategy";
        this.tokenUrl = "/getTokenUrl";
        this.publicKey = "305C300D06092A864886F70D0101010500034B003048024100B2A6A66EA45FB8316ADEB4E5B2FA18FFEC17332B76F7BE1A3CF7B067A3F52752AF9CA23C00FB714570F6262FB59F7BC535DE5838B0EEFC8297E0D02EBA4F76050203010001";
    }


    private void initSignStrategy(ApiRequest apiRequest) {
        //获取服务安全策略信息
        ApiSignStrategy signStrategy = super.getSignStrategy(apiRequest.getPath());
        //判断是否需要token校验
        if(null!=signStrategy && "token".equals(signStrategy.getSignType())){
            //从本地缓存获取token信息,如果本地缓存存在token信息，验证本地缓存token的有效次数，
            //1.如果验证通过，token次数-1，回写到本地缓存；
            //2.如果验证不通过，从新获取token信息，并写到本地缓存。

            //从token服务获取token信息
            ResultInfo resultInfo = super.getTokenInfo(signStrategy);
            if(null!=resultInfo && SdkConstant.SUCCESS.equals(resultInfo.getCode())) {
                apiRequest.setTokenValue(resultInfo.getData().getTokenValue());
                apiRequest.getHeaders().put(SdkConstant.AUTH_EQUIPMENTNO,Arrays.asList(equipmentNo));
            }else{
                System.err.println("获取token信息失败");
            }
        }
    }

    /**
     * Version:202007291119561910
     */
    public ApiResponse Qyxxcx(String entname, String uniscid, String authcode) {
        ApiRequest apiRequest = new ApiRequest(HttpConstant.SCHEME_HTTP, Method.POST, "/api/74E0A3C1D01B4AE3B6A111C62C960E4A", SdkConstant.AUTH_TYPE_ENCRYPT, "1");
        initSignStrategy(apiRequest);

        apiRequest.addParam("authcode", authcode, ParamPosition.HEADER, false);

        apiRequest.addParam("entname", entname, ParamPosition.FORM, false);

        apiRequest.addParam("uniscid", uniscid, ParamPosition.FORM, false);

        return syncInvoke(apiRequest);
    }

};