package cn.gtmap.realestate.exchange.service.impl.inf.request;

import cn.gtmap.realestate.common.core.enums.ShijiInterfaceEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.HttpClientService;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.exchange.core.bo.log.AuditEventBO;
import cn.gtmap.realestate.exchange.core.bo.reqProp.HttpReqPropBO;
import cn.gtmap.realestate.exchange.service.impl.inf.yancheng.ShijgxServiceImpl;
import cn.gtmap.realestate.exchange.service.inf.build.InterfaceRequestBuilder;
import cn.gtmap.realestate.exchange.service.inf.request.InterfaceRequestService;
import cn.gtmap.realestate.exchange.util.CommonUtil;
import cn.gtmap.realestate.exchange.web.rest.ExchangeInterfaceRestController;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
 * @version 1.0  2021/5/10 10:20
 * @description
 */
@Service(value = "httpGetForShij")
public class HttpGetRequestForShijServiceImpl extends InterfaceRequestService<HttpReqPropBO> {

    private static String[] HEADER_PARAMS = {"gateway_appid", "gateway_sig", "gateway_rtime"};

    @Autowired
    private ShijgxServiceImpl shijgxService;

    @Value("${shijgx.appid:}")
    private String appid;

    @Override
    public void sendRequest(InterfaceRequestBuilder builder) {
        Object requestBody = builder.getRequestBody();
        LOGGER.info("请求requestBody:" + JSONObject.toJSONString(requestBody));
        HttpReqPropBO prop = super.getRequestPropFromBuilder(builder);
        if (prop.isParamRequired() && !CheckParameter.checkAnyParameter(requestBody)) {
            return;
        }
        // 市级共享接口不采用UseNameValuePair方式，也不采用ParamClass方式拼接参数，这两部分先删除，以使代码更简洁清晰
        // 后续如有需要，可参考HttpGetRequestServiceImpl中这两种方式再补充进来
        if (StringUtils.isNotBlank(prop.getUrl())) {
            // 处理请求头和请求体参数o
            Map<String, Object> requestParamMap = CommonUtil.objectToMap(requestBody);
            LOGGER.info("requestParamMap为：" + JSONObject.toJSONString(requestParamMap));

            Map<String, Object> headMap = new HashMap<>();
            Map<String, Object> paramMap = new HashMap<>();
            if (CollectionUtils.isNotEmpty(requestParamMap.keySet())) {
                for (String key : requestParamMap.keySet()) {
                    if (Arrays.asList(HEADER_PARAMS).contains(key)) {
                        headMap.put(key, requestParamMap.get(key));
                    } else {
                        paramMap.put(key, requestParamMap.get(key));
                    }
                }
            }
            headMap.put("gateway_appid", appid);
            if (!headMap.containsKey("gateway_rtime") || !headMap.containsKey("gateway_sig")) {
                headMap.put("gateway_appid", appid);
                Object bean = Container.getBean(ExchangeInterfaceRestController.class);
                if (bean != null) {
                    ExchangeInterfaceRestController restController = (ExchangeInterfaceRestController) bean;
                    Map<String, String> tokenParam = new HashMap<>();
                    tokenParam.put("rtime", Long.toString(System.currentTimeMillis()));
                    String gatewaySig = shijgxService.getSign(tokenParam);
                    if (StringUtils.isNotBlank(gatewaySig)) {
                        headMap.put("gateway_sig", gatewaySig);
                    }
                    String gatewayRtime = shijgxService.getRtime(tokenParam);
                    if (gatewayRtime != null) {
                        headMap.put("gateway_rtime", gatewayRtime);
                    }
                }
            }
            LOGGER.info("headMap：" + JSONObject.toJSONString(headMap));
            LOGGER.info("paramMap：" + JSONObject.toJSONString(paramMap));

            String requestUrl = prop.getUrl();
            String urlParam = CommonUtil.mapToUrlParam(paramMap);

            HttpGet httpGet = new HttpGet(requestUrl + "?" + urlParam);

//            if (StringUtils.isNotBlank(prop.getContentType())) {
//                httpGet.setHeader("Content-Type", prop.getContentType());
//            } else {
//                httpGet.setHeader("Content-Type", "application/json; charset=UTF-8");
//            }
//            if (StringUtils.isNotBlank(prop.getxPlatToken())) {
//                httpGet.setHeader("X-PLAT-TOKEN", prop.getxPlatToken());
//            }
//            httpGet.setHeader("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
//            httpGet.setHeader("unitCode", "LandResourcesBureau");
//            if (StringUtils.isNotBlank(prop.getAppKey())) {
//                httpGet.setHeader("appKey", prop.getAppKey());
//            }
//            if (CollectionUtils.isNotEmpty(headMap.keySet())) {
//                httpGet.setHeader("gateway_appid", String.valueOf(headMap.get("gateway_appid")));
//                httpGet.setHeader("gateway_rtime", String.valueOf(headMap.get("gateway_rtime")));
//                httpGet.setHeader("gateway_sig", String.valueOf(headMap.get("gateway_sig")));
//            }
            String response = "";
            Exception logE = null;
            try {
                response = HttpRequest.get(requestUrl + "?" + urlParam)
                        .header("Content-Type", StringUtils.isNotBlank(prop.getContentType())?prop.getContentType():"application/json")
                        .header("gateway_appid", String.valueOf(headMap.get("gateway_appid")))
                        .header("gateway_rtime", String.valueOf(headMap.get("gateway_rtime")))
                        .header("gateway_sig", String.valueOf(headMap.get("gateway_sig")))
                        .execute().body();
                LOGGER.info("市级接口response：{}" , response);
//                response=testResponse(builder.getExchangeBean().getId());
            } catch (Exception e) {
                logE = e;
                LOGGER.error("httpGet 请求异常：url:{},reqMap:{}", requestUrl, JSONObject.toJSONString(requestBody), e);
                throw new AppException("httpGet 请求异常");
            } finally {
                // 记录 请求日志
                AuditEventBO auditEventBO = new AuditEventBO(prop, builder);
                auditEventBO.setRequest(JSONObject.toJSONString(requestParamMap));
                auditEventBO.setResponse(response);
                auditEventBO.setException(logE);
                super.saveAuditLog(auditEventBO);
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
                        }else {
                            JSONObject body = responseJson.getJSONObject("body");
                            body.put("code","200");
                            JSONObject targetJson = new JSONObject();
                            targetJson.put("body", body);
//                            LOGGER.info("=============>response：{}" , targetJson.toJSONString());
                            return JSON.toJSONString(targetJson);
                        }
                    }
                }else {
                    if (!ShijiInterfaceEnum.CITY_INTERFACE_RESPONSE_SUCCESS.getCode().equals(responseJson.getString(ShijiInterfaceEnum.CITY_INTERFACE_RESPONSE_SUCCESS.getCodeName()))) {
                        return JSON.toJSONString(initCityErrorResponse(errorJson, responseJson));
                    }
                }
            }  else if (responseJson != null && responseJson.containsKey("data") && !responseJson.containsKey("status") && !responseJson.containsKey("code")) {
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


    private String testResponse(String beanid) {
        String response = "";
        if (StringUtils.equals(beanid, "shijjk_shengcsyxzm")) {
            response = "{" +
                    "    \"code\":200," +
                    "    \"message\":\"操作成功.\"," +
                    "    \"body\":{" +
                    "        \"code\":0," +
                    "        \"message\":\"操作成功\"," +
                    "        \"data\":[" +
                    "            {" +
                    "                \"BIRTH_NO\":\"32……9\",\n" +
                    "                \"M_AGE\":26,\n" +
                    "                \"F_IDCTYPE\":\"10\",\n" +
                    "                \"GESTATION_WEEK\":40,\n" +
                    "                \"DT\":\"2020-02-08T21:22:41.000+0000\",\n" +
                    "                \"F_ADR\":\"/\",\n" +
                    "                \"C_STATUS\":null,\n" +
                    "                \"M_NATIONNAME\":\"中国\",\n" +
                    "                \"F_NATIONNAME\":\"中国\",\n" +
                    "                \"GESTATION_DAY\":4,\n" +
                    "                \"ISSUE_PPNAME\":\"邓……\",\n" +
                    "                \"NOTE\":null,\n" +
                    "                \"PLACE_COUNTRYNAME\":\"射阳\",\n" +
                    "                \"M_ICCARD\":\"32……00\",\n" +
                    "                \"BIRTH_DATE\":\"2015-12-20T14:32:00.000+0000\",\n" +
                    "                \"MED_INSTITUTIONS\":\"……卫生院\",\n" +
                    "                \"ISSUE_DATE\":\"2016-01-14T16:00:00.000+0000\",\n" +
                    "                \"WEIGHT\":3500,\n" +
                    "                \"PLACE_CITYNAME\":\"盐城市\",\n" +
                    "                \"F_AGE\":26,\n" +
                    "                \"F_ICCARD\":\"32……75\",\n" +
                    "                \"F_ETHNICNAME\":\"汉族\",\n" +
                    "                \"M_ETHNICNAME\":\"汉族\",\n" +
                    "                \"sptrk_update_time\":\"2020-12-17T02:02:30.000+0000\",\n" +
                    "                \"BABY_SEX\":\"1\",\n" +
                    "                \"HEIGHT\":50,\n" +
                    "                \"ISSUE_STNAME\":\"射阳县妇幼保健所\",\n" +
                    "                \"F_IDCOTHER\":null,\n" +
                    "                \"M_IDCOTHER\":null,\n" +
                    "                \"M_ADR\":\"江苏省淮安市\",\n" +
                    "                \"F_NAME\":\"茆……\",\n" +
                    "                \"M_NAME\":\"陈……\",\n" +
                    "                \"M_IDCTYPE\":\"10\",\n" +
                    "                \"PLACE_PROVINCENAME\":\"江苏\",\n" +
                    "                \"BABY_NAME\":\"茆……\"\n" +
                    "            }\n" +
                    "        ],\n" +
                    "        \"messageId\":null\n" +
                    "    }\n" +
                    "}";
        } else if (StringUtils.equals(beanid, "shijjk_jzgcjgysba")) {
            response = "{\n" +
                    "    \"code\":200,\n" +
                    "    \"message\":\"操作成功.\",\n" +
                    "    \"body\":{\n" +
                    "        \"code\":0,\n" +
                    "        \"message\":\"操作成功\",\n" +
                    "        \"data\":[\n" +
                    "            {\n" +
                    "                \"fzjg\":\"盐城市住房和城乡建设局\",\n" +
                    "                \"sqdwmc\":\"盐城……公司\",\n" +
                    "                \"sqdwdz\":\"盐城……中心A11\",\n" +
                    "                \"baxmxxmc\":\"文华……楼\",\n" +
                    "                \"bah\":\"盐……号\",\n" +
                    "                \"cd_operation\":\"i\",\n" +
                    "                \"barq\":null,\n" +
                    "                \"bamj\":\"16217\",\n" +
                    "                \"cd_bach\":\"\",\n" +
                    "                \"cd_time\":\"2021-02-06T01:35:42.000+08:00\",\n" +
                    "                \"RKSJ\":\"2021-02-06T01:35:42.000+08:00\",\n" +
                    "                \"bz\":null,\n" +
                    "                \"sync_sign\":0,\n" +
                    "                \"fddbr\":\"……\",\n" +
                    "                \"UUID\":6,\n" +
                    "                \"dataSource\":null\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"fzjg\":\"盐城市住房和城乡建设局\",\n" +
                    "                \"sqdwmc\":\"盐城……公司\",\n" +
                    "                \"sqdwdz\":\"盐……中心\",\n" +
                    "                \"baxmxxmc\":\"文……楼\",\n" +
                    "                \"bah\":\"32……1\",\n" +
                    "                \"cd_operation\":\"i\",\n" +
                    "                \"barq\":\"2020-08-07\",\n" +
                    "                \"bamj\":\"地上：1508.0M2地下0M2\",\n" +
                    "                \"cd_bach\":\"\",\n" +
                    "                \"cd_time\":\"2021-02-06T01:35:42.000+08:00\",\n" +
                    "                \"RKSJ\":\"2021-02-06T01:35:42.000+08:00\",\n" +
                    "                \"bz\":null,\n" +
                    "                \"sync_sign\":0,\n" +
                    "                \"fddbr\":\"……\",\n" +
                    "                \"UUID\":142,\n" +
                    "                \"dataSource\":null\n" +
                    "            }\n" +
                    "        ],\n" +
                    "        \"messageId\":null\n" +
                    "    },\n" +
                    "    \"timestamp\":0\n" +
                    "}";
        }
        return response;
    }
}
