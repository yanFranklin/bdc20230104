package cn.gtmap.realestate.common.core.dto.exchange.xuancheng.zwsdk;

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
public class XuanchengZwSdkAPi extends BaseApp {
    private static Logger LOGGER = LoggerFactory.getLogger(XuanchengZwSdkAPi.class);


    public XuanchengZwSdkAPi() {
        this.apiClient = new ApiClient();
        this.apiClient.init();
        this.appId = "678614342927447b9ccb013de2074e6a";
        this.appSecret = "8DAD4E77C94D2CF3A97294E96636501A";
        this.host = "59.203.166.60";
        this.httpPort = 4989;
        this.httpsPort = 443;
        this.stage = "RELEASE";
        this.equipmentNo = "XXX";
        this.signStrategyUrl = "/getSignStrategy";
        this.tokenUrl = "/getTokenUrl";
        this.publicKey = "305C300D06092A864886F70D0101010500034B003048024100BD3131B7851D725FA3470E221E263E0DA3051B71385DA7A6D3305012B98FBA9BF348C190FEDA884251943654F4F5584D965A8B3A8EBDD1523A33160C426986970203010001";
        this.icloudlockEnabled = false;//关闭云锁验证
    }

    /**
     * 初始化方法，获取API签名策略信息
     */
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
                //apiRequest.getHeaders().appendOne(SdkConstant.AUTH_EQUIPMENTNO,equipmentNo);
            }else{
                System.err.println("获取token信息失败");
            }
        }
    }

    /**
     * 办件号取号
     */
    public ApiResponse projIdFeedback(String projIdFeedbackXml, ApiCallback apiCallback) {
        ApiRequest apiRequest = new ApiRequest(HttpConstant.SCHEME_HTTP,
                Method.POST,
                "/1.0.0/djz-tylsh-rest/api/projIdFeedback",
                SdkConstant.AUTH_TYPE_ENCRYPT,
                "1");
        initSignStrategy(apiRequest);


        apiRequest.addParam("projIdFeedbackXml", projIdFeedbackXml, ParamPosition.FORM, true);

        return syncInvoke(apiRequest);
    }


    /**
     * 受理
     *
     * @param apasInfoXml
     * @param attrXml
     * @param formXml
     * @return
     */
    public ApiResponse apasInfo(String acceptInfoXml, String attrXml) {
        ApiRequest apiRequest = new ApiRequest(HttpConstant.SCHEME_HTTP,
                Method.POST,
                "/api/accept",
                SdkConstant.AUTH_TYPE_DEFAULT,
                "d0ebe8198536403eaa22b0df6b1b8c8e");
        initSignStrategy(apiRequest);


        apiRequest.addParam("acceptInfoXml", acceptInfoXml, ParamPosition.FORM, false);

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
    public ApiResponse transactXml(String transactXml, String formXml, String attrXml) {
        ApiRequest apiRequest = new ApiRequest(HttpConstant.SCHEME_HTTP,
                Method.POST,
                "/api/transact",
                SdkConstant.AUTH_TYPE_DEFAULT,
                "d0ebe8198536403eaa22b0df6b1b8c8e");
        initSignStrategy(apiRequest);

        apiRequest.addParam("transactXml", transactXml, ParamPosition.FORM, false);

        apiRequest.addParam("formXml", formXml, ParamPosition.FORM, false);

        apiRequest.addParam("attrXml", attrXml, ParamPosition.FORM, false);

        return syncInvoke(apiRequest);
    }


    /**
     * 申报登记反馈
     * @param apasRegnfoXml
     * @param attrXml
     * @param formXml
     * @param apiCallback
     */
    public void apasRegInfo(String apasRegnfoXml, String attrXml, String formXml, ApiCallback apiCallback) {
        ApiRequest apiRequest = new ApiRequest(HttpConstant.SCHEME_HTTP,
                Method.POST, "/api/apasRegInfo",
                SdkConstant.AUTH_TYPE_DEFAULT,
                "d0ebe8198536403eaa22b0df6b1b8c8e");
        initSignStrategy(apiRequest);

        apiRequest.addParam("apasRegnfoXml", apasRegnfoXml, ParamPosition.FORM, false);

        apiRequest.addParam("attrXml", attrXml, ParamPosition.FORM, false);

        apiRequest.addParam("formXml", formXml, ParamPosition.FORM, false);

        asyncInvoke(apiRequest, apiCallback);
    }

    public ApiResponse apasRegInfo(String apasRegInfo, String attrXml, String formXml) {
        ApiRequest apiRequest = new ApiRequest(HttpConstant.SCHEME_HTTP,
                Method.POST, "/api/apasRegInfo",
                SdkConstant.AUTH_TYPE_DEFAULT,
                "d0ebe8198536403eaa22b0df6b1b8c8e");
        initSignStrategy(apiRequest);
        LOGGER.info("宣城政务地址：{},port:{}", apiRequest.getHost(), apiRequest.getPort());

        apiRequest.addParam("apasRegnfoXml", apasRegInfo, ParamPosition.FORM, false);
        apiRequest.addParam("attrXml", attrXml, ParamPosition.FORM, false);
        apiRequest.addParam("formXml", formXml, ParamPosition.FORM, false);
        return syncInvoke(apiRequest);
    }
}