package cn.gtmap.realestate.exchange.service.impl.inf.request;

import cn.gtmap.gtc.feign.common.exception.GtFeignException;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.exchange.core.bo.log.AuditEventBO;
import cn.gtmap.realestate.exchange.core.bo.reqProp.BeanReqPropBO;
import cn.gtmap.realestate.exchange.service.inf.build.InterfaceRequestBuilder;
import cn.gtmap.realestate.exchange.service.inf.request.InterfaceRequestService;
import cn.gtmap.realestate.exchange.util.CommonUtil;
import cn.gtmap.realestate.exchange.util.XmlEntityConvertUtil;
import com.alibaba.fastjson.JSONObject;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-05-24
 * @description
 */
@Service(value = "beanReq")
public class BeanRequestServiceImpl extends InterfaceRequestService<BeanReqPropBO> {


    /**
     * @param builder
     * @return java.lang.Object
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 发送请求
     */
    @Override
    public void sendRequest(InterfaceRequestBuilder builder) {
        // 获取请求属性
        BeanReqPropBO prop = super.getRequestPropFromBuilder(builder);
        // 获取  请求 参数 键值对
        Object requestBody = builder.getRequestBody();
        if(prop.isParamRequired() && (requestBody == null
                || !CheckParameter.checkAnyParameter(requestBody))){
            return ;
        }
        Map<String,Object> requestParamMap = new HashMap<>();
        if (!(requestBody instanceof List) && !(requestBody instanceof JSONObject)) {
            if (requestBody instanceof Map) {
                requestParamMap = (HashMap) requestBody;
            } else {
                requestParamMap = CommonUtil.objectToMap(requestBody);
            }
            if (requestParamMap == null && requestBody != null && requestBody instanceof String) {
                requestParamMap = CommonUtil.stringToMap(XmlEntityConvertUtil.xmlToJson(requestBody.toString()));
            }
        }
        Object beanService = null;
        if(StringUtils.isNotBlank(prop.getBeanName())){
            beanService = Container.getBean(prop.getBeanName());
        }
        if(StringUtils.isNotBlank(prop.getClassName())){
            try {
                Class clazz = Class.forName(prop.getClassName());
                beanService = clazz.newInstance();
            } catch (ClassNotFoundException e) {
                LOGGER.error("未找到配置类：{}",prop.getClassName(),e);
            } catch (IllegalAccessException e) {
                LOGGER.error("配置类实例化异常：{}",prop.getClassName(),e);
            } catch (InstantiationException e) {
                LOGGER.error("配置类实例化异常：{}",prop.getClassName(),e);
            }
        }

        if(prop != null && beanService != null
                && StringUtils.isNotBlank(prop.getMethodName())){
            String methodName = prop.getMethodName();
            String params = prop.getParams();
            String paramVals = prop.getParamVals();
            if(beanService != null){
                // 获取目标方法
                Method[] methods = beanService.getClass().getMethods();
                Method targetMethod = null;
                if(methods.length > 0){
                    for(Method method : methods){
                        if(StringUtils.equals(method.getName(),methodName)){
                            targetMethod = method;
                            break;
                        }
                    }
                }
                if(targetMethod != null){
                    // 获取方法参数
                    Object[] args = new Object[0];
                    // 获取 参数类型
                    if(StringUtils.isNotBlank(params)){
                        Class<?>[] paramTypes = targetMethod.getParameterTypes();
                        String[] paramKey = params.split(",");
                        args = new Object[paramKey.length];
                        for(int i = 0 ;i < paramKey.length ; i++){
                            Object param = null;
                            // 如果请求参数是JSON结构 直接从 request中获取
                            if(requestBody instanceof JSONObject){
                                param = ((JSONObject)requestBody).get(paramKey[i]);
                            }else if(MapUtils.isNotEmpty(requestParamMap)){
                                param = requestParamMap.get(paramKey[i]);
                            }
                            Class paramClass = paramTypes[i];
                            // 如果类型直接对上了
                            if(paramClass.isInstance(param)){
                                args[i] = param;
                            }else if(param instanceof JSONObject){
                                args[i] = ((JSONObject) param).toJavaObject(paramClass);
                            }else if(param instanceof String){
                                // 字符串类型 说明是JSON结构的
                                args[i] = JSONObject.parseObject((String)param, paramClass);
                            }
                        }
                    }else if(StringUtils.isNotBlank(paramVals)){
                        args = params.split(",");
                    }else {
                        args = new Object[1];
                        args[0] = requestBody;
                        Class<?>[] paramTypes = targetMethod.getParameterTypes();
                        // 排除String类型
                        if(paramTypes.length == 1
                                && !paramTypes[0].equals(String.class)){
                            args[0] = JSONObject.parseObject(JSONObject.toJSONString(requestBody),paramTypes[0]);
                        }
                    }
                    Object response = null;
                    Exception logE = null;
                    try {
                        // 执行方法
                        LOGGER.debug("beanRequest:{},method:{},args:{}",beanService.getClass().getName(),
                                targetMethod.getName(),JSONObject.toJSONString(args));
                        if(prop.isNoArgs()){
                            response = targetMethod.invoke(beanService);
                        }else{
                            response = targetMethod.invoke(beanService,args);
                        }

                        LOGGER.debug("beanRequest:{},method:{},response:{}",beanService.getClass().getName(),
                                targetMethod.getName(),JSONObject.toJSONString(response));
                        if(prop.isResponseJson()){
                            super.setResponseBody(JSONObject.toJSONString(response),builder);
                        }else{
                            builder.setResponseBody(response);
                        }
                    } catch (AppException e){
                        logE = e;
                        String msg = getErrorMsg(e);
                        LOGGER.error(msg,e);
                        throw e;
                    } catch (IllegalAccessException e) {
                        logE = e;
                        String msg = getErrorMsg(e);
                        LOGGER.error(msg,e);
                        throw new AppException(msg);
                    } catch (InvocationTargetException e) {
                        logE = e;
                        String msg;
                        if(e.getTargetException() != null && (e.getTargetException() instanceof AppException)){
                            msg = e.getTargetException().getMessage();
                        }else {
                            msg = getErrorMsg(e);
                        }
                        LOGGER.error(msg,e);
                        throw new AppException(msg);
                    } finally {
                        AuditEventBO auditEventBO = new AuditEventBO(prop,builder);
                        auditEventBO.setRequest(JSONObject.toJSONString(requestBody));
                        auditEventBO.setResponse(JSONObject.toJSONString(response));
                        auditEventBO.setException(logE);
                        super.saveAuditLog(auditEventBO);
                    }
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

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param ex
     * @return java.lang.String
     * @description 获取异常信息
     */
    private String getErrorMsg(Exception ex){
        String msg = "通过应用内部服务方式请求方法异常";
        GtFeignException gte = null;
        // lyq 2018-12-30
        // 增加判断是否是feign异常
        if (ex instanceof GtFeignException) {
            gte = (GtFeignException) ex;
        }
        if(ex.getCause() != null && ex.getCause() instanceof HystrixRuntimeException){
            ex = (HystrixRuntimeException)ex.getCause();
        }
        // 或者是 有Hystix异常包装过的 feign异常 处理 异常码
        if (ex.getCause() != null && ex.getCause() instanceof GtFeignException) {
            gte = ((GtFeignException) ex.getCause());
        }
        if (gte != null) {
            String responseBody = gte.getMsgBody();
            Map bodyMap = JSONObject.parseObject(responseBody, Map.class);
            if (MapUtils.getInteger(bodyMap, "code") != null
                    && StringUtils.isNotBlank(MapUtils.getString(bodyMap, "msg"))) {
                msg = MapUtils.getString(bodyMap, "msg");
                if(StringUtils.isNotBlank(MapUtils.getString(bodyMap,"detail"))){
                    msg = msg +","+MapUtils.getString(bodyMap,"detail");
                }
            }
        }
        return msg;
    }
}
