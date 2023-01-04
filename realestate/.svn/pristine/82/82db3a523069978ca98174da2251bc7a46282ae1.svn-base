package cn.gtmap.realestate.exchange.service.impl.inf.request;

import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.common.util.WebServiceUtils;
import cn.gtmap.realestate.exchange.core.bo.log.AuditEventBO;
import cn.gtmap.realestate.exchange.core.bo.reqProp.XfireWebServiceBO;
import cn.gtmap.realestate.exchange.service.inf.build.InterfaceRequestBuilder;
import cn.gtmap.realestate.exchange.service.inf.request.InterfaceRequestService;
import cn.gtmap.realestate.exchange.util.CommonUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019-07-06
 * @description xfire webservice请求方式
 */
@Service(value = "xfireWebService")
public class XfireWebServiceRequestServiceImpl extends InterfaceRequestService<XfireWebServiceBO> {

    /**
     * @param builder
     * @return java.lang.Object
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 发送请求
     */
    @Override
    public void sendRequest(InterfaceRequestBuilder builder) {
        Object requestBody = builder.getRequestBody();
        XfireWebServiceBO prop = super.getRequestPropFromBuilder(builder);
        if(requestBody != null && CheckParameter.checkAnyParameter(requestBody) && StringUtils.isNoneBlank(prop.getUrl(),
                prop.getMethodName())){
            // 获取  请求 参数 键值对
            Map<String,Object> requestParamMap = CommonUtil.objectToMap(requestBody);
            String response = null;
            Exception logE = null;
            try {
                List<Object> paramArrayVal=new ArrayList<Object>();
                if(MapUtils.isNotEmpty(requestParamMap)){
                    for(Map.Entry<String,Object>  str:requestParamMap.entrySet()){
                        paramArrayVal.add(str.getValue());
                    }
                }
                response=WebServiceUtils.callWebService(prop.getUrl(),prop.getMethodName(),paramArrayVal.toArray());
                super.setResponseBody(response,builder);
            } catch (Exception e) {
                LOGGER.error("xifreWebService 请求异常：url:{},reqMap:{}",prop.getUrl(),requestParamMap,e);
                throw new AppException("xifreWebService 请求异常");
            } finally {
                try{
                    AuditEventBO auditEventBO = new AuditEventBO(prop,builder);
                    auditEventBO.setRequest(JSONObject.toJSONString(requestParamMap));
                    auditEventBO.setResponse(response);
                    auditEventBO.setException(logE);
                    super.saveAuditLog(auditEventBO);
                }catch (Exception e){
                    LOGGER.error("记录请求日志异常",e);
                }
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
    public String dealWithResponse(String response) {return response;}
}
