package cn.gtmap.realestate.common.core.dto.exchange.hefei.sdkApiApp;

import com.iflytek.fsp.shield.java.sdk.constant.HttpConstant;
import com.iflytek.fsp.shield.java.sdk.constant.SdkConstant;
import com.iflytek.fsp.shield.java.sdk.enums.Method;
import com.iflytek.fsp.shield.java.sdk.model.ApiRequest;

/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 1.0  2020-05-08
 * @description 电子证照信息接口 制证签章接口
 */
public class SdkApiDzzzZzwhApp extends SdkApiDzzzZzqzTokenApp {

    @Override
    public ApiRequest getRequest() {
        ApiRequest apiRequest = new ApiRequest(HttpConstant.SCHEME_HTTP, Method.POST, "/2.0/zzwk-service/criterion/certificateMaintain", SdkConstant.AUTH_TYPE_ENCRYPT, "1");
        return apiRequest;
    }
}
