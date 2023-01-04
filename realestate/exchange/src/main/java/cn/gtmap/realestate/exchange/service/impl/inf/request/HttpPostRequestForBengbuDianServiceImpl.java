package cn.gtmap.realestate.exchange.service.impl.inf.request;

import cn.gtmap.realestate.common.core.enums.ShijiInterfaceEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.HttpClientService;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.exchange.core.bo.log.AuditEventBO;
import cn.gtmap.realestate.exchange.core.bo.reqProp.HttpReqPropBO;
import cn.gtmap.realestate.exchange.service.inf.build.InterfaceRequestBuilder;
import cn.gtmap.realestate.exchange.service.inf.request.InterfaceRequestService;
import cn.gtmap.realestate.exchange.util.DianDESUtil;
import cn.gtmap.realestate.exchange.util.CommonUtil;
import cn.gtmap.realestate.exchange.util.SecurityUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0  2022年1月12日
 * @description
 */
@Service(value = "httpPostForBengbuDian")
public class HttpPostRequestForBengbuDianServiceImpl extends InterfaceRequestService<HttpReqPropBO> {

    @Autowired
    private HttpClientService httpClientService;

    /**
     * 分配的appId
     */
    @Value("${bengbu.dian.appId:}")
    private String appid;

    /**
     * 私钥
     */
    @Value("${bengbu.dian.secret:}")
    private String secret;

    /**
     * RSA公钥
     */
    @Value("${bengbu.dian.rasPublicKey:}")
    private String rasPublicKey;

    private static String[] HEADER_PARAMS = {"appid", "secret", "rasPublicKey"};

