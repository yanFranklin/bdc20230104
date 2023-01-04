package cn.gtmap.realestate.exchange.service.impl.inf.request;

import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.exchange.core.bo.log.AuditEventBO;
import cn.gtmap.realestate.exchange.core.bo.reqProp.RestReqPropBO;
import cn.gtmap.realestate.exchange.service.inf.build.InterfaceRequestBuilder;
import cn.gtmap.realestate.exchange.service.inf.request.InterfaceRequestService;
import cn.gtmap.realestate.exchange.util.CommonUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-05-23
 * @description Rest GET
 */
@Service(value = "restGet")
public class RestGetRequestServiceImpl extends InterfaceRequestService<RestReqPropBO> {

    @Resource(name = "exchangeRestTemplate")
    private RestTemplate restTemplate;

    /**
     * @param builder
     * @return java.lang.Object
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 发送请求
     */
    @Override
    public void sendRequest(InterfaceRequestBuilder builder) {
        // 获取请求属性
        RestReqPropBO prop = super.getRequestPropFromBuilder(builder);
        Object requestBody = builder.getRequestBody();
        if(StringUtils.isNotBlank(prop.getUrl())){
            String url = prop.getUrl();
            Map<String,Object> requestParamMap = CommonUtil.objectToMap(requestBody);
            String response = null;
            Exception logE = null;
            try{
                response = restTemplate.getForObject(url, String.class,requestParamMap);
            }catch (Exception e){
                logE = e;
                LOGGER.error("restGet 请求异常：url:{},requestParamMap:{}",prop.getUrl(),requestParamMap,e);
                throw new AppException("restGet 请求异常");
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
