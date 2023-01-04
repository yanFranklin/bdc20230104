package cn.gtmap.realestate.common.core.dto.exchange.shucheng.dzzz;

import com.iflytek.fsp.shield.java.sdk.constant.HttpConstant;
import com.iflytek.fsp.shield.java.sdk.constant.SdkConstant;
import com.iflytek.fsp.shield.java.sdk.enums.Method;
import com.iflytek.fsp.shield.java.sdk.model.ApiRequest;

/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 1.0  2020-07-10
 * @description 电子证照信息接口 查询电子证照信息
 */
public class ShuchengSdkApiDzzzZzClxxCxApp extends ShuchengSdkApiDzzzClcxAndDownApp {

    @Override
    public ApiRequest getRequest() {
        ApiRequest apiRequest = new ApiRequest(HttpConstant.SCHEME_HTTP, Method.POST, "/api-0.8.20/zzwk-service/currency/queryMaterial", SdkConstant.AUTH_TYPE_ENCRYPT, "1");
        return apiRequest;
    }
}
