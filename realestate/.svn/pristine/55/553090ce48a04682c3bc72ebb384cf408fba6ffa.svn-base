package cn.gtmap.realestate.exchange.service.impl.inf.build;

import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.exchange.core.bo.xsd.ElementBO;
import cn.gtmap.realestate.exchange.core.bo.xsd.ResponseInfoBO;
import cn.gtmap.realestate.exchange.service.inf.build.BuildRequestService;
import cn.gtmap.realestate.exchange.service.inf.build.InterfaceElemDozerService;
import cn.gtmap.realestate.exchange.service.inf.build.InterfaceRequestBuilder;
import cn.gtmap.realestate.exchange.util.CommonUtil;
import cn.gtmap.realestate.exchange.util.TokenUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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
 * @description 处理响应实体
 */
@Service(value = "buildResponse")
public class BuildResponseBodyServiceImpl extends BuildAbstractServiceImpl {

    protected static Logger LOGGER = LoggerFactory.getLogger(BuildResponseBodyServiceImpl.class);

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

        // 如果 请求信息为空 但是请求数据不为空时 保存对照后的请求数据 为响应结构
        if(builder.getExchangeBean() != null
                && builder.getExchangeBean().getRequestInfoBO() == null
                && builder.getRequestBody() != null){
            builder.setResponseBody(CommonUtil.objectAppendObj(builder.getResponseBody(),builder.getRequestBody()));
        }

