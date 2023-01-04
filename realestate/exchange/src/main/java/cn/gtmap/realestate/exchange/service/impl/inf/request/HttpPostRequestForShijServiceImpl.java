package cn.gtmap.realestate.exchange.service.impl.inf.request;

import cn.gtmap.realestate.common.core.enums.ShijiInterfaceEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
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
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
 * @version 1.0  2021/5/10 9:34
 * @description
 */
@Service(value = "httpPostForShij")
public class HttpPostRequestForShijServiceImpl extends InterfaceRequestService<HttpReqPropBO> {

    @Autowired
    private ShijgxServiceImpl shijgxService;

    @Value("${shijgx.appid:}")
    private String appid;
    @Value("${gxj.appid:}")
    private String gxjAppid;

    private static String[] HEADER_PARAMS = {"gateway_appid", "gateway_sig", "gateway_rtime"};

    @Override
    public void sendRequest(InterfaceRequestBuilder builder) {
        Object requestBody = builder.getRequestBody();
        LOGGER.info("请求requestBody:" + JSONObject.toJSONString(requestBody));
        HttpReqPropBO prop = super.getRequestPropFromBuilder(builder);
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
                        paramMap.put(key, requestParamMap.get(key));
                    }
                }
            }
            if (!headMap.containsKey("gateway_rtime") || !headMap.containsKey("gateway_sig")) {
                //资金监管分配新的id和key
                if ("zjjg_tsdbxx".equals(prop.getBeanId())) {
                    headMap.put("gateway_appid", gxjAppid);
                } else {
                    headMap.put("gateway_appid", appid);

                }
                Object bean = Container.getBean(ExchangeInterfaceRestController.class);
                if (bean != null) {
                    ExchangeInterfaceRestController restController = (ExchangeInterfaceRestController) bean;
                    Map<String, String> tokenParam = new HashMap<>();
                    tokenParam.put("rtime", Long.toString(System.currentTimeMillis()));
                    tokenParam.put("beanId", prop.getBeanId());
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
            String urlParam = CommonUtil.mapToUrlParam(paramMap);
            String response = "";
            Exception logE = null;
            try {
                if ("营业执照-获取证照信息获取和下载接口".equals(prop.getLogEventName())
                        || "身份证_证照获取和下载接口".equals(prop.getLogEventName())
                        || "婚姻登记_证照获取和下载接口".equals(prop.getLogEventName())
                        || "卫健委-死亡医学证明".equals(prop.getLogEventName())
                ) {
                    LOGGER.info("post-ulr+param,接口名：{},beanid{}", prop.getLogEventName(), prop.getBeanId());
                    response = HttpRequest.post(prop.getUrl() + "?" + urlParam)
                            .header("Content-Type", StringUtils.isNotBlank(prop.getContentType()) ? prop.getContentType() : "application/json")
                            .header("gateway_appid", String.valueOf(headMap.get("gateway_appid")))
                            .header("gateway_rtime", String.valueOf(headMap.get("gateway_rtime")))
                            .header("gateway_sig", String.valueOf(headMap.get("gateway_sig")))
//                        .body(JSON.toJSONString(paramMap))
                            .execute().body();
                } else {
                    LOGGER.info("post-url+body,接口名：{}", prop.getLogEventName());

                    response = HttpRequest.post(prop.getUrl())
                            .header("Content-Type", StringUtils.isNotBlank(prop.getContentType()) ? prop.getContentType() : "application/json")
                            .header("gateway_appid", String.valueOf(headMap.get("gateway_appid")))
                            .header("gateway_rtime", String.valueOf(headMap.get("gateway_rtime")))
                            .header("gateway_sig", String.valueOf(headMap.get("gateway_sig")))
                            .body(JSON.toJSONString(paramMap))
                            .execute().body();
                }


                LOGGER.info("市级接口response：{}", response);
            } catch (Exception e) {
                logE = e;
                LOGGER.error("httpPost 请求异常：url:{},reqMap:{}", prop.getUrl(), JSONObject.toJSONString(requestBody), e);
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