    @Override
    public void sendRequest(InterfaceRequestBuilder builder) {
        Object requestBody = builder.getRequestBody();
        LOGGER.info("蚌埠国网电接口请求requestBody:" + JSONObject.toJSONString(requestBody));
        HttpReqPropBO prop = super.getRequestPropFromBuilder(builder);
        //加密Util

        DianDESUtil desUtil = new DianDESUtil();

        if (CheckParameter.checkAnyParameter(requestBody) && StringUtils.isNotBlank(prop.getUrl())) {
            // 处理请求头和请求体参数
            Map<String, Object> requestParamMap = CommonUtil.objectToMap(requestBody);
            LOGGER.info("requestParamMap为：" + JSONObject.toJSONString(requestParamMap));

            Map<String, Object> headMap = new HashMap<>();
            Map<String, Object> paramMap = new HashMap<>();
            if (CollectionUtils.isNotEmpty(requestParamMap.keySet())) {
                for (String key : requestParamMap.keySet()) {
                    if (Arrays.asList(HEADER_PARAMS).contains(key)) {
                        headMap.put(key, requestParamMap.get(key));
                    } else {
                        try {
                            paramMap.put(key, desUtil.encrypt((String) requestParamMap.get(key)));
                        } catch (UnsupportedEncodingException e) {
                            LOGGER.info("蚌埠国网电接口请求参数加密失败！:{}", e);
                            e.printStackTrace();
                        }
                    }
                }
            }
            //组织headmap
            long time = System.currentTimeMillis();
            String nonce = RandomStringUtils.randomNumeric(10);
            //随机生成16位AES秘钥
            String aesSecret = RandomStringUtils.randomNumeric(16);
            // 对AES秘钥做RAS加密
            String encodeAesSecret = SecurityUtil.rsaPublicEncrypt(aesSecret, rasPublicKey);
            // 生成签名，这里可以将body一起拼接进来
            String sign = SecurityUtil.aes128Encrypt(appid + ":" + secret + ":" + time + ":" + nonce + ":" + encodeAesSecret, aesSecret);
            // header放入鉴权信息

           /* String body = JSONObject.toJSONString(paramMap);
            String encrypt = SecurityUtil.aes128Encrypt(body, aesSecret);*/

            LOGGER.info("蚌埠国网电paramMap：" + JSONObject.toJSONString(paramMap));
            String response = "";
            Exception logE = null;
            //设置Content-Type，根据接口区分
            String contentType = "";

            HttpPost httpPost = new HttpPost(prop.getUrl());
            httpPost.setHeader("time", String.valueOf(time));
            httpPost.setHeader("appId", appid);
            httpPost.setHeader("encodeSecretKey", encodeAesSecret);
            httpPost.setHeader("nonce", nonce);
            httpPost.setHeader("sign", sign);
            if ("json".equals(prop.getContentType())) {
                StringEntity entity = new StringEntity(JSONObject.toJSONString(paramMap), ContentType.create("text/json", "UTF-8"));
                httpPost.addHeader("Content-Type", "application/json; charset=utf-8");
                httpPost.setEntity(entity);
            } else {
                //formData
                ContentType strContent = ContentType.create("text/plain", Charset.forName("UTF-8"));
                MultipartEntityBuilder reqEntity = MultipartEntityBuilder.create();
                //参数填充
                for (String key : paramMap.keySet()) {
                    reqEntity.addTextBody(key, (String) paramMap.get(key), strContent);
                }
                HttpEntity entity = reqEntity.build();
                httpPost.setEntity(entity);
            }
            try {
                LOGGER.info("蚌埠国网电开始执行忽略证书http请求，方法为：{}", httpPost.getMethod());
                RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();
                httpPost.setConfig(requestConfig);
                response = httpClientService.doPost(httpPost, "UTF-8");
                LOGGER.info("蚌埠国网电response：{}", response);
            } catch (Exception e) {
                logE = e;
                LOGGER.error("蚌埠国网电httpPost 请求异常：url:{},reqMap:{}", prop.getUrl(), JSONObject.toJSONString(requestBody), e);
                throw new AppException("蚌埠国网电httpPost 请求异常");
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
            super.setResponseBody(response, builder);
        }
    }

    private static Map<String, String> provinceErrorMsgMap = new HashMap<>(4);

    static {
        provinceErrorMsgMap.put(ShijiInterfaceEnum.PROVICE_AGENCY_INTERFACE_RESPONSE_ERROR.getCode(), ShijiInterfaceEnum.PROVICE_AGENCY_INTERFACE_RESPONSE_ERROR.getMsg());
        provinceErrorMsgMap.put(ShijiInterfaceEnum.PROVICE_AGENCY_INTERFACE_RESPONSE_ERROR_WITH_PARAM.getCode(), ShijiInterfaceEnum.PROVICE_AGENCY_INTERFACE_RESPONSE_ERROR_WITH_PARAM.getMsg());
        provinceErrorMsgMap.put(ShijiInterfaceEnum.PROVICE_AGENCY_INTERFACE_RESPONSE_UNKONW_ERROR.getCode(), ShijiInterfaceEnum.PROVICE_AGENCY_INTERFACE_RESPONSE_UNKONW_ERROR.getMsg());
    }

    /**
     * 处理外部接口返回体转化为exchange内部的返回体,由子类实现
     *
     * @param response
     * @return
     */
    @Override
    public String dealWithResponse(String response) {
        JSONObject errorJson = new JSONObject(4);
        //盐城市级接口返回参数处理
        try {
            JSONObject responseJson = JSON.parseObject(response);
            if (responseJson != null && responseJson.containsKey(ShijiInterfaceEnum.PROVICE_AGENCY_INTERFACE_RESPONSE_SUCCESS.getCodeName())) {
                if (!ShijiInterfaceEnum.PROVICE_AGENCY_INTERFACE_RESPONSE_SUCCESS.getCode().equals(responseJson.getString(ShijiInterfaceEnum.PROVICE_AGENCY_INTERFACE_RESPONSE_SUCCESS.getCodeName()))) {
                    return JSON.toJSONString(initProvinceErrorResponse(errorJson, responseJson));
                } else {
                    responseJson.put(ShijiInterfaceEnum.PROVICE_AGENCY_INTERFACE_RESPONSE_SUCCESS.getCodeName(), "200");
                    return JSON.toJSONString(responseJson);
                }
            } else if (responseJson != null && responseJson.containsKey(ShijiInterfaceEnum.CITY_INTERFACE_RESPONSE_SUCCESS.getCodeName())) {
                if (responseJson.containsKey("body")) {
                    if (responseJson.getJSONObject("body") != null && !responseJson.getJSONObject("body").containsKey("access_token") && responseJson.getJSONObject("body").containsKey(ShijiInterfaceEnum.CITY_INTERFACE_RESPONSE_SUCCESS.getCodeName())) {
                        if (!"0".equals(responseJson.getJSONObject("body").getString(ShijiInterfaceEnum.CITY_INTERFACE_RESPONSE_SUCCESS.getCodeName()))) {
                            JSONObject tempJson = initCityErrorResponse(errorJson, responseJson.getJSONObject("body"));
                            JSONObject targetJson = new JSONObject();
                            targetJson.put("body", tempJson);
                            return JSON.toJSONString(targetJson);
                        } else {
                            JSONObject body = responseJson.getJSONObject("body");
                            body.put("code", "200");
                            JSONObject targetJson = new JSONObject();
                            targetJson.put("body", body);
//                            LOGGER.info("=============>response：{}" , targetJson.toJSONString());
                            return JSON.toJSONString(targetJson);
                        }
                    }
                } else {
                    if (!ShijiInterfaceEnum.CITY_INTERFACE_RESPONSE_SUCCESS.getCode().equals(responseJson.getString(ShijiInterfaceEnum.CITY_INTERFACE_RESPONSE_SUCCESS.getCodeName()))) {
                        return JSON.toJSONString(initCityErrorResponse(errorJson, responseJson));
                    }
                }
            } else if (responseJson != null && responseJson.containsKey("data") && !responseJson.containsKey("status") && !responseJson.containsKey("code")) {
                if (responseJson.getJSONObject("data") != null && responseJson.getJSONObject("data").containsKey(ShijiInterfaceEnum.PROVICE_AGENCY_INTERFACE_RESPONSE_SUCCESS.getCodeName())) {
                    if (!ShijiInterfaceEnum.PROVICE_AGENCY_INTERFACE_RESPONSE_SUCCESS.getCode().equals(responseJson.getJSONObject("data").getString(ShijiInterfaceEnum.PROVICE_AGENCY_INTERFACE_RESPONSE_SUCCESS.getCodeName()))) {
                        JSONObject tempJson = initProvinceErrorResponse(errorJson, responseJson.getJSONObject("data"));
                        JSONObject targetJson = new JSONObject();
                        targetJson.put("data", tempJson);
                        return JSON.toJSONString(targetJson);
                    } else {
                        responseJson.getJSONObject("data").put(ShijiInterfaceEnum.PROVICE_AGENCY_INTERFACE_RESPONSE_SUCCESS.getCodeName(), "200");
                        return JSON.toJSONString(responseJson);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("处理市级接口异常情况失败", e);
            LOGGER.info("处理市级接口异常情况:{}", response);
            return response;
        }
        return response;
    }

    private JSONObject initCityErrorResponse(JSONObject errorJson, JSONObject responseJson) {
        errorJson.put(ShijiInterfaceEnum.CITY_INTERFACE_RESPONSE_ERROR.getCodeName(), responseJson.getString(ShijiInterfaceEnum.CITY_INTERFACE_RESPONSE_ERROR.getCodeName()));
        if (StringUtils.isBlank(responseJson.getString(ShijiInterfaceEnum.CITY_INTERFACE_RESPONSE_ERROR.getMsgName()))) {
            errorJson.put(ShijiInterfaceEnum.CITY_INTERFACE_RESPONSE_ERROR.getMsgName(), ShijiInterfaceEnum.CITY_INTERFACE_RESPONSE_ERROR.getMsg());
            errorJson.put("data", null);
        } else {
            errorJson.put(ShijiInterfaceEnum.CITY_INTERFACE_RESPONSE_ERROR.getMsgName(), responseJson.getString(ShijiInterfaceEnum.CITY_INTERFACE_RESPONSE_ERROR.getMsgName()));
            errorJson.put("data", null);
        }
        return errorJson;
    }

    private JSONObject initProvinceErrorResponse(JSONObject errorJson, JSONObject responseJson) {
        errorJson.put(ShijiInterfaceEnum.PROVICE_AGENCY_INTERFACE_RESPONSE_ERROR.getCodeName(), responseJson.getString(ShijiInterfaceEnum.PROVICE_AGENCY_INTERFACE_RESPONSE_ERROR.getCodeName()));
        if (StringUtils.isBlank(responseJson.getString(ShijiInterfaceEnum.PROVICE_AGENCY_INTERFACE_RESPONSE_ERROR.getMsgName()))) {
            errorJson.put(ShijiInterfaceEnum.PROVICE_AGENCY_INTERFACE_RESPONSE_ERROR.getMsgName(), provinceErrorMsgMap.get(responseJson.getString(ShijiInterfaceEnum.PROVICE_AGENCY_INTERFACE_RESPONSE_ERROR.getCodeName())));
//            errorJson.put("data", new JSONArray());
        } else {
//            errorJson.put("data", new JSONArray());
            errorJson.put(ShijiInterfaceEnum.PROVICE_AGENCY_INTERFACE_RESPONSE_ERROR.getMsgName(), responseJson.getString(ShijiInterfaceEnum.PROVICE_AGENCY_INTERFACE_RESPONSE_ERROR.getMsgName()));
        }
        return errorJson;
    }

}

