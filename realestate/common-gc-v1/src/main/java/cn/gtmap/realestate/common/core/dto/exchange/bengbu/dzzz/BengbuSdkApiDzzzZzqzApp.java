package cn.gtmap.realestate.common.core.dto.exchange.bengbu.dzzz;

import cn.gtmap.realestate.common.core.dto.exchange.hefei.sdkApiApp.SdkApiDzzzZzqzTokenApp;
import com.iflytek.fsp.shield.java.sdk.constant.HttpConstant;
import com.iflytek.fsp.shield.java.sdk.constant.SdkConstant;
import com.iflytek.fsp.shield.java.sdk.enums.Method;
import com.iflytek.fsp.shield.java.sdk.model.ApiRequest;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0  2020-05-08
 * @description 电子证照信息接口 制证签章接口
 */
public class BengbuSdkApiDzzzZzqzApp extends BengbuSdkApiDzzzZzqzTokenApp {

    @Override
    public ApiRequest getRequest() {
        ApiRequest apiRequest = new ApiRequest(HttpConstant.SCHEME_HTTP, Method.POST, "/criterion/makesignCertificate", SdkConstant.AUTH_TYPE_ENCRYPT, "a2b55e65007d45b3bc1e045581ddfec1");
        return apiRequest;
    }
}
