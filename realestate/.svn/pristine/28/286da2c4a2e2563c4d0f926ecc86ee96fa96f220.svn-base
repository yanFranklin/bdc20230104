package cn.gtmap.realestate.exchange.service.impl.inf.request;

import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.HttpClientService;
import cn.gtmap.realestate.exchange.core.bo.log.AuditEventBO;
import cn.gtmap.realestate.exchange.core.bo.reqProp.HttpReqPropBO;
import cn.gtmap.realestate.exchange.service.inf.build.InterfaceRequestBuilder;
import cn.gtmap.realestate.exchange.service.inf.request.InterfaceRequestService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.StringEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-09-27
 * @description http  delete 方法
 */
@Service(value = "httpDeleteJson")
public class HttpDeleteJsonRequestServiceImpl extends InterfaceRequestService<HttpReqPropBO> {

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
        if(StringUtils.isNotBlank(prop.getUrl())){
            String requestUrl = prop.getUrl();
            ExchangeHttpDelete httpDelete = new ExchangeHttpDelete(requestUrl);
            httpDelete.setHeader("Content-Type","application/json; charset=UTF-8");
            httpDelete.setHeader("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
            StringEntity entity = new StringEntity(JSONObject.toJSONString(requestBody), Charset.forName("UTF-8"));
            httpDelete.setEntity(entity);
            String response = "";
            Exception logE = null;
            try {
                response = httpClientService.sendRequest(httpDelete,"UTF-8");
            } catch (IOException e) {
                logE = e;
                LOGGER.error("httpDelete 请求异常：url:{},reqMap:{}",requestUrl,JSONObject.toJSONString(requestBody),e);
                throw new AppException("httpDelete 请求异常");
            } finally {
                try{
                    AuditEventBO auditEventBO = new AuditEventBO(prop,builder);
                    auditEventBO.setRequest(JSONObject.toJSONString(requestBody));
                    auditEventBO.setResponse(response);
                    auditEventBO.setException(logE);
                    super.saveAuditLog(auditEventBO);
                }catch (Exception e){
                    LOGGER.error("记录请求日志异常",e);
                }
            }
            super.setResponseBody(response,builder);
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