        // 此处不需要判断 responseBody是否为null
        // 有需要根据responseBody是否为null 判断响应结果的接口
        if(builder.getExchangeBean() != null
                && builder.getExchangeBean().getResponseInfoBO() != null){
            ResponseInfoBO responseInfoBO = builder.getExchangeBean().getResponseInfoBO();
            //  响应可能存在三种情况：1.JSONObject  2.JSONArray  3.String
            Object respBodySource = builder.getResponseBody();
            //先处理token验证  如果已经重新获取过一次token就不再处理
            if((builder.getHasGetToken()==null || !builder.getHasGetToken()) &&
                    StringUtils.isNotBlank(responseInfoBO.getTokenErrorBeanName()) &&
                    StringUtils.isNotBlank(responseInfoBO.getTokenType())){
                boolean checkResult = (boolean) interfaceElemDozerService.request(respBodySource,responseInfoBO.getTokenErrorBeanName());
                //token错误的话重新请求
                if(checkResult){
                    //先清除token
                    TokenUtil.removeToken(responseInfoBO.getTokenType());
                    //设置已经重新获取token
                    builder.setHasGetToken(true);
                    //重新请求
                    builder.setResponseBody(null);
                    builder.setRequestBody(builder.getOriginalRequestObject());
                    for (BuildRequestService service : builder.getBuildServiceList()) {
                        service.build(builder);
                    }
                    return;
                }
            }

            //  循环 配置中的 element元素 转换responseBody
            if (CollectionUtils.isNotEmpty(responseInfoBO.getElementBOList())) {
                List<ElementBO> elementBOList = responseInfoBO.getElementBOList();
                // 循环处理 Element 元素
                try {
                    Map elemenMap = new HashMap();
                    Object responseBody = null;
                    for (ElementBO elem : elementBOList) {
                        Object result = null;
                        try {
                            result = convertElement(respBodySource,elem);
                        }catch (Exception e){
                            if(elem.isIgnoreException()){
                                LOGGER.error("convertElement异常，element:{}",JSONObject.toJSONString(elem),e);
                            }else{
                                throw e;
                            }
                        }

                        // 对空值做处理
                        if(result == null){
                            result = dealNull(elem);
                        }

                        // 判断是否需要 XML 转实体
                        if(result instanceof String && StringUtils.isNotBlank(elem.getXmlToObject())){
                            result = xmlToObject(result,elem.getXmlToObject());
                        }

                        // 判断是否需要 编码格式转换
//                        if(result instanceof String && StringUtils.isNotBlank(elem.getCharsetChange())){
//                            result = charsetChange(result,elem.getCharsetChange());
//                        }

                        // 判断是否需要 base64解密
                        if(result instanceof String && StringUtils.isNotBlank(elem.getBase64Decode())){
                            result = base64Decode(result,elem.getBase64Decode());
                        }

                        // 如果 元素值不允许为空
                        if(elem.isNotEmpty() && result == null ){
                            if(StringUtils.isNotBlank(elem.getEmptyExMsg())){
                                throw new AppException(elem.getEmptyExMsg());
                            }else{
                                throw new AppException("相应结果为空");
                            }
                        }

                        if(result != null && respBodySource != result){

                            //  集合强转成实体操作
                            if(result instanceof List && elem.getListToObject()){
                                List resultList = (List)result;
                                if(CollectionUtils.isNotEmpty(resultList)){
                                    result = resultList.get(0);
                                }
                            }

                            //  实体强转集合操作
                            if(elem.isObjectToList()){
                                List tempList = new ArrayList();
                                tempList.add(result);
                                result = tempList;
                            }

                            if(StringUtils.isNotBlank(elem.getName())){
                                elemenMap.put(elem.getName(),result);
                            }  else if(elementBOList.size() == 1){
                                responseBody = result;
                            } else if(StringUtils.isNotBlank(elem.getBase64Decode()) && result instanceof String){
//                                responseBody = result;
                                respBodySource = result;
                            } else if(!(result instanceof List)){
                                // 针对属性与其他资源平铺场景
                                elemenMap.putAll(JSONObject.parseObject(JSONObject.toJSONString(result)));
                            } else {
                                throw new AppException("元素存在多个未命名配置，请检查");
                            }
                        }
                        // 将元素解析后的结果 也保存在转换前的资源中 可作为下一个元素解析的参数
                        if(MapUtils.isNotEmpty(elemenMap) && respBodySource instanceof Map){
                            ((Map)respBodySource).putAll(elemenMap);
                        } else if (elem.isAppendRequestBody() && MapUtils.isNotEmpty(elemenMap)) {
                            Map<String, Object> requestParamMap = CommonUtil.objectToMap(respBodySource);
                            if (requestParamMap == null) {
                                requestParamMap = new HashMap();
                            }
                            requestParamMap.putAll(elemenMap);
                            respBodySource = requestParamMap;
                        }


                    }
                    if(MapUtils.isNotEmpty(elemenMap)
                            && responseBody == null ){
                        responseBody = elemenMap;
                    }
                    // 再处理 最外层 即response标签上的dozerXml 和 sourceService
                    if(responseInfoBO.getDozerBeanMapper() != null){
                        responseBody = interfaceElemDozerService.collectCovert((Map)responseBody,responseInfoBO.getDozerBeanMapper());
                    }
                    if(StringUtils.isNotBlank(responseInfoBO.getRefBeanName())){
                        responseBody = interfaceElemDozerService.request(responseBody,responseInfoBO.getRefBeanName());
                    }
                    if(StringUtils.isNotBlank(responseInfoBO.getResultKey())){
                        if(responseBody instanceof Map){
                            responseBody = MapUtils.getObject((Map)responseBody,responseInfoBO.getResultKey());
                        }else if(responseBody instanceof JSONObject){
                            responseBody = ((JSONObject) responseBody).get(responseInfoBO.getResultKey());
                        }
                    }

                    // 排除属性
                    if(CollectionUtils.isNotEmpty(responseInfoBO.getExcludeKey())){
                        responseBody = CommonUtil.excludeObjectKey(responseInfoBO.getExcludeKey(),responseBody);
                    }
                    builder.setResponseBody(responseBody);
                } catch (IllegalAccessException e) {
                    LOGGER.error("element元素转换",e);
                    throw new AppException("element元素转换");
                } catch (InstantiationException e) {
                    LOGGER.error("element元素转换",e);
                    throw new AppException("element元素转换");
                }
            } else if (builder.getResponseBody() == null) {
                ResponseInfoBO response = builder.getExchangeBean().getResponseInfoBO();
                if (response != null && response.isExtendRequestBody()) {
                    builder.setResponseBody(builder.getRequestBody());
                }

            }
        }
    }

    /**
     * @param requestSource
     * @param elementBO
     * @return java.lang.Object
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 转换元素 先对照，后请求
     */
    private Object convertElement(Object requestSource, ElementBO elementBO) throws IllegalAccessException, InstantiationException {
        Object convertResult = requestSource;
        if(BooleanUtils.toBoolean(elementBO.getSourceFirst())){
            if(StringUtils.isNotBlank(elementBO.getRefBeanName())){
                convertResult = interfaceElemDozerService.request(convertResult,elementBO.getRefBeanName());
            }
            if(convertResult != null && elementBO.hasDozer()){
                convertResult = interfaceElemDozerService.convert(convertResult,elementBO.getDozerBeanMapper() );
            }
        }else{
            if(convertResult != null && elementBO.hasDozer()){
                convertResult = interfaceElemDozerService.convert(convertResult,elementBO.getDozerBeanMapper() );
            }
            if(StringUtils.isNotBlank(elementBO.getRefBeanName())){
                convertResult = interfaceElemDozerService.request(convertResult,elementBO.getRefBeanName());
            }
        }
        return convertResult;
    }

}
