package cn.gtmap.realestate.exchange.service.impl.inf.request;

import cn.gtmap.realestate.common.core.dto.exchange.SdkApiAbstractApp;
import cn.gtmap.realestate.exchange.core.bo.log.AuditEventBO;
import cn.gtmap.realestate.exchange.core.bo.reqProp.HfSdkApiPropBO;
import cn.gtmap.realestate.exchange.service.inf.build.InterfaceRequestBuilder;
import cn.gtmap.realestate.exchange.service.inf.request.InterfaceRequestService;
import cn.gtmap.realestate.exchange.util.CommonUtil;
import com.alibaba.fastjson.JSONObject;
import com.iflytek.fsp.shield.java.sdk.model.ApiResponse;
import jodd.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-10-09
 * @description 合肥省级 数据平台接口 请求方式
 */
@Service(value = "hfSdkApi")
public class HfSdkApiRequestServiceImpl extends InterfaceRequestService<HfSdkApiPropBO> {

    @Value("${data.version:}")
    private String dataversion;
    // 请求参数 和响应结果 最大长度
    private static final int MAX_LENGTH = 5000;

    /**
     * @param builder
     * @return java.lang.Object
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 发送请求
     */
    @Override
    public void sendRequest(InterfaceRequestBuilder builder) {
        HfSdkApiPropBO propBO = super.getRequestPropFromBuilder(builder);
        Object requestBody = builder.getRequestBody();
        Map<String, Object> requestParamMap = CommonUtil.objectToMap(requestBody);

        // 处理参数
        if (!(requestBody instanceof List) && !(requestBody instanceof JSONObject)) {
            if (requestBody instanceof Map) {
                requestParamMap = (HashMap) requestBody;
            } else {
                requestParamMap = CommonUtil.objectToMap(requestBody);
            }
        }
        if (StringUtil.isNotBlank(propBO.getSdkAppClassName())) {
            String response = "";
            Exception logE = null;
            try {
                Class appClass = Class.forName(propBO.getSdkAppClassName());
                Object requestApp = appClass.newInstance();
                if (requestApp != null) {

                    LOGGER.info("电子证照请求版本1：{}", dataversion);
                    LOGGER.info("电子证照请求版本sdk名1：{}", propBO.getSdkAppClassName());
                    SdkApiAbstractApp abstractApp = (SdkApiAbstractApp) requestApp;
                    abstractApp.setHost(propBO.getHost());
                    abstractApp.setHttpPort(Integer.parseInt(propBO.getPort()));
                    ApiResponse apiResponse = abstractApp.getApiResponse(requestParamMap);
                    LOGGER.info("电子证照response1:{}", apiResponse.toString());

                    // 处理执行结果
                    if (apiResponse != null
                            && apiResponse.getBody() != null) {
                        response = new String(apiResponse.getBody(), "UTF-8");
                        LOGGER.info("电子证照response:{}", response);
                        super.setResponseBody(response, builder);
                    }


                }
            } catch (ClassNotFoundException e) {
                logE = e;
                LOGGER.error("SDK请求异常", e);
            } catch (IllegalAccessException e) {
                logE = e;
                LOGGER.error("SDK请求异常", e);
            } catch (InstantiationException e) {
                logE = e;
                LOGGER.error("SDK请求异常", e);
            } catch (UnsupportedEncodingException e) {
                logE = e;
                LOGGER.error("SDK请求异常", e);
            } finally {
                // 记录 请求日志
                AuditEventBO auditEventBO = new AuditEventBO(propBO, builder);
                auditEventBO.setRequest(JSONObject.toJSONString(requestParamMap));
                auditEventBO.setResponse(response);
                if (StringUtils.isNotBlank(auditEventBO.getRequest()) && auditEventBO.getRequest().length() > MAX_LENGTH) {
                    auditEventBO.setRequest("字符串过长，已省略。开头：" + StringUtils.left(auditEventBO.getRequest(), 1000));
                }
                if (StringUtils.isNotBlank(auditEventBO.getResponse()) && auditEventBO.getResponse().length() > MAX_LENGTH) {
                    auditEventBO.setResponse("字符串过长，已省略。开头：" + StringUtils.left(auditEventBO.getResponse(), 1000));
                }
                auditEventBO.setException(logE);
                super.saveAuditLog(auditEventBO);
            }
        }
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
