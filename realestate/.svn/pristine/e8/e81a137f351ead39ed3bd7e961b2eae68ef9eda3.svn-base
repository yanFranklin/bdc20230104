package cn.gtmap.realestate.common.core.dozer.converters.gtmap;

import cn.gtmap.realestate.common.core.dozer.GtmapCompareableCustomConverter;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.dozer.converters.DateFormatContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * 参数字段有值做对照
 * @author <a herf="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0, 2018/11/8
 * @description
 */
public class ParamFieldNotNullTargetCustomConverter implements GtmapCompareableCustomConverter {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParamFieldNotNullTargetCustomConverter.class);
    private String param;
    private Object destObject;
    private Object srcObject;
    /**
     * @param existingDestinationFieldValue
     * @param sourceFieldValue
     * @param destinationClass
     * @param sourceClass
     * @return
     */
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {
        if (sourceFieldValue != null && StringUtils.isNotBlank(sourceFieldValue.toString()) && destObject!=null && StringUtils.isNotBlank(param)) {
            try {
                if(srcObject instanceof JSONObject){
                    if(StringUtils.isNotBlank(((JSONObject) srcObject).getString(param))){
                        return sourceFieldValue;
                    }
                }else {
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(param, srcObject.getClass());
                    Method get = propertyDescriptor.getReadMethod();
                    if (get.invoke(srcObject) != null && StringUtils.isNotBlank(get.invoke(srcObject).toString())) {
                        return sourceFieldValue;
                    }
                }
            } catch (Exception e) {
                LOGGER.error("获取参数字段值异常", e);
            }
        }
        return existingDestinationFieldValue;
    }

    @Override
    public void setDestinationObject(Object destinationObject) {
        destObject = destinationObject;
    }

    @Override
    public void setSourceObject(Object sourceObject) {
        srcObject = sourceObject;
    }


    @Override
    public void setParameter(String parameter) {
        param = parameter;
    }
}
