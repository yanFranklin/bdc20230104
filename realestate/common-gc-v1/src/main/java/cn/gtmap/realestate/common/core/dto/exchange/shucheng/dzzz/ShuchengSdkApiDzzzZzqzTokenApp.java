package cn.gtmap.realestate.common.core.dto.exchange.shucheng.dzzz;

import cn.gtmap.realestate.common.core.dto.exchange.SdkApiAbstractApp;
import com.alibaba.fastjson.JSONObject;
import com.iflytek.fsp.shield.java.sdk.constant.HttpConstant;
import com.iflytek.fsp.shield.java.sdk.constant.SdkConstant;
import com.iflytek.fsp.shield.java.sdk.enums.Method;
import com.iflytek.fsp.shield.java.sdk.http.ApiClient;
import com.iflytek.fsp.shield.java.sdk.model.ApiRequest;
import com.iflytek.fsp.shield.java.sdk.model.ApiResponse;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0  2020-01-16
 * @description 获取电子证照的token
 */
public class ShuchengSdkApiDzzzZzqzTokenApp extends SdkApiAbstractApp {


    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ShuchengSdkApiDzzzZzqzTokenApp.class);

    public ShuchengSdkApiDzzzZzqzTokenApp() {
        this.apiClient = new ApiClient();
        this.apiClient.init();
        this.appId = "49e0ab41b1db4b64a446bd7b5baceb9d";
        this.appSecret = "C6FAE1476811FF2FEED9B1923EAEB43B";
        this.host = "59.203.234.225";
        this.httpPort = 4989;
        this.httpsPort = 443;
        this.stage = "RELEASE";
        this.equipmentNo = "XXX";
        this.signStrategyUrl = "/getSignStrategy";
        this.tokenUrl = "/getTokenUrl";
        this.publicKey = "305C300D06092A864886F70D0101010500034B0030480241008A64C8D7B46B9D89ADD095CFE67ED476F572952ECB79292E9EC47C3EE53D120C427C0A670859B245065E2B9ED06EEA5ABE06E041BB28327850A35F9EE47798390203010001";
        this.icloudlockEnabled = false;//关闭云锁验证
    }

    @Override
    public ApiRequest getRequest() {
        ApiRequest apiRequest = new ApiRequest(HttpConstant.SCHEME_HTTP, Method.POST, "/criterion/certificateMaintain", SdkConstant.AUTH_TYPE_DEFAULT, "242d458b48214a2fa0164fa598519641");
        return apiRequest;
    }

    @Override
    public ApiResponse getApiResponse(Map<String, Object> requestParamMap) {
        ApiRequest apiRequest = getRequest();
      /*  initSignStrategy(apiRequest);
        setParam(apiRequest, requestParamMap);*/

        try {

            apiRequest.setBody(JSONObject.toJSONString(requestParamMap).getBytes("UTF-8"));
//            makeSignCertificate(JSONObject.toJSONString(requestParamMap).getBytes("UTF-8"));
//            LOGGER.info("舒城电子证照请求参数：{}", apiRequest.getBody());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return syncInvoke(apiRequest);
    }


    /**
     * Version:202011091708438308
     */
    public ApiResponse makeSignCertificate(byte[] body) {
        ApiRequest apiRequest = new ApiRequest(HttpConstant.SCHEME_HTTP, Method.POST, "/criterion/makeSignCertificate", SdkConstant.AUTH_TYPE_DEFAULT, "242d458b48214a2fa0164fa598519641");
        // initSignStrategy(apiRequest);
        LOGGER.info("舒城电子证照body代码测试！");
        apiRequest.setBody(body);

        return syncInvoke(apiRequest);
    }

   /* public static void main(String[] args) throws Exception{
        JSONObject paramObj =new JSONObject();
        paramObj.put("contentCode", "1134142500323877890026");
        //paramObj.put("infoTypeCode","001");
        paramObj.put("ownerId", "91341523798129996E");
        paramObj.put("ownerName", "舒城县惠源商贸有限责任公司");
        paramObj.put("infoValidityBegin", "20220215");
        paramObj.put("format","ofd");
        paramObj.put("slbh","202202150004");
        //paramObj.put("infoValidityEnd", "2041-01-01");
        JSONObject surface = new JSONObject();
        surface.put("djh", "0000031");
        surface.put("qt", "不动产权证书号:皖(2019)舒城县不动产权第0004178号\n抵押方式:一般抵押\n担保债权数额,45万元\n债务履行期限：2022年02月15日至2022年02月28日");
        surface.put("fj", "业务编号：202202150004");
        surface.put("zmh2", "2022");
        surface.put("zmh1", "皖");
        surface.put("bh", "34000000016");
        surface.put("zmh3", "舒城县");
        surface.put("infoCode", "皖(2022)舒城县不动产证明第0000031号");
        surface.put("czrsfzh1", "91341523798129996E");
        surface.put("cxewm", "");
        surface.put("n", "2022");
        surface.put("r", "15");
        surface.put("zl", "城关镇古城北路青青家园拆迁安置工程1幢5层503室");
        surface.put("ywr", "王伟辉 徐林");
        surface.put("qlr", "舒城县惠源商贸有限责任公司");
        surface.put("whewm", "");
        surface.put("bdcdyh", "341523005104GB01004F00010431");
        surface.put("czr1", "舒城县惠源商贸有限责任公司");
        surface.put("qlhsx", "抵押权");
        surface.put("y", "2");
        surface.put("ewm", "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");
        paramObj.put("data", surface.toJSONString());
//        byte[] bytes = JSONObject.toJSONString(paramObj).getBytes("UTF-8");
        ShuchengSdkApiDzzzZzqzApp test = new ShuchengSdkApiDzzzZzqzApp();
        ApiResponse response = test.getApiResponse(paramObj);
        System.out.println(new String(response.getBody(), "UTF-8"));
    }*/
}