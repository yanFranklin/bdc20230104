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
import cn.gtmap.realestate.exchange.util.constants.Constants;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-11-21
 * @description
 */
@Service(value = "httpTokenPostFile")
public class HttpTokenPostFileRequestServiceImpl extends InterfaceRequestService<HttpReqPropBO> {

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
        if (prop.isParamRequired() && !CheckParameter.checkAnyParameter(requestBody)) {
            return;
        }
        Map<String, Object> requestParamMap = new HashMap<>();
        if (!(requestBody instanceof List) && !(requestBody instanceof JSONObject)) {
            if (requestBody instanceof Map) {
                requestParamMap = (HashMap) requestBody;
            } else {
                requestParamMap = CommonUtil.objectToMap(requestBody);
            }
        }

        // 获取参数 中的 文件流
        String fileParamName = "";
        MultipartFile file = null;
        for (String key : requestParamMap.keySet()) {
            if (requestParamMap.get(key) instanceof MultipartFile) {
                file = (MultipartFile) requestParamMap.get(key);
                fileParamName = key;
            }
        }
        if (StringUtils.isNotBlank(prop.getUrl()) && file != null) {
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
            if (StringUtils.isNotBlank(prop.getTokenInterface())) {
                httpPost.setHeader("token", token);
            }
            String fileName = file.getOriginalFilename();
            String response = "";
            Exception logE = null;
            try {
                MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create().
                        setMode(HttpMultipartMode.RFC6532);
                entityBuilder.setCharset(Charset.forName("UTF-8"));
                entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE); //加上此行代码解决返回中文乱码问题
                entityBuilder.addBinaryBody(fileParamName, file.getInputStream(),
                        ContentType.APPLICATION_FORM_URLENCODED, fileName); // 文件流
                HttpEntity entity = entityBuilder.build();
                httpPost.setEntity(entity);
                response = httpClientService.doPost(httpPost, "UTF-8");
            } catch (IOException e) {
                logE = e;
                LOGGER.error("httpPostFile 请求异常：url:{} ", requestUrl, e);
                throw new AppException("httpPost 请求异常");
            } finally {
                try {
                    AuditEventBO auditEventBO = new AuditEventBO(prop, builder);
                    auditEventBO.setResponse(response);
                    auditEventBO.setException(logE);
                    super.saveAuditLog(auditEventBO);
                } catch (Exception e) {
                    LOGGER.error("记录请求日志异常", e);
                }
            }
            LOGGER.info("httpPostFile response:{}", response);
//            response = "{\n" +
//                    "    \"status\": true,\n" +
//                    "    \"data\": \"201906/20190629160748829.jpg\",\n" +
//                    "    \"msg\": \"查询成功\"\n" +
//                    "}";
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
}
