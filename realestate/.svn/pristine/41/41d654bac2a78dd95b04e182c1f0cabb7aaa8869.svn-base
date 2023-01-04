package cn.gtmap.realestate.exchange.service.impl.inf.build;

import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.exchange.core.ex.ValidException;
import cn.gtmap.realestate.exchange.service.inf.ExchangeBeanRequestService;
import cn.gtmap.realestate.exchange.service.inf.build.InterfaceElemDozerService;
import cn.gtmap.realestate.exchange.util.CommonUtil;
import cn.gtmap.realestate.exchange.util.ValidUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.metadata.ClassMappingMetadata;
import org.dozer.metadata.MappingMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-05-19
 * @description 交互接口服务中  ELEMENT元素的 对照处理逻辑
 */
@Service
public class InterfaceElementDozerServiceImpl implements InterfaceElemDozerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InterfaceElementDozerServiceImpl.class);

    @Autowired
    private ValidUtil validUtil;

    @Autowired
    private ExchangeBeanRequestService exchangeBeanRequestService;

    /**
     * @param source
     * @param dozerBeanMapper
     * @return java.lang.Object
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 转换
     */
    @Override
    public Object convert(Object source, DozerBeanMapper dozerBeanMapper) throws InstantiationException, IllegalAccessException {
        if (source != null && dozerBeanMapper != null) {
            MappingMetadata mappingMetadata = dozerBeanMapper.getMappingMetadata();
            List<ClassMappingMetadata> metaMappingList = mappingMetadata.getClassMappings();
            if (CollectionUtils.isNotEmpty(metaMappingList)) {
                Object targetObject = null;
                for (ClassMappingMetadata metaMapping : metaMappingList) {
                    // 如果定义了 map-id 说明是被其他MAPPING引用，不需要处理
                    if (StringUtils.isNotBlank(metaMapping.getMapId())) {
                        continue;
                    }
                    // 从大对象（source）中 找到需要对照的对象实体
                    Object sourceObject = getObjectByClass(source, metaMapping.getSourceClass());
                    if (sourceObject != null) {
                        // 集合
                        if (sourceObject instanceof List) {
                            List targetList = new ArrayList();
                            List tempList = (List) sourceObject;
                            // 循环对照  集合对照结果 也是集合
                            for (Object temp : tempList) {
                                Object tar = convertSingleObj(temp, metaMapping.getDestinationClass(), dozerBeanMapper, null);
                                if (tar != null) {
                                    targetList.add(tar);
                                }
                            }
                            if (CollectionUtils.isNotEmpty(targetList)) {
                                if (targetObject == null) {
                                    targetObject = new ArrayList<>();
                                }
                                if (targetObject instanceof List) {
                                    ((List) targetObject).addAll(targetList);
                                } else {
                                    return targetList;
                                }
                            }
                        } else {
                            // 实体对照
                            targetObject = convertSingleObj(sourceObject, metaMapping.getDestinationClass(), dozerBeanMapper, targetObject);
                        }
                    }
                }
                return targetObject;
            }
        }
        return null;
    }

    /**
     * @param elementMap
     * @param dozerBeanMapper
     * @return java.lang.Object
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 汇总元素对照后的实体对象
     */
    @Override
    public Object collectCovert(Map<String, Object> elementMap, DozerBeanMapper dozerBeanMapper) throws IllegalAccessException, InstantiationException {
        MappingMetadata mappingMetadata = dozerBeanMapper.getMappingMetadata();
        // 此处 获取 原对象为MAP的对照关系 中的 目标对象实体类
        List<ClassMappingMetadata> metaMappingList = mappingMetadata.getClassMappingsBySource(Map.class);
        if (CollectionUtils.isEmpty(metaMappingList)) {
            metaMappingList = mappingMetadata.getClassMappingsBySource(HashMap.class);
        }
        if (CollectionUtils.isNotEmpty(metaMappingList)) {
            Class targetClass = metaMappingList.get(0).getDestinationClass();
            Object targetObject = targetClass.newInstance();
            dozerBeanMapper.map(elementMap, targetObject);
            if (CheckParameter.checkAnyParameter(targetObject)) {
                return targetObject;
            }
        }
        return null;
    }

    /**
     * @param sourceObject
     * @param targetClass
     * @param dozerBeanMapper
     * @return java.lang.Object
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 单个实体转换
     */
    @Override
    public Object convertSingleObj(Object sourceObject, Class targetClass,
                                   DozerBeanMapper dozerBeanMapper, Object targetObject) throws IllegalAccessException, InstantiationException {
        if (targetObject == null) {
            targetObject = targetClass.newInstance();
        }
        dozerMapWithValid(dozerBeanMapper, sourceObject, targetObject);
        if (!CheckParameter.checkAnyParameter(targetObject)) {
            return null;
        }
        return targetObject;
    }

    /**
     * @param source
     * @param sourceClass
     * @return java.lang.Object
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据 对照源类型，从source中找到待对照实体
     */
    private static Object getObjectByClass(Object source, Class sourceClass) {

        // 1. 直接判断实体 是否是dozer文件里的 source
        Object sourceObj = covertInstance(sourceClass, source);
        if (sourceObj != null) {
            return sourceObj;
        }

        // 2. 判断是否为 JSONObject
        if (source instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) source;
            return getJsonObjectSourceObj(jsonObject, sourceClass);
        }
        // 3. 判断是否为 JSONArray
        if (source instanceof JSONArray) {
            JSONArray jsonArray = (JSONArray) source;
            return getJsonArraySourceObj(jsonArray, sourceClass);
        }

        // 4. 判断是否是MAP类型，如果是MAP类型循环 value 判断
        if (source instanceof Map) {
            // 4.1 判断Map 是否功能直接转成实体
            Object sourceTarObj = covertInstance(sourceClass, source);
            if (sourceTarObj != null) {
                return sourceTarObj;
            }
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(source));
            return getJsonObjectSourceObj(jsonObject, sourceClass);
