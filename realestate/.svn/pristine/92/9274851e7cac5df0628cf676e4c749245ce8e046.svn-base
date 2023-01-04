package cn.gtmap.realestate.exchange.service.impl.inf.request;

import cn.gtmap.realestate.common.core.enums.ShijiInterfaceEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.HttpClientService;
import cn.gtmap.realestate.exchange.core.bo.log.AuditEventBO;
import cn.gtmap.realestate.exchange.core.bo.reqProp.HttpReqPropBO;
import cn.gtmap.realestate.exchange.service.inf.build.InterfaceRequestBuilder;
import cn.gtmap.realestate.exchange.service.inf.request.InterfaceRequestService;
import cn.gtmap.realestate.exchange.util.Codecs;
import cn.gtmap.realestate.exchange.util.DESUtil;
import cn.gtmap.realestate.exchange.util.DateUtil;
import cn.gtmap.realestate.exchange.util.Sha1Util;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @version 1.0  2022/08/15
 * @description 淮安供水接口
 */
@Service(value = "httpPostForHuaianShui")
public class HttpPostRequestForHuaianShuiServiceImpl extends InterfaceRequestService<HttpReqPropBO> {

    @Autowired
    private HttpClientService httpClientService;

    private static final String ENCRYPT_PASSWORD = "12345678901234567890123456789012";
    private static final String DES_PASSWORD = "123456789012345678901234";

    /**
     * 分配的appId
     */
    @Value("${huaian.shui.appid:}")
    private String appId;

    /**
     * 私钥
     */
    @Value("${huaian.shui.secret:}")
    private String appSecret;

    @Override
    public void sendRequest(InterfaceRequestBuilder builder) {
        Object requestBody = builder.getRequestBody();
        LOGGER.info("淮安供水接口请求requestBody:" + JSONObject.toJSONString(requestBody));
        // 原始请求数据：{"UserID":12345}
        String requestStr = JSONObject.toJSONString(requestBody);
        LOGGER.info("请求参数：" + requestStr);
        HttpReqPropBO prop = super.getRequestPropFromBuilder(builder);
        LOGGER.info("拿到的URL配置是：{}",prop.getUrl());

        if (StringUtils.isNotBlank(prop.getUrl())) {
            // sha1加密
            String sha1EncryptStr = null;
            try {
                sha1EncryptStr = Sha1Util.HmacSHA1Encrypt(requestStr, ENCRYPT_PASSWORD);
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("淮安供水sha1加密失败，失败信息：{}", e);
            }
            sha1EncryptStr = "00000000" + sha1EncryptStr + requestStr;
            // des加密
            String encryptStr = DESUtil.encrypt(DES_PASSWORD, sha1EncryptStr);

            // 请求header
            HttpPost httpPost = new HttpPost(prop.getUrl());
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-Type", "application/json; charset=UTF-8");
            String timestamp = DateUtil.formateTimeYmdhms(new Date());
            httpPost.setHeader("Timestamp", timestamp);
            String AccessToken = Codecs.md5Hex(appId + appSecret + timestamp);
            String Authorization = Base64.encodeBase64String((appId + ":" + timestamp).getBytes());
            httpPost.setHeader("AccessToken", AccessToken);
            httpPost.setHeader("Authorization", Authorization);

            // post请求，设置请求体
            StringEntity entity = new StringEntity(encryptStr, ContentType.create("text/json", "UTF-8"));
            httpPost.setEntity(entity);
            String response = "";
            Exception logE = null;
            try {
                LOGGER.info("淮安供水http请求，方法为：{}", httpPost.getMethod());
                RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();
                httpPost.setConfig(requestConfig);
                response = httpClientService.doPost(httpPost, "UTF-8");
                LOGGER.info("淮安供水response：{}", response);
                // 解密
                if (StringUtils.isNotBlank(response)) {
                    // des解密
                    String decryptStr = DESUtil.decrypt(DES_PASSWORD, response);
                    decryptStr = StringUtils.substring(decryptStr, 8, decryptStr.length());
                    String sha1EncryptReStr = StringUtils.substring(decryptStr, 0, 40);
                    String resultStr = StringUtils.substring(decryptStr, 40, decryptStr.length());
                    // sha1解密
                    if (StringUtils.equals(sha1EncryptReStr, Sha1Util.HmacSHA1Encrypt(resultStr, ENCRYPT_PASSWORD))) {
                        response = requestStr;
                    }
                    LOGGER.error("查询自来水信息：验签失败！");
                }


            } catch (Exception e) {
                logE = e;
                LOGGER.error("淮安供水httpPost 请求异常：url:{},reqMap:{}", prop.getUrl(), JSONObject.toJSONString(requestBody), e);
                throw new AppException("淮安供水httpPost 请求异常");
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

