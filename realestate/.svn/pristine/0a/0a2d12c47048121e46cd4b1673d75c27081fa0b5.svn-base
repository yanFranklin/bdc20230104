package cn.gtmap.realestate.common.core.dto.exchange.xuancheng.gwsdk;

import com.iflytek.fsp.shield.java.sdk.constant.HttpConstant;
import com.iflytek.fsp.shield.java.sdk.constant.SdkConstant;
import com.iflytek.fsp.shield.java.sdk.enums.Method;
import com.iflytek.fsp.shield.java.sdk.enums.ParamPosition;
import com.iflytek.fsp.shield.java.sdk.enums.SignType;
import com.iflytek.fsp.shield.java.sdk.http.ApiClient;
import com.iflytek.fsp.shield.java.sdk.http.BaseApp;
import com.iflytek.fsp.shield.java.sdk.model.ApiRequest;
import com.iflytek.fsp.shield.java.sdk.model.ApiResponse;
import com.iflytek.fsp.shield.java.sdk.model.ApiSignStrategy;
import com.iflytek.fsp.shield.java.sdk.model.ResultInfo;

import java.util.Arrays;


/**
 * 个体工商户基本信息查询接口
 */
public class ShieldSyncAppGtgshjbxxcx extends BaseApp {

    public ShieldSyncAppGtgshjbxxcx() {
        this.apiClient = new ApiClient();
        this.apiClient.init();
        this.appId = "93bc782361304f6e9d766f3eab996503";
        this.appSecret = "B7DAC0B2BAF78341B1D2A7324E3E3953";
        this.host = "59.203.5.92";
        this.httpPort = 4989;
        this.httpsPort = 443;
        this.stage = "RELEASE";
        this.equipmentNo = "XXX";
        this.signStrategyUrl = "/getSignStrategy";
        this.tokenUrl = "/getTokenUrl";
        this.publicKey = "305C300D06092A864886F70D0101010500034B00304802410098BF2D031A1AC71262C8821AAE4E333D83E5388D05827932A6ABF58C1093C5423598535941540AE704B9AB6919DEDF4AF3E9BF94E9AB3BD7711A9B5D45A8BCBB0203010001";
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
    public ApiResponse Gtgsjbxxcx(String epbno, String name, String traname, String authcode) {
        ApiRequest apiRequest = new ApiRequest(
                HttpConstant.SCHEME_HTTP,
                Method.POST,
                "/api/4EFD02A7AC484666B7BB5B41D3477837",
                SdkConstant.AUTH_TYPE_ENCRYPT,
                SignType.DEFAULT.name(),
                "1");
        initSignStrategy(apiRequest);


        apiRequest.addParam("epbno", epbno, ParamPosition.FORM, true);

        apiRequest.addParam("name", name, ParamPosition.FORM, true);

        apiRequest.addParam("traname", traname, ParamPosition.FORM, true);

        apiRequest.addParam("authcode", authcode, ParamPosition.HEADER, false);

        return syncInvoke(apiRequest);
    }

};