package cn.gtmap.realestate.exchange.service.impl.inf.request;

import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.HttpClientService;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.exchange.config.TokenServiceChoose;
import cn.gtmap.realestate.exchange.core.bo.log.AuditEventBO;
import cn.gtmap.realestate.exchange.core.bo.reqProp.HttpPostJsonPropBO;
import cn.gtmap.realestate.exchange.service.inf.TokenService;
import cn.gtmap.realestate.exchange.service.inf.build.InterfaceRequestBuilder;
import cn.gtmap.realestate.exchange.service.inf.request.InterfaceRequestService;
import cn.gtmap.realestate.exchange.util.CommonUtil;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-09-24
 * @description
 */
@Service(value = "httpTokenPostJson")
public class HttpTokenPostJsonRequestServiceImpl extends InterfaceRequestService<HttpPostJsonPropBO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpTokenPostJsonRequestServiceImpl.class);

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
        HttpPostJsonPropBO prop = super.getRequestPropFromBuilder(builder);
        if (prop.isParamRequired() && !CheckParameter.checkAnyParameter(requestBody)) {
            return;
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
            if (StringUtils.isBlank(token)) {
                throw new AppException("获取token失败");
            }
            if (StringUtils.isNotBlank(prop.getTokenInterface()) && Constants.TOKEN.ZZQZ_TOKEN.equals(prop.getTokenInterface())) {
                requestUrl.append("?token=");
                requestUrl.append(token);
            }
            if (StringUtils.isNotBlank(prop.getTokenInterface()) && Constants.TOKEN.JT_ZZQZ_TOKEN.equals(prop.getTokenInterface())) {
                requestUrl.append("?token=");
                requestUrl.append(token);
            }
//            LOGGER.info("======wahaha======>{}",requestUrl.toString());
            HttpPost httpPost = new HttpPost(requestUrl.toString());
            if (StringUtils.isNotBlank(prop.getTokenInterface()) && Constants.TOKEN.SHUI_TOKEN.equals(prop.getTokenInterface())) {
                httpPost.setHeader("token", token);
            }
            if (StringUtils.isNotBlank(prop.getTokenInterface()) && Constants.TOKEN.ZZQZ_TOKEN.equals(prop.getTokenInterface())) {
                httpPost.setHeader("token", token);
            }
            if (StringUtils.isNotBlank(prop.getTokenInterface()) && Constants.TOKEN.JT_ZZQZ_TOKEN.equals(prop.getTokenInterface())) {
                httpPost.setHeader("token", token);
            }
            httpPost.setHeader("Content-Type", "application/json; charset=UTF-8");
            httpPost.setHeader("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
            httpPost.setHeader("unitCode", "LandResourcesBureau");
            if (StringUtils.isNotBlank(prop.getAppKey())) {
                httpPost.setHeader("appKey", prop.getAppKey());
            }
            String parameter = JSONObject.toJSONString(requestBody);
            // 判断是否需要加密
            if (StringUtils.isNotBlank(prop.getEncryptMethod())) {
                parameter = enOrDecrypt(prop.getEncryptMethod(), parameter);
            }
            StringEntity entity = new StringEntity(parameter, StandardCharsets.UTF_8);
            httpPost.setEntity(entity);
            if (StringUtils.isNotBlank(prop.getSoTimeout()) && NumberUtils.isNumber(prop.getSoTimeout())) {
                RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(NumberUtils.toInt(prop.getSoTimeout())).build();
                LOGGER.error("httpPost 请求url：{}，参数配置:{}", requestUrl, requestConfig.toString());
                httpPost.setConfig(requestConfig);
                LOGGER.info("http请求配置：{}",JSONObject.toJSONString(requestConfig));
            }
            String response = "";
            Exception logE = null;
            String paramLog = JSONObject.toJSONString(requestBody);
            if (StringUtils.isNotBlank(paramLog) && paramLog.length() <= 5000) {
                LOGGER.debug("httpPost beanId:{},param:{}", builder.getExchangeBean().getId(), JSONObject.toJSONString(requestBody));
            }
            try {
                LOGGER.info("httpTokenPostJson 开始请求：url:{},reqMap:{}", requestUrl, JSONObject.toJSONString(requestBody));
                response = httpClientService.doPost(httpPost, "UTF-8");
                LOGGER.info("httpTokenPostJson 请求结束：url:{},reqMap:{},返回值{}", requestUrl, JSONObject.toJSONString(requestBody), response);

                // 判断是否需要解密
                if (StringUtils.isNotBlank(prop.getDecryptMethod())) {
                    response = enOrDecrypt(prop.getDecryptMethod(), response);
                }
            } catch (Exception e) {
                logE = e;
                LOGGER.info("httpTokenPostJson 请求异常：url:{},reqMap:{}", requestUrl, JSONObject.toJSONString(requestBody), e);
                throw new AppException("httpTokenPostJson 请求异常");
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
//            String response = testResponse(builder);
            LOGGER.debug("httpPost response:{}", response);
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
     * @param methodProp, param
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 加密或解密方法
     */
    private static String enOrDecrypt(String methodProp, String param) {
        String resultParam = param;
        Object result = CommonUtil.executeStaticMethod(methodProp, param);
        if (result != null) {
            resultParam = result.toString();
        }
        return resultParam;
    }


    /**
     * @param builder
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 测试数据
     */
    private static String testResponse(InterfaceRequestBuilder builder) {
        String response = "";
        String beanId = builder.getExchangeBean().getId();
        if ("wwsq_ykqjfztcx".equals(beanId)) {
            response = "{\n" +
                    "\t\"head\": {\n" +
                    "\t\t\"regionCode\": \"地区编码\",\n" +
                    "\t\t\" orgid \": \"机构编码\",\n" +
                    "\t\t\" statusCode \": \"响应状态码\",\n" +
                    "\t\t\" msg \": \"响应信息\"\n" +
                    "\t},\n" +
                    "\t\"data\": [{\n" +
                    "\t\t\"ddbh\": \"111\",\n" +
                    "\t\t\"ddje\": \"21\",\n" +
                    "\t\t\"jfzt\": \"1\",\n" +
                    "\t\t\"ddzt\": \"1\",\n" +
                    "\t\t\"nwslbh\": \"1121\",\n" +
                    "\t\t\"url\": \"1\",\n" +
                    "\t\t\"ze\": \"1\"\n" +
                    "\t}]\n" +
                    "}";
        }
        if ("wwsq_ykqtkcz".equals(beanId)) {
            response = "{\n" +
                    "  \"head\": {\n" +
                    "    \"regionCode\": \"地区编码\",\n" +
                    "    \" orgid \": \"机构编码\",\n" +
                    "    \" statusCode \": \"响应状态码\",\n" +
                    "    \" msg \": \"响应信息\"\n" +
                    "  },\n" +
                    "  \"data\": [\n" +
                    "    {\n" +
                    "      \"tkzt\": \"1\",\n" +
                    "      \"tkdh\": \"2\",\n" +
                    "      \"ddbh\": \"3\",\n" +
                    "      \"tkje\": \"4\"\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}";
        }
        if ("wwsq_ykqtkztcx".equals(beanId)) {
            response = "{\n" +
                    "  \"head\": {\n" +
                    "    \"regionCode\": \"地区编码\",\n" +
                    "    \" orgid \": \"机构编码\",\n" +
                    "    \" statusCode \": \"响应状态码\",\n" +
                    "    \" msg \": \"响应信息\"\n" +
                    "  },\n" +
                    "  \"data\": {\n" +
                    "    \"tkzt\": \"1\"\n" +
                    "  }\n" +
                    "}";
        }
        if ("wwsq_pos_gxdd".equals(beanId)) {
            response = "{\n" +
                    "  \"head\": {\n" +
                    "    \"regionCode\": \"地区编码\",\n" +
                    "    \" orgid \": \"机构编码\",\n" +
                    "    \" statusCode \": \"响应状态码\",\n" +
                    "    \" msg \": \"响应信息\"\n" +
                    "  },\n" +
                    "  \"data\": {\n" +
                    "    \"IttParty_Jrnl_No\": \"\",\n" +
                    "    \"Py_Ordr_No\": \"\",\n" +
                    "    \"PsRlt_Cd\": \"\",\n" +
                    "    \"Err_Pcsg_Inf\": \"\",\n" +
                    "    \"Py_StCd\": \"\",\n" +
                    "    \"Rmrk1\": \"\",\n" +
                    "    \"Rmrk2\": \"\",\n" +
                    "    \"SIGN_INF\": \"\"\n" +
                    "  }\n" +
                    "}";
        }
        if ("wwsqScddh".equals(beanId)) {
            response = "{\n" +
                    "  \"head\": {\n" +
                    "    \"regionCode\": \"地区编码\",\n" +
                    "    \" orgid \": \"机构编码\",\n" +
                    "    \" statusCode \": \"响应状态码\",\n" +
                    "    \" msg \": \"响应信息\"\n" +
                    "  },\n" +
                    "  \"data\": [\n" +
                    "    {\n" +
                    "      \"ddbh\": \"20200302001001\",\n" +
                    "      \"sfglid\": \"20200302001001\",\n" +
                    "      \"sfglidlx\": \"20200302001001\",\n" +
                    "      \"jfzt\": \"\",\n" +
                    "      \"code\": \"0000\",\n" +
                    "      \"ddjlid\": \"0000\",\n" +
                    "      \"jfurl\": \"http://\"\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}";
        }
        if ("wwsq_tsswsys".equals(beanId)) {
            response = "{\n" +
                    "  \"head\": {\n" +
                    "    \"regionCode\": \"地区编码\",\n" +
                    "    \" orgid \": \"机构编码\",\n" +
                    "    \" statusCode \": \"响应状态码\",\n" +
                    "    \" msg \": \"响应信息\"\n" +
                    "  },\n" +
                    "  \"data\": {\n" +
                    "    \"code\": \"\",\n" +
                    "    \"msg\": \"\",\n" +
                    "    \"jsmx\": [\n" +
                    "      {\n" +
                    "        \"nsrsbh\": \"\",\n" +
                    "        \"sphm\": \"\",\n" +
                    "        \"swjgbm\": \"\",\n" +
                    "        \"jsjg\": \"\"\n" +
                    "      }\n" +
                    "    ],\n" +
                    "    \"fsmx\": [\n" +
                    "      {\n" +
                    "        \"wybh\": \"\",\n" +
                    "        \"jksbm\": \"\",\n" +
                    "        \"fsjg\": \"\"\n" +
                    "      }\n" +
                    "    ]\n" +
                    "  }\n" +
                    "}";
        }
        return response;
    }
}
