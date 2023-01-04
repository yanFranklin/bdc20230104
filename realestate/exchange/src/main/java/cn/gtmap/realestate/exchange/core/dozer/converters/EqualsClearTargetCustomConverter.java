package cn.gtmap.realestate.exchange.core.dozer.converters;

import cn.gtmap.realestate.common.core.dozer.GtmapCompareableCustomConverter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * @author <a herf="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 1.0, 2020/8/14
 * @description 如果值与参数相同则清空目标值，不相同的话不对照
 */
public class EqualsClearTargetCustomConverter implements GtmapCompareableCustomConverter {
    private static final Logger LOGGER = LoggerFactory.getLogger(EqualsClearTargetCustomConverter.class);
    private String param;
    private Object destObject;

    /**
     * @param existingDestinationFieldValue
     * @param sourceFieldValue
     * @param destinationClass
     * @param sourceClass
     * @return
     */
    @Override
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {
        if (sourceFieldValue != null && StringUtils.isNotBlank(sourceFieldValue.toString()) && StringUtils.isNotBlank(param)) {
            if (StringUtils.equals(param, sourceFieldValue.toString())) {
                return " ";
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

    }


    @Override
    public void setParameter(String parameter) {
        param = parameter;
    }
}
