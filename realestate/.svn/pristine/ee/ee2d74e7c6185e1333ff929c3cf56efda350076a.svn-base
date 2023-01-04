package cn.gtmap.realestate.exchange.service.impl.inf.build;

import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.exchange.core.bo.xsd.ElementBO;
import cn.gtmap.realestate.exchange.core.bo.xsd.RequestInfoBO;
import cn.gtmap.realestate.exchange.core.ex.ValidException;
import cn.gtmap.realestate.exchange.service.inf.build.InterfaceElemDozerService;
import cn.gtmap.realestate.exchange.service.inf.build.InterfaceRequestBuilder;
import cn.gtmap.realestate.exchange.util.CommonUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-05-15
 * @description 构造请求实体
 */
@Service(value = "buildRequest")
public class BuildRequestBodyServiceImpl extends BuildAbstractServiceImpl {

    protected static Logger LOGGER = LoggerFactory.getLogger(BuildRequestBodyServiceImpl.class);

    @Autowired
    private InterfaceElemDozerService interfaceElemDozerService;

    /**
     * @param builder
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 通过builder 处理 请求和响应
     */
    @Override
    public void build(InterfaceRequestBuilder builder) {
        if(builder.getRequestBody() == null && builder.getExchangeBean() != null
                && builder.getExchangeBean().getRequestInfoBO() != null
                && !builder.getExchangeBean().getRequestInfoBO().isParamRequired()){
            builder.setRequestBody(new HashMap<>());
        }
        if(builder.getRequestBody() != null) {
            if (builder.getExchangeBean() != null
                    && CheckParameter.checkAnyParameter(builder.getExchangeBean().getRequestInfoBO())) {
                RequestInfoBO requestInfoBO = builder.getExchangeBean().getRequestInfoBO();
                Object requestSource = builder.getRequestBody();
                //  循环处理 Element 元素
                try {
                    Map totalMap = new HashMap();
                    Object requestBody = null;
                    if (CollectionUtils.isNotEmpty(requestInfoBO.getElementBOList())) {
                        List<ElementBO> elementBOList = requestInfoBO.getElementBOList();
                        for (ElementBO elem : elementBOList) {
                            Map elemenMap = new HashMap();
                            Object result = null;
                            try {
                                result = convertElement(requestSource, elem);
                            }catch (Exception e){
                                if(elem.isIgnoreException()){
                                    LOGGER.error("convertElement异常，element:{}",JSONObject.toJSONString(elem),e);
                                }else{
                                    throw e;
                                }
                            }
                            if(result == null){
                                result = dealNull(elem);
                            }
                            if (result != null && requestSource != result) {
                                //  集合强转成实体操作
                                if(result instanceof List && elem.getListToObject()){
                                    List resultList = (List)result;
                                    if(CollectionUtils.isNotEmpty(resultList)){
                                        result = resultList.get(0);
                                    }else{
                                        result = new HashMap<>();
                                    }
                                }
                                //  实体强转集合操作
                                if(elem.isObjectToList()){
                                    List tempList = new ArrayList();
                                    tempList.add(result);
                                    result = tempList;
                                }


                                if (StringUtils.isNotBlank(elem.getName())) {
                                    // 判断是否需要转成JSON结构的字符串
                                    if(elem.isObjectToJsonString()){
                                        result = JSONObject.toJSONString(result);
                                    }
                                    // 判断是否需要处理为XML结构
                                    if(StringUtils.isNotBlank(elem.getObjectToXmlStringMethod())){
                                        result = super.executeStaticMethodWithEntity(result,elem.getObjectToXmlStringMethod());
                                    }
                                    // 判断是否 需要加密
                                    if(StringUtils.isNotBlank(elem.getEncodeMethod())){
                                        result = super.executeStaticMethodWithEntity(result,elem.getEncodeMethod());
                                    }
                                    elemenMap.put(elem.getName(), result);
                                } else if (!(result instanceof List)) {
                                    // 针对属性与其他资源平铺场景
                                    elemenMap.putAll(JSONObject.parseObject(JSONObject.toJSONString(result)));
                                } else if (elementBOList.size() == 1 || elem.getNotNullReturn()) {
                                    // 如果 element 长度即为1  或者 元素特性为 有返回值即返回
                                    requestBody = result;
                                }  else {
                                    throw new AppException("元素存在多个未命名配置，请检查");
                                }

                                // 将返回结果 添加给其他参数 一并返回
                                if(elementBOList.size() == 1 && elem.isAppendRequestBody() ){
                                    Map<String,Object> requestParamMap = CommonUtil.objectToMap(requestSource);
                                    elemenMap.putAll(requestParamMap);
                                }
                                // 元素特性为 有返回值即返回 不进行以下element处理，则直接返回
                                if(elem.getNotNullReturn()){
                                    break;
                                }
                            }

                            // 如果 元素值不允许为空
                            if(elem.isNotEmpty() && result == null ){
                                if(StringUtils.isNotBlank(elem.getEmptyExMsg())){
                                    throw new ValidException(elem.getEmptyExMsg());
                                }else{
                                    throw new ValidException(elem.getName()+",不能为空");
                                }
                            }
                            // 将元素解析后的结果 也保存在转换前的资源中 可作为下一个元素解析的参数
                            if(! elem.isNoSetRequestBody() &&  MapUtils.isNotEmpty(elemenMap) && requestSource instanceof Map){
                                ((Map)requestSource).putAll(elemenMap);
                            }
                            totalMap.putAll(elemenMap);
                        }
                    }
                    if(MapUtils.isNotEmpty(totalMap)
                            && requestBody == null ){
                        requestBody = totalMap;
                    }
                    // 再处理 最外层的dozerXml 对照
                    if(requestInfoBO.getDozerBeanMapper() != null){
                        requestBody = interfaceElemDozerService.collectCovert((Map)requestBody,requestInfoBO.getDozerBeanMapper());
                    }
                    if(requestInfoBO.getRefBeanName() != null){
                        requestBody = interfaceElemDozerService.request(requestBody,requestInfoBO.getRefBeanName());
                    }

                    if(StringUtils.isNotBlank(requestInfoBO.getResultKey())){
                        if(requestBody instanceof Map){
                            requestBody = MapUtils.getObject((Map)requestBody,requestInfoBO.getResultKey());
                        }
                        if(requestBody instanceof JSONObject){
                            requestBody = ((JSONObject) requestBody).get(requestInfoBO.getResultKey());
                        }
                    }

                    // 如果最外层有name 则转成MAP形式 key为Name
                    if(StringUtils.isNotBlank(requestInfoBO.getName())){
                        Map<String,Object> outerRequestMap = new HashMap<>();
                        outerRequestMap.put(requestInfoBO.getName(),requestBody);
                        requestBody = outerRequestMap;
                    }
                    if(requestInfoBO.isReplaceRequestBody()){
                        builder.setRequestBody(requestBody);
                    }else if(requestBody != null){
                        if(requestInfoBO.isExtendRequestBody()
                                && builder.getRequestBody() != null){
                            builder.setRequestBody(CommonUtil.objectAppendObj(builder.getRequestBody(),
                                    requestBody));
                        }else{
                            builder.setRequestBody(requestBody);
                        }
                    }


                    // 排除属性
                    if(CollectionUtils.isNotEmpty(requestInfoBO.getExcludeKey())){
                        builder.setRequestBody(CommonUtil.excludeObjectKey(requestInfoBO.getExcludeKey(),builder.getRequestBody()));
                    }

                    if(((builder.getExchangeBean().getServiceInfoBO() == null
                            || StringUtils.isBlank(builder.getExchangeBean().getServiceInfoBO().getRefBeanName()))
                            &&
                            (builder.getExchangeBean().getResponseInfoBO() == null
                            || CollectionUtils.isEmpty(builder.getExchangeBean().getResponseInfoBO().getElementBOList
                                    ()))) || builder.getExchangeBean().getResponseInfoBO().isExtendRequestBody()){
                        builder.setResponseBody(requestBody);
                    }

                    if(builder.getExchangeBean().getResponseInfoBO() != null
                            && StringUtils.isNotBlank(builder.getExchangeBean().getResponseInfoBO().getExtendRequestBodyWithKey())){
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put(builder.getExchangeBean().getResponseInfoBO().getExtendRequestBodyWithKey(),requestBody);
                        builder.setResponseBody(jsonObject);
                    }
                } catch (IllegalAccessException e) {
                    LOGGER.error("element元素转换",e);
                    throw new AppException("element元素转换");
                } catch (InstantiationException e) {
                    LOGGER.error("element元素转换",e);
                    throw new AppException("element元素转换");
                }
            }
        }
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param requestSource
     * @param elementBO
     * @return java.lang.Object
     * @description 转换元素 先请求，后对照
     */
    private Object convertElement(Object requestSource, ElementBO elementBO) throws IllegalAccessException, InstantiationException {
        Object convertResult = requestSource;
        if(BooleanUtils.toBoolean(elementBO.getDozerFirst())){
            if(convertResult != null && elementBO.hasDozer()){
                convertResult = interfaceElemDozerService.convert(convertResult,elementBO.getDozerBeanMapper() );
            }
            if(StringUtils.isNotBlank(elementBO.getRefBeanName())){
                convertResult = interfaceElemDozerService.request(convertResult,elementBO.getRefBeanName());
            }
        }else{
            if(StringUtils.isNotBlank(elementBO.getRefBeanName())){
                convertResult = interfaceElemDozerService.request(convertResult,elementBO.getRefBeanName());
            }
            if(convertResult != null && elementBO.hasDozer()){
                convertResult = interfaceElemDozerService.convert(convertResult,elementBO.getDozerBeanMapper() );
            }
        }
        return convertResult;
    }
}
