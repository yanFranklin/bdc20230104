package cn.gtmap.realestate.exchange.service.impl.inf.request;

import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.HttpClientService;
import cn.gtmap.realestate.exchange.config.TokenServiceChoose;
import cn.gtmap.realestate.exchange.core.bo.log.AuditEventBO;
import cn.gtmap.realestate.exchange.core.bo.reqProp.HttpReqPropBO;
import cn.gtmap.realestate.exchange.service.inf.TokenService;
import cn.gtmap.realestate.exchange.service.inf.build.InterfaceRequestBuilder;
import cn.gtmap.realestate.exchange.service.inf.request.InterfaceRequestService;
import cn.gtmap.realestate.exchange.util.CommonUtil;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-05-22
 * @description HTTP POST 类型请求
 */
@Service(value = "httpTokenPost")
public class HttpTokenPostRequestServiceImpl extends InterfaceRequestService<HttpReqPropBO> {

    @Autowired
    private HttpClientService httpClientService;
    @Autowired
    private TokenServiceChoose tokenServiceChoose;

    /**
     * @param builder
     * @return java.lang.Object
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 发送请求
     */
    @Override
    public void sendRequest(InterfaceRequestBuilder builder) {
        Object requestBody = builder.getRequestBody();
        HttpReqPropBO prop = super.getRequestPropFromBuilder(builder);
        Map<String, Object> requestParamMap = new HashMap<>();
        if (!(requestBody instanceof List)) {
            requestParamMap = CommonUtil.objectToMap(requestBody);
        }
        if (StringUtils.isNotBlank(prop.getUrl())) {
            StringBuilder requestUrl = new StringBuilder(prop.getUrl());
            String token = null;
            //获取对应的token实现类
            if (StringUtils.isNotBlank(prop.getTokenInterface())) {
                TokenService tokenService = tokenServiceChoose.getTokenService(prop.getTokenInterface());
                if (tokenService != null) {
                    token = tokenService.getToken();
                }
            } else {
                TokenService tokenService = tokenServiceChoose.getTokenService(Constants.TOKEN.YKQ_TOKEN);
                token = tokenService.getToken();
                requestUrl.append("?token=");
                requestUrl.append(token);
            }
            HttpPost httpPost = new HttpPost(requestUrl.toString());
            if (StringUtils.isNotBlank(prop.getContentType())) {
                httpPost.setHeader("Content-Type", prop.getContentType());
            }
            if (StringUtils.isNotBlank(prop.getTokenInterface())) {
                httpPost.setHeader("token", token);
            }
            httpPost.setHeader("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
            httpPost.setHeader("unitCode", "LandResourcesBureau");
            if (StringUtils.isNotBlank(prop.getAppKey())) {
                httpPost.setHeader("appKey", prop.getAppKey());
            }
            if (StringUtils.isNotBlank(prop.getxPlatToken())) {
                httpPost.setHeader("X-PLAT-TOKEN", prop.getxPlatToken());
            }
            List<NameValuePair> parameters = Lists.newArrayList();
            if (requestParamMap != null && CollectionUtils.isNotEmpty(requestParamMap.keySet())) {
                for (String key : requestParamMap.keySet()) {
                    Object value = requestParamMap.get(key);
                    if (!(value instanceof String)) {
                        value = JSONObject.toJSONString(value);
                    }
                    parameters.add(new BasicNameValuePair(key, (String) value));
                }
            }
            httpPost.setEntity(new UrlEncodedFormEntity(parameters, Charsets.UTF_8));
            if (StringUtils.isNotBlank(prop.getSoTimeout()) && NumberUtils.isNumber(prop.getSoTimeout())) {
                RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(NumberUtils.toInt(prop.getSoTimeout())).build();
                LOGGER.error("httpPost 请求url：{}，参数配置:{}", requestUrl, requestConfig.toString());
                httpPost.setConfig(requestConfig);
            }
            String response = "";
            Exception logE = null;
            try {
                if (prop.isHttpsRequest()) {
                    response = httpClientService.doHttpsPostHlzs(httpPost, "UTF-8");
                } else {
                    response = httpClientService.doPost(httpPost, "UTF-8");
                }
            } catch (IOException e) {
                logE = e;
                LOGGER.error("httpPost 请求异常：url:{},reqMap:{}", requestUrl, JSONObject.toJSONString(requestBody), e);
                throw new AppException("httpPost 请求异常");
            } finally {
                try {
                    AuditEventBO auditEventBO = new AuditEventBO(prop, builder);
                    auditEventBO.setRequest(JSONObject.toJSONString(requestBody));
                    auditEventBO.setResponse(response);
                    auditEventBO.setException(logE);
                    super.saveAuditLog(auditEventBO);
                } catch (Exception e) {
                    LOGGER.error("记录请求日志异常", e);
                }
            }
//            response = testResponse(requestParamMap,builder);
            super.setResponseBody(response, builder);
        }
    }

    /**
     * 处理外部接口返回体转化为exchange内部的返回体,由子类实现
     *
     * @param response
     * @return
     */
    @Override
    public String dealWithResponse(String response) {return response;}

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param requestParamMap, builder
     * @return java.lang.String
     * @description 测试数据
     */
    private static String testResponse(Map<String,Object> requestParamMap,InterfaceRequestBuilder builder){
        String beanId = builder.getExchangeBean().getId();
        // 省级平台测试数据
        String response = "{\n" +
                "  \"head\": {\n" +
                "    \"msg\": \"安全token错误\",\n" +
                "    \"code\": \"2002\"\n" +
                "  },\n" +
                "  \"data\": {}\n" +
                "}\n" +
                " ";
        if(requestParamMap.get("gxData") != null){
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(requestParamMap.get("gxData")));
            JSONObject head = jsonObject.getJSONObject("head");
            if(head != null && StringUtils.equals("45678901",head.getString("token"))){
                response = "{\n" +
                        "   \"data\": {\"cxjg\": [\n" +
                        "            {\n" +
                        "          \t\t\"xm\": \"张三\",\n" +
                        "         \t\t\"yhzgx\": \"母亲\",\n" +
                        "         \t\t\"zjhm\": \"320723197507121111\"\n" +
                        "      \t\t},\n" +
                        "            {\n" +
                        "         \t\t\"xm\": \"李四\",\n" +
                        "         \t\t\"yhzgx\": \"妻子\",\n" +
                        "         \t\t\"zjhm\": \"320723199003192222\"\n" +
                        "      \t\t},\n" +
                        "            {\n" +
                        "         \t\t\"xm\": \"王五\",\n" +
                        "         \t\t\"yhzgx\": \"女儿\",\n" +
                        "         \t\t\"zjhm\": \"320723199512263333\"\n" +
                        "      \t\t}\n" +
                        "   \t]},\n" +
                        "   \"head\":{\n" +
                        "      \"code\": \"0000\",\n" +
                        "      \"msg\": \"查询成功\"\n" +
                        "   }\n" +
                        "}";
            }
        }
        if(builder.getExchangeBean().getId().equals("sjpt_token")){
            response = "{\n" +
                    "\"head\": {\n" +
                    "\"code\": \"0000\",\n" +
                    "\"msg\": \"success\"\n" +
                    "},\n" +
                    "\"data\":{\n" +
                    "\"token\": \"45678901\"\n" +
                    "}\n" +
                    "}";
        }
//        response = "{\n" +
//                "  \"result\": \"true\",\n" +
//                "  \"resultText\": \"可以过户\",\n" +
//                "  \"username\": \"刘德华\",\n" +
//                "  \"usersfid\": \"340100190010100021\",\n" +
//                "  \"daikou\": \"\",\n" +
//                "  \"address\": \"地址\",\n" +
//                "  \"qianfei\": \"\",\n" +
//                "  \"zhanghuyue\": \"\",\n" +
//                "  \"lastchaobiaoDate\": \"\",\n" +
//                "  \"lastBjs\": \"\"\n" +
//                "}";

        if("jfpt_tsdjfxx".equals(beanId)){
            response = "{\"data\":{\"mpayCode\":\"34000019090000033396\",\"prePayRecordList\":[{\"adminDiv\":\"黄山市\",\"billAmount\":\"0.01\",\"billNo\":\"201908121712010000000235\",\"createTime\":\"20190806190048\",\"exempt\":\"0.00\",\"itemCode\":\"3401031811270000055\",\"itemName\":\"黄山缴费测试申请无线电频率招标、拍卖-刘贵02\",\"itemNum\":\"1\",\"money\":\"0.01\",\"overdueFine\":\"0.00\",\"payAreaCode\":\"0000000018\",\"payAreaName\":\"黄山市财政局\",\"payDeptCode\":\"06502\",\"payDeptName\":\"黄山市市政工程管理处\",\"payItemCode\":\"07613102\",\"payItemName\":\"城市道路占用费\",\"payor\":\"黄山缴费测试_刘贵\",\"payorIdNum\":\"340824199507016014\",\"pinCode\":\"34100019090000033418\",\"sourceId\":\"b11aad66443d43fda87fbdac51dda6ee\",\"stdFee\":\"0.01\",\"sysId\":\"hf0551\",\"sysSecId\":\"D812641AEDE35E51A150F2C2FDA60026\"},{\"adminDiv\":\"黄山市\",\"billAmount\":\"0.01\",\"billNo\":\"201908121712010000000368\",\"createTime\":\"20190806190048\",\"exempt\":\"0.00\",\"itemCode\":\"3401031811270000044\",\"itemName\":\"黄山缴费测试申请无线电频率招标、拍卖-刘贵03\",\"itemNum\":\"1\",\"money\":\"0.01\",\"overdueFine\":\"0.00\",\"payAreaCode\":\"0000000018\",\"payAreaName\":\"黄山市财政局\",\"payDeptCode\":\"06502\",\"payDeptName\":\"黄山市市政工程管理处\",\"payItemCode\":\"07613102\",\"payItemName\":\"城市道路占用费\",\"payor\":\"黄山缴费测试_刘贵\",\"payorIdNum\":\"340824199507016014\",\"pinCode\":\"34100019090000033405\",\"sourceId\":\"b11aad66443d43fda87fbdac51dda6dd\",\"stdFee\":\"0.01\",\"sysId\":\"hf0551\",\"sysSecId\":\"D812641AEDE35E51A150F2C2FDA60026\"}]},\"msg\":\"success\",\"status\":true}";
        }
        if("jfpt_jfztcx".equals(beanId)){
            response = "{\n" +
                    "    \"data\": {\n" +
                    "        \"paymentStatus\": \"01\",\n" +
                    "        \"transactionNo\": \"ZWJF1810170000000552\"\n" +
                    "    },\n" +
                    "    \"msg\": \"success\",\n" +
                    "    \"status\": true\n" +
                    "}";
        }
        if("emsddjr".equals(beanId)){
            response = "{\"response\":[{\"mailNo\":\"1103311486731\",\"success\":\"T\",\"txLogisticID\":\"BZL6207485221738977\"}],\"success\":\"T\"}";
        }
        if ("emsddjr".equals(beanId)) {
            response = "{\"response\":[{\"mailNo\":\"1103311486731\",\"success\":\"T\",\"txLogisticID\":\"BZL6207485221738977\"}],\"success\":\"T\"}";
        }
        if ("tsSwxxClf_dk".equals(beanId) || "tsSwxxSpf_dk".equals(beanId)) {
            response = "{\n" +
                    "\"result\": {\n" +
                    "          \"head\": {\n" +
                    "            \"channel_id\": \"AH.TAX.BDCJY.XX\",\n" +
                    "\"tran_id\": \"TAX.INTEGRATE.JY.SECONDHOUSEINFO\",\n" +
                    "\"tran_date\": \"20191227\",\n" +
                    "            \"tran_seq\": \"9A5A1B5B65994B4A97A9FDA52659B358\",\n" +
                    "            \"rtn_code\": \"200\",\n" +
                    "            \"rtn_msg\": \"存量房信息采集保存成功！\"\n" +
                    "          },\n" +
                    "\"body\": {\n" +
                    "\t           \"fwuuid\": \"d889bb7237594c3386fb28bcdd0fd245\",\n" +
                    "\"BDCDYH\": \"3416000151911220053768\",\n" +
                    "\t           \"htbh\": \"FWR40405333033\",\n" +
                    "\"sldh\": \"FWR40405333033\"\n" +
                    "}\n" +
                    "    }\n" +
                    "}";
        }
        return response;
    }
}
