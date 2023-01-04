package cn.gtmap.realestate.exchange.service.inf.build;

import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.exchange.core.bo.log.AuditEventBO;
import cn.gtmap.realestate.exchange.core.bo.xsd.ExchangeBean;
import cn.gtmap.realestate.exchange.service.inf.request.InterfaceRequestService;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-05-15
 * @description
 */
public class InterfaceRequestBuilder {

    /**
     * 服务对象实体
     */
    private ExchangeBean exchangeBean;

    /**
     * 发送请求的服务
     */
    private InterfaceRequestService interfaceRequestService;

    /**
     * 请求类型 基本信息
     */
    private Map<String,Object> requestInfo;

    /**
     * request转换前的 请求参数
     */
    private Object originalRequestObject;

    /**
     * 请求参数 转换源
     */
//    private Object requestSource;

    /**
     * request中 各元素 转换后的 结果集合
     */
//    private List<Object> requestElementList;

    /**
     * 请求参数
     */
    private Object requestBody;

    /**
     * 外部请求直接响应结果
     */
    private Object responseBody;

    /**
     * 是否已经重新获取过token
     */
    private Boolean hasGetToken;

    /**
     * 构造过程 服务集合
     */
    private List<BuildRequestService> buildServiceList;

    // 记录日志
    private AuditEventBO auditEventBO;

    public InterfaceRequestBuilder(ExchangeBean exchangeBean,Object requestBody,List<BuildRequestService> buildServiceList){
        setOriginal(requestBody);
        this.requestBody = requestBody;
        this.exchangeBean = exchangeBean;
        this.buildServiceList = buildServiceList;
    }

    public InterfaceRequestBuilder(ExchangeBean exchangeBean,Object requestBody){
        setOriginal(requestBody);
        this.requestBody = requestBody;
        this.exchangeBean = exchangeBean;
        List<BuildRequestService> serviceList = new ArrayList<>();
        serviceList.add((BuildRequestService) Container.getBean(Constants.BUILDBEANNAME_SERVICEINFO));
        serviceList.add((BuildRequestService) Container.getBean(Constants.BUILDBEANNAME_REQUEST));
        serviceList.add((BuildRequestService) Container.getBean(Constants.BUILDBEANNAME_SENDREQUEST));
        serviceList.add((BuildRequestService) Container.getBean(Constants.BUILDBEANNAME_RESPONSE));
        serviceList.add((BuildRequestService) Container.getBean(Constants.BUILDBEANNAME_LOG));
        this.buildServiceList = serviceList;
    }

    public Object invoke(){
        if(CollectionUtils.isNotEmpty(buildServiceList)){
            for(BuildRequestService service : buildServiceList){
                try {
                    service.build(this);
                } catch (Exception e) {
                    BuildRequestService logService = (BuildRequestService) Container.getBean(Constants.BUILDBEANNAME_LOG);
                    if (this.getAuditEventBO() == null) {
                        this.setAuditEventBO(new AuditEventBO());
                    }
                    this.getAuditEventBO().setException(e);
                    logService.build(this);
                    throw new AppException(e.getMessage());
                }

            }
        }
        return this.responseBody;
    }

    public Map<String, Object> getRequestInfo() {
        return requestInfo;
    }

    public void setRequestInfo(Map<String, Object> requestInfo) {
        this.requestInfo = requestInfo;
    }


    public InterfaceRequestService getInterfaceRequestService() {
        return interfaceRequestService;
    }

    public void setInterfaceRequestService(InterfaceRequestService interfaceRequestService) {
        this.interfaceRequestService = interfaceRequestService;
    }

    public Object getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(Object requestBody) {
        this.requestBody = requestBody;
    }

    public Object getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(Object responseBody) {
        this.responseBody = responseBody;
    }

    public ExchangeBean getExchangeBean() {
        return exchangeBean;
    }

    public void setExchangeBean(ExchangeBean exchangeBean) {
        this.exchangeBean = exchangeBean;
    }

    public Object getOriginalRequestObject() {
        return originalRequestObject;
    }

    public void setOriginalRequestObject(Object originalRequestObject) {
        this.originalRequestObject = originalRequestObject;
    }

    public List<BuildRequestService> getBuildServiceList() {
        return buildServiceList;
    }

    public Boolean getHasGetToken() {
        return hasGetToken;
    }

    public void setHasGetToken(Boolean hasGetToken) {
        this.hasGetToken = hasGetToken;
    }


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param requestBody
     * @return void
     * @description 处理原始请求参数
     */
    private void setOriginal(Object requestBody){
        if(requestBody != null){
            if(requestBody instanceof List){
                this.originalRequestObject = JSONArray.parseArray(JSONObject.toJSONString(requestBody));
            }else{
                try{
                    this.originalRequestObject = JSONObject.parseObject(JSONObject.toJSONString(requestBody),requestBody.getClass());
                }catch(Exception e){
                    this.originalRequestObject = requestBody;
                }
            }
        }
    }

    public AuditEventBO getAuditEventBO() {
        return auditEventBO;
    }

    public void setAuditEventBO(AuditEventBO auditEventBO) {
        this.auditEventBO = auditEventBO;
    }
}
