package cn.gtmap.realestate.common.core.dto.exchange.bengbu.zwsdk;

import com.iflytek.fsp.shield.java.sdk.constant.HttpConstant;
import com.iflytek.fsp.shield.java.sdk.constant.SdkConstant;
import com.iflytek.fsp.shield.java.sdk.enums.Method;
import com.iflytek.fsp.shield.java.sdk.enums.ParamPosition;
import com.iflytek.fsp.shield.java.sdk.http.ApiClient;
import com.iflytek.fsp.shield.java.sdk.http.BaseApp;
import com.iflytek.fsp.shield.java.sdk.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/3/23
 * @description 蚌埠政务取号api
 */
public class BengbuZwSdkAPi extends BaseApp {
    private static Logger LOGGER = LoggerFactory.getLogger(BengbuZwSdkAPi.class);


    public BengbuZwSdkAPi() {
        this.apiClient = new ApiClient();
        this.apiClient.init();
        this.appId = "c796da501fb241379428f7ee3ba08731";
        this.appSecret = "9DC025B303DCB0A17B084497E47514FA";
        this.host = "11.48.64.75";
        this.httpPort = 37808;
        this.httpsPort = 443;
        this.stage = "RELEASE";
        this.publicKey = "305C300D06092A864886F70D0101010500034B003048024100B4200583FA8FBCF13A54907A608C22481A224C41C73A41DB01F7CF8BEBA4F626929B768E7B5BFB2A6B56C550674A990F8AA210A156C9A322104D88339509CBF90203010001";
        this.equipmentNo = "XXX";
        this.signStrategyUrl = "/getSignStrategy";
        this.tokenUrl = "/getTokenUrl";
        this.publicKey = "305C300D06092A864886F70D0101010500034B003048024100B4200583FA8FBCF13A54907A608C22481A224C41C73A41DB01F7CF8BEBA4F626929B768E7B5BFB2A6B56C550674A990F8AA210A156C9A322104D88339509CBF90203010001";
        this.icloudlockEnabled = false;//关闭云锁验证
    }

    /**
     * 初始化方法，获取API签名策略信息
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
                //apiRequest.getHeaders().appendOne(SdkConstant.AUTH_EQUIPMENTNO,super.equipmentNo);
            } else {
                System.err.println("获取token信息失败");
            }
        }
    }

    public ApiResponse apasRegInfo(String apasRegInfo, String attrXml, String formXml) {
        ApiRequest apiRequest = new ApiRequest(HttpConstant.SCHEME_HTTP, Method.POST, "/api/apasRegInfo", SdkConstant.AUTH_TYPE_DEFAULT, "0d21a399f1c74800b10ea64b271e9a62");
//        initSignStrategy(apiRequest);
        LOGGER.info("蚌埠政务地址：{},port:{}", apiRequest.getHost(), apiRequest.getPort());

        apiRequest.addParam("apasRegnfoXml", apasRegInfo, ParamPosition.FORM, false);
        apiRequest.addParam("attrXml", attrXml, ParamPosition.FORM, false);
        apiRequest.addParam("formXml", formXml, ParamPosition.FORM, false);
        return syncInvoke(apiRequest);
    }

    /**
     * Version:201901272045229603
     */
    public void apasRegInfo(String apasRegnfoXml, String attrXml, String formXml, ApiCallback apiCallback) {
        ApiRequest apiRequest = new ApiRequest(HttpConstant.SCHEME_HTTP, Method.POST, "/api/apasRegInfo", SdkConstant.AUTH_TYPE_DEFAULT, "0d21a399f1c74800b10ea64b271e9a62");
        //initSignStrategy(apiRequest);


        apiRequest.addParam("apasRegnfoXml", apasRegnfoXml, ParamPosition.FORM, false);

        apiRequest.addParam("attrXml", attrXml, ParamPosition.FORM, false);

        apiRequest.addParam("formXml", formXml, ParamPosition.FORM, false);

        asyncInvoke(apiRequest, apiCallback);
    }

    /**
     * Version:202004091116385302
     */
    public void projIdFeedback(String projIdFeedbackXml, ApiCallback apiCallback) {
        ApiRequest apiRequest = new ApiRequest(HttpConstant.SCHEME_HTTP, Method.POST, "/1.0/djz-tylsh-rest/api/projIdFeedback", SdkConstant.AUTH_TYPE_ENCRYPT, "1");
        //initSignStrategy(apiRequest);


        apiRequest.addParam("projIdFeedbackXml", projIdFeedbackXml, ParamPosition.FORM, true);

        asyncInvoke(apiRequest, apiCallback);
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
        ApiRequest apiRequest = new ApiRequest(HttpConstant.SCHEME_HTTP, Method.POST, "/api/apasInfo", SdkConstant.AUTH_TYPE_DEFAULT, "79cb6b93c6c346beb3d47b26d9e99839");
        initSignStrategy(apiRequest);
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
        ApiRequest apiRequest = new ApiRequest(HttpConstant.SCHEME_HTTP, Method.POST, "/api/accept", SdkConstant.AUTH_TYPE_DEFAULT, "79cb6b93c6c346beb3d47b26d9e99839");
        initSignStrategy(apiRequest);
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
        ApiRequest apiRequest = new ApiRequest(HttpConstant.SCHEME_HTTP, Method.POST, "/api/transact", SdkConstant.AUTH_TYPE_DEFAULT, "79cb6b93c6c346beb3d47b26d9e99839");
        initSignStrategy(apiRequest);
        apiRequest.addParam("transactXml", apasRegInfo, ParamPosition.FORM, false);
        apiRequest.addParam("attrXml", attrXml, ParamPosition.FORM, false);
        apiRequest.addParam("formXml", formXml, ParamPosition.FORM, false);
        apiRequest.addParam("pubXml", formXml, ParamPosition.FORM, false);
        return syncInvoke(apiRequest);
    }
}