package cn.gtmap.realestate.exchange.service.impl.inf.request;

import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.HttpClientService;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.exchange.config.TokenServiceChoose;
import cn.gtmap.realestate.exchange.core.bo.log.AuditEventBO;
import cn.gtmap.realestate.exchange.core.bo.reqProp.HttpReqPropBO;
import cn.gtmap.realestate.exchange.service.inf.TokenService;
import cn.gtmap.realestate.exchange.service.inf.build.InterfaceRequestBuilder;
import cn.gtmap.realestate.exchange.service.inf.request.InterfaceRequestService;
import cn.gtmap.realestate.exchange.util.CommonUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "httpTokenGetForHuaian")
public class HttpTokenGetForHuaianRequestServiceImpl extends InterfaceRequestService<HttpReqPropBO> {

    @Autowired
    private TokenServiceChoose tokenServiceChoose;

    @Autowired
    private HttpClientService httpClientService;

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
        if (prop.isParamRequired() && !CheckParameter.checkAnyParameter(requestBody)) {
            return;
        }
        if (StringUtils.isNotBlank(prop.getUrl())) {
            String token = null;
            //获取对应的token实现类
            if (StringUtils.isNotBlank(prop.getTokenInterface())) {
                TokenService tokenService = tokenServiceChoose.getTokenService(prop.getTokenInterface());
                if (tokenService != null) {
                    token = tokenService.getToken(builder.getExchangeBean().getId());
                }
            }

            String requestUrl = prop.getUrl();
            HttpGet httpGet = null;
            Map<String, Object> requestParamMap = new HashMap<>(12);
            if (prop.isGetUseNameValuePair()) {
                requestParamMap = CommonUtil.objectToMap(requestBody);
                List<NameValuePair> paramsList = new ArrayList<>();
                for (Map.Entry<String, Object> request : requestParamMap.entrySet()) {
                    paramsList.add(new BasicNameValuePair(request.getKey(), JSONObject.toJSONString(request.getValue())));
                }
                URIBuilder uriBuilder = null;
                try {
                    uriBuilder = new URIBuilder(requestUrl);
                    uriBuilder.addParameters(paramsList);
                    httpGet = new HttpGet(uriBuilder.build());
                } catch (URISyntaxException e) {
                    LOGGER.debug("httpGet 请求异常：url:{},reqMap:{}", requestUrl, JSONObject.toJSONString(requestBody), e);
                    httpGet = new HttpGet(requestUrl + "?" + CommonUtil.mapToUrlParam(requestParamMap));
                }
            } else {
                String urlParam = "";
                if (StringUtils.isNotBlank(prop.getParamClass())) {
                    // 需要强转
                    try {
                        Object castParam = JSONObject.parseObject(JSONObject.toJSONString(requestBody), Class.forName(prop.getParamClass()));
                        requestParamMap = JSONObject.parseObject(JSONObject.toJSONString(castParam, SerializerFeature.WriteNullStringAsEmpty), Map.class);
                        urlParam = CommonUtil.mapToUrlParam(requestParamMap);
                    } catch (ClassNotFoundException e) {
                        LOGGER.error("参数强转异常", e);
                    }
                } else {
                    requestParamMap = CommonUtil.objectToMap(requestBody);
                    urlParam = CommonUtil.mapToUrlParam(requestParamMap);
                }
                httpGet = new HttpGet(requestUrl + "?" + urlParam);
                LOGGER.info("httpTokenGet 请求链接为：{}，参数为：{}", requestUrl, urlParam);
            }
            if (StringUtils.isNotBlank(prop.getContentType())) {
                httpGet.setHeader("Content-Type", prop.getContentType());
            } else {
                httpGet.setHeader("Content-Type", "application/json; charset=UTF-8");
            }
            if (StringUtils.isNotBlank(prop.getxPlatToken())) {
                httpGet.setHeader("X-PLAT-TOKEN", prop.getxPlatToken());
            }

            httpGet.setHeader("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
            httpGet.setHeader("unitCode", "LandResourcesBureau");
            if (StringUtils.isNotBlank(prop.getAppKey())) {
                if(StringUtils.isNotBlank(prop.getResponser()) && "baselibrary".equals(prop.getResponser())){
                    httpGet.setHeader("X-H3C-APPKEY", prop.getAppKey());
                }else{
                    httpGet.setHeader("appKey", prop.getAppKey());
                }
            }
            if (StringUtils.isNotBlank(prop.getToken())) {
                httpGet.setHeader("token", prop.getToken());
            }
            if(StringUtils.isNotBlank(token)){
                httpGet.setHeader(new BasicHeader("access_token", token));
            }
            if(StringUtils.isNotBlank(prop.getWorkspaceId())){
                httpGet.setHeader("X-H3C-ID", prop.getWorkspaceId());
            }

            LOGGER.info("httpTokenGet 请求头参数为：{}", JSON.toJSONString(httpGet.getAllHeaders()));
            String response = "";
            Exception logE = null;
            try {
                LOGGER.info("是否https请求：{}", prop.isHttpsRequest());
                if (prop.isHttpsRequest()) {
                    LOGGER.info("httpsTokenGet 请求：httpGet:{}", JSON.toJSONString(httpGet));
                    response = httpClientService.doHttpsGetHlzs(httpGet);
                } else {
                    LOGGER.info("httpTokenGet  请求：httpGet:{}",  JSON.toJSONString(httpGet));
                    response = httpClientService.doGet(httpGet);
                }
            } catch (IOException e) {
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
            LOGGER.info("httpTokenGet 请求返回值为：{}", response);
            super.setResponseBody(response, builder);
        }
    }

    public static HostnameVerifier getHostnameVerifier(){
        HostnameVerifier hostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        };
        return hostnameVerifier;
    }

    public static SSLSocketFactory getSSLSocketFactory(){
        try{
            SSLContext sslContext =  SSLContext.getInstance("SSL");
            sslContext.init(null, getTrustManager(), new SecureRandom());
            return sslContext.getSocketFactory();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    private static TrustManager[] getTrustManager(){
        TrustManager[] trustManagers = new TrustManager[]{
            new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[]{};
                }
            }
        };
        return trustManagers;
    }

    /**
     * 处理外部接口返回体转化为exchange内部的返回体,由子类实现
     *
     * @param response
     * @return
     */
    @Override
    public String dealWithResponse(String response) {
        return response;
    }
}
