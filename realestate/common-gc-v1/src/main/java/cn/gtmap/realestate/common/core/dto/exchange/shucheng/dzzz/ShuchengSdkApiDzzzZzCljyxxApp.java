package cn.gtmap.realestate.common.core.dto.exchange.shucheng.dzzz;

import com.iflytek.fsp.shield.java.sdk.constant.HttpConstant;
import com.iflytek.fsp.shield.java.sdk.constant.SdkConstant;
import com.iflytek.fsp.shield.java.sdk.enums.Method;
import com.iflytek.fsp.shield.java.sdk.model.ApiRequest;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 1.0  2020-07-10
 * @description 电子证照信息接口 获取材料简易信息
 */
public class ShuchengSdkApiDzzzZzCljyxxApp extends ShuchengSdkApiDzzzClcxAndDownApp {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ShuchengSdkApiDzzzZzCljyxxApp.class);

    @Override
    public ApiRequest getRequest() {
        LOGGER.info("获取材料简易信息");
        ApiRequest apiRequest = new ApiRequest(HttpConstant.SCHEME_HTTP, Method.POST, "/api-0.8.20/zzwk-service/currency/querySummary", SdkConstant.AUTH_TYPE_ENCRYPT, "1");
        return apiRequest;
    }
}
