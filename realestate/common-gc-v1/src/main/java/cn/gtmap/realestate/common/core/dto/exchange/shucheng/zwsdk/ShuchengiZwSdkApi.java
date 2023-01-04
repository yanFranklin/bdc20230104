package cn.gtmap.realestate.common.core.dto.exchange.shucheng.zwsdk;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShuchengiZwSdkApi extends BaseApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShuchengiZwSdkApi.class);


    public ShuchengiZwSdkApi() {
        this.apiClient = new ApiClient();
        this.apiClient.init();
        this.appId = "d8f24dcdf9d4495ead4d807d2bfd5cf9";
        this.appSecret = "539983FEB48C9BE2DC111BC2A7BEA2AE";
        this.host = "59.203.234.225";
        this.httpPort = 8785;
        this.httpsPort = 443;
        this.stage = "RELEASE";
        this.equipmentNo = "XXX";
        this.signStrategyUrl = "/getSignStrategy";
        this.tokenUrl = "/getTokenUrl";
        this.publicKey = "305C300D06092A864886F70D0101010500034B00304802410099EE9E3E9BF8066E81DE53DD8E7ED7FF4EAD5F8B234A72E67F612BBF58741A64456C6A6932BCE620BD0249F4AD3EDCEE37D679E44793815FDE5ABBC9233622AB0203010001";
        this.icloudlockEnabled = false;//关闭云锁验证
    }

    /**
     * 初始化，获取服务签名策略
     */
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
            } else {
                LOGGER.error("获取token信息失败");
            }
        }
    }

    /**
     * @param apasRegInfo
     * @param attrXml
     * @param formXml
     * @return
     */
    public ApiResponse apasRegInfo(String apasRegInfo, String attrXml, String formXml) {
        ApiRequest apiRequest = new ApiRequest(HttpConstant.SCHEME_HTTP, Method.POST, "acceptance-rest/api/apasRegInfo", SdkConstant.AUTH_TYPE_DEFAULT, "039c9c4193684da08acc4fefdce35667");
//        initSignStrategy(apiRequest);
        apiRequest.addParam("apasRegnfoXml", apasRegInfo, ParamPosition.FORM, false);
        apiRequest.addParam("attrXml", attrXml, ParamPosition.FORM, false);
        apiRequest.addParam("formXml", formXml, ParamPosition.FORM, false);
        return syncInvoke(apiRequest);
    }

    /**
     * 申报登记
     *
     * @param apasInfoXml
     * @param attrXml
     * @param formXml
     * @return
     */
    public ApiResponse apasInfo(String apasRegInfo, String attrXml, String formXml) {
        ApiRequest apiRequest = new ApiRequest(HttpConstant.SCHEME_HTTP, Method.POST, "/api/apasInfo", SdkConstant.AUTH_TYPE_DEFAULT, "039c9c4193684da08acc4fefdce35667");
//        initSignStrategy(apiRequest);
        apiRequest.addParam("apasInfoXml", apasRegInfo, ParamPosition.FORM, false);
        apiRequest.addParam("attrXml", attrXml, ParamPosition.FORM, false);
        apiRequest.addParam("formXml", formXml, ParamPosition.FORM, false);
        return syncInvoke(apiRequest);
    }

    /**
     * 申报登记_受理或删除节点信息推送
     *
     * @param apasInfoXml
     * @param attrXml
     * @param formXml
     * @return
     */
    public ApiResponse acceptInfo(String apasRegInfo, String attrXml) {
        ApiRequest apiRequest = new ApiRequest(HttpConstant.SCHEME_HTTP, Method.POST, "/api/accept", SdkConstant.AUTH_TYPE_DEFAULT, "039c9c4193684da08acc4fefdce35667");
//        initSignStrategy(apiRequest);
        apiRequest.addParam("acceptInfoXml", apasRegInfo, ParamPosition.FORM, false);
        apiRequest.addParam("attrXml", attrXml, ParamPosition.FORM, false);
        return syncInvoke(apiRequest);
    }

    /**
     * 申报登记_办结
     *
     * @param acceptInfoXml
     * @param attrXml
     * @param formXml
     * @return
     */
    public ApiResponse transactXml(String apasRegInfo, String attrXml, String formXml) {
        ApiRequest apiRequest = new ApiRequest(HttpConstant.SCHEME_HTTP, Method.POST, "/api/transact", SdkConstant.AUTH_TYPE_DEFAULT, "039c9c4193684da08acc4fefdce35667");
        initSignStrategy(apiRequest);
        apiRequest.addParam("transactXml", apasRegInfo, ParamPosition.FORM, false);
        apiRequest.addParam("attrXml", attrXml, ParamPosition.FORM, false);
        apiRequest.addParam("formXml", formXml, ParamPosition.FORM, false);
        apiRequest.addParam("pubXml", formXml, ParamPosition.FORM, false);
        return syncInvoke(apiRequest);
    }

}