//            Map sourceMap = (Map)source;
//            if(MapUtils.isNotEmpty(sourceMap)){
//                Iterator<Map.Entry> ite = sourceMap.entrySet().iterator();
//                while (ite.hasNext()){
//                    Map.Entry entry = ite.next();
//                    Object value = entry.getValue();
//                    // 4.2 Map 中的 VALUE 如果是 目标实体
//                    Object tarObj = covertInstance(sourceClass,value);
//                    if(tarObj != null){
//                        return tarObj;
//                    }else if(value instanceof  List){
//                        // Map 中的 VALUE 是 LIST类型 循环判断是否有目标实体
//                        List sourceList = (List) value;
//                        List filteredList = new ArrayList();
//                        if(CollectionUtils.isNotEmpty(sourceList)){
//                            for(Object temp : sourceList){
//                                Object tarTemp = covertInstance(sourceClass,temp);
//                                if(tarTemp != null){
//                                    filteredList.add(tarTemp);
//                                }
//                            }
//                        }
//                        if(CollectionUtils.isNotEmpty(filteredList)){
//                            return filteredList;
//                        }
//                    }
//                }
//            }
        }

        // 5. 判断是否是List类型，循环判断
        if (source instanceof List) {
            List sourceList = (List) source;
            List filteredList = new ArrayList();
            if (CollectionUtils.isNotEmpty(sourceList)) {
                for (Object temp : sourceList) {
                    Object tarObj = covertInstance(sourceClass, temp);
                    if (tarObj != null) {
                        filteredList.add(tarObj);
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(filteredList)) {
                return filteredList;
            }
        }
        return null;
    }

    public static Object covertInstance(Class<?> clazz, Object obj) {
        try {
            if (clazz.isInstance(obj)) {
                return obj;
            }
            if (CommonUtil.checkObjectCastToClass(obj, clazz)) {
                Object source = JSONObject.parseObject(JSONObject.toJSONString(obj), clazz);
                if (CheckParameter.checkAnyParameter(source)) {
                    return source;
                }
            }
        } catch (Exception e) {

        }
        return null;
    }

    private static Object getJsonObjectSourceObj(JSONObject jsonObject, Class sourceClass) {
        // 直接判断是否可以转成目标对象
//        Object temp = null;
        boolean isList = false;
//        try{
//            temp = jsonObject.toJavaObject(sourceClass);
//        }catch(Exception e){}
//        if(temp != null && CheckParameter.checkAnyParameter(temp)){
//            return temp;
//        }
        if (CommonUtil.checkObjectCastToClass(jsonObject, sourceClass)) {
            Object source = JSONObject.parseObject(JSONObject.toJSONString(jsonObject), sourceClass);
            if (CheckParameter.checkAnyParameter(source)) {
                return source;
            }
        }
        // 循环JSON中的key值
        List filteredList = new ArrayList();
        for (String key : jsonObject.keySet()) {
            Object arryInnerObj = jsonObject.get(key);
            Object tarObj = covertInstance(sourceClass, arryInnerObj);
            if (tarObj != null) {
                isList = false;
                filteredList.add(tarObj);
                break;
            } else if (arryInnerObj instanceof Collection) {
                if (!(arryInnerObj instanceof JSONArray) && (arryInnerObj instanceof List)) {
                    arryInnerObj = JSONArray.parseArray(JSONObject.toJSONString(arryInnerObj));
                }
                Object arrayResult = getJsonArraySourceObj((JSONArray) arryInnerObj, sourceClass);
                if (arrayResult != null) {
                    isList = true;
                    filteredList.addAll((List) arrayResult);
                }
            } else if (arryInnerObj instanceof JSONObject) {
                if (arryInnerObj == jsonObject) {
                    return arryInnerObj;
                }
                Object objResult = getJsonObjectSourceObj((JSONObject) arryInnerObj, sourceClass);
                if (objResult != null) {
                    if (objResult instanceof List) {
                        isList = true;
                        filteredList.addAll((List) objResult);
                    } else {
                        isList = false;
                        filteredList.add(objResult);
                    }
                }
            }
        }
        if (filteredList.size() > 1 || isList) {
            return filteredList;
        }
        if (filteredList.size() == 1) {
            return filteredList.get(0);
        }
        return null;
    }

    private static Object getJsonArraySourceObj(JSONArray jsonArray, Class sourceClass) {
        List filteredList = new ArrayList();
        if (CollectionUtils.isNotEmpty(jsonArray)) {
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject innerJsonObj = null;
                try {
                    innerJsonObj = jsonArray.getJSONObject(i);
                } catch (Exception e) {
                    LOGGER.debug("", e);
                }
                if (innerJsonObj != null) {
                    Object objResult = getJsonObjectSourceObj(innerJsonObj, sourceClass);
                    if (objResult != null) {
                        if (objResult instanceof List) {
                            filteredList.addAll((List) objResult);
                        } else {
                            filteredList.add(objResult);
                        }
                    }
                }
            }
        }
        return filteredList;
    }

    /**
     * @param source
     * @param exchangeBeanId
     * @return java.lang.Object
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 执行服务 返回服务响应结果
     */
    @Override
    public Object request(Object source, String exchangeBeanId) {
        if (StringUtils.isNotBlank(exchangeBeanId)) {
            return exchangeBeanRequestService.request(exchangeBeanId, source);
        }
        return null;
    }

    /**
     * @param dozerBeanMapper
     * @param sourceObject
     * @param targetObject
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 对照 并 验证
     */
    @Override
    public void dozerMapWithValid(DozerBeanMapper dozerBeanMapper, Object sourceObject, Object targetObject) {
        // 对照源
        String validMsg = validUtil.validateObject(sourceObject);
        if (StringUtils.isNotBlank(validMsg)) {
            LOGGER.error(validMsg);
            throw new ValidException(validMsg);
        }
        dozerBeanMapper.map(sourceObject, targetObject);
        // 对照结果
        validMsg = validUtil.validateObject(targetObject);
        if (StringUtils.isNotBlank(validMsg)) {
            LOGGER.error(validMsg);
            throw new ValidException(validMsg);
        }
    }
}
