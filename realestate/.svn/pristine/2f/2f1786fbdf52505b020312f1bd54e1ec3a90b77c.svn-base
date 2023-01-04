package cn.gtmap.realestate.common.core.dozer.converters.gtmap;

import cn.gtmap.realestate.common.core.dozer.GtmapCompareableCustomConverter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 有值的话清空目标对象的值
 * @author <a herf="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0, 2018/11/8
 * @description
 */
public class NotNullClearTargetCustomConverter implements GtmapCompareableCustomConverter {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotNullClearTargetCustomConverter.class);
    private String param;
    private Object destObject;
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
                String[] params = param.split(",");
                for(String str:params){
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(str, destObject.getClass());
                    Method writeMethod = propertyDescriptor.getWriteMethod();
                    writeMethod.invoke(destObject,new String[]{null});
                }
            } catch (Exception e) {
                LOGGER.error("清空目标对象的字段值异常", e);
            }
            return null;
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
