package cn.gtmap.realestate.exchange.service.inf.request;

import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.exchange.core.bo.log.AuditEventBO;
import cn.gtmap.realestate.exchange.service.inf.build.InterfaceRequestBuilder;
import cn.gtmap.realestate.exchange.service.impl.inf.build.BuildLogServiceImpl;
import cn.gtmap.realestate.exchange.util.CommonUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-05-15
 * @description 发送请求的服务
 */
public abstract class InterfaceRequestService<T> {

    protected static Logger LOGGER = LoggerFactory.getLogger(InterfaceRequestService.class);

    @Autowired
    private BuildLogServiceImpl buildLogService;

    public T getRequestPropFromBuilder(InterfaceRequestBuilder builder){
        T requestProp = null;
        if(builder.getRequestInfo() != null){
            requestProp = CommonUtil.mapToObject(builder.getRequestInfo(),getTClass());
        }
        return requestProp;
    }

    private Class<T> getTClass(){
        //返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。
        ParameterizedType type = (ParameterizedType)this.getClass().getGenericSuperclass();
        //返回表示此类型实际类型参数的 Type 对象的数组()，想要获取第二个泛型的Class，所以索引写1
        return (Class)type.getActualTypeArguments()[0];//<T>
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param auditEventBO
     * @return void
     * @description 使builder与日志实体 互相持有
     */
    public void saveAuditLog(AuditEventBO auditEventBO){
        if(auditEventBO.getBuilder() != null){
            auditEventBO.getBuilder().setAuditEventBO(auditEventBO);
//            buildLogService.saveAuditLog(auditEventBO);
        }
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param builder
     * @return void
     * @description 发送请求的 包装方法 可在真正发送请求前后做处理
     */
    public void wrapSendRequest(InterfaceRequestBuilder builder){
        try{
            long beginTime = System.currentTimeMillis();
            // 发送请求
            sendRequest(builder);
            long endTime = System.currentTimeMillis();
            if(builder.getAuditEventBO() != null){
                // 获取请求时间 并保存日志
                builder.getAuditEventBO().setUseTime(endTime-beginTime);
            }
        }catch (Exception e){
            LOGGER.error("请求异常，beanId:{}",builder.getExchangeBean().getId(),e);
            throw new AppException(e.getMessage());
        }finally {
            buildLogService.saveAuditLog(builder.getAuditEventBO());
        }
    }


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param builder
     * @return java.lang.Object
     * @description 发送请求
     */
    public abstract void sendRequest(InterfaceRequestBuilder builder);


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param response
     * @param builder
     * @return void
     * @description 将相应转换成JSON实体  放进builder 的 response属性
     */
    public void setResponseBody(String response,InterfaceRequestBuilder builder){
        if(StringUtils.isNotBlank(response)){
            try {
                //处理返回参数
                if (builder.getRequestInfo() != null && builder.getRequestInfo().containsKey("dealWithResponseSwitch") && !"false".equals(builder.getRequestInfo().get("dealWithResponseSwitch"))){
                    response = dealWithResponse(response);
                }
                // 将结果 JSON 结构存放进 响应结构体
                JSONObject jsonObject = JSONObject.parseObject(response);
                if(builder.getResponseBody() != null
                        && builder.getResponseBody() instanceof Map){
                    jsonObject.putAll((Map)builder.getResponseBody());
                }
                builder.setResponseBody(jsonObject);
            } catch (JSONException e){
                // 如果 转换异常，尝试转换成 JSONArray
                try{
                    JSONArray jsonArray = JSONObject.parseArray(response);
                    builder.setResponseBody(jsonArray);
                } catch (JSONException e2){
                    // 如果仍然转换异常，直接保存String
                    builder.setResponseBody(response);
                }
            }
        }
    }

    /**
     * 处理外部接口返回体转化为exchange内部的返回体,由子类实现
     * @param response
     * @return
     */
    public abstract String dealWithResponse(String response);

}
