package cn.gtmap.realestate.common.core.dto.exchange.hefei.sdkApiApp;

import com.iflytek.fsp.shield.java.sdk.constant.HttpConstant;
import com.iflytek.fsp.shield.java.sdk.constant.SdkConstant;
import com.iflytek.fsp.shield.java.sdk.enums.Method;
import com.iflytek.fsp.shield.java.sdk.model.ApiRequest;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2020-01-16
 * @description 电子证照信息接口 证照材料文件下载
 */
public class SdkApiDzzzClxzApp extends SdkApiDzzzTokenApp {

    @Override
    public ApiRequest getRequest() {
        ApiRequest apiRequest = new ApiRequest(HttpConstant.SCHEME_HTTP, Method.POST, "/api-0.8.20/zzwk-service/criterion/certificateDownload", SdkConstant.AUTH_TYPE_ENCRYPT, "1");
        return apiRequest;
    }
}
