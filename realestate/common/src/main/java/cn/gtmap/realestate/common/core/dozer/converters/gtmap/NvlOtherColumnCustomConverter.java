package cn.gtmap.realestate.common.core.dozer.converters.gtmap;

import cn.gtmap.realestate.common.core.dozer.GtmapCompareableCustomConverter;
import org.apache.commons.lang3.StringUtils;
import org.dozer.converters.DateFormatContainer;
import org.dozer.converters.PrimitiveOrWrapperConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-07-09
 * @description 为空 获取其他字段 给目标字段赋值
 */
public class NvlOtherColumnCustomConverter implements GtmapCompareableCustomConverter {

    private String param;
    private static final Logger LOGGER = LoggerFactory.getLogger(NvlOtherColumnCustomConverter.class);
    private final PrimitiveOrWrapperConverter primitiveConverter = new PrimitiveOrWrapperConverter();
    private Object srcObject;
    private Object destObject;

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


    /**
     * @param existingDestinationFieldValue
     * @param sourceFieldValue
     * @param destinationClass
     * @param sourceClass
     * @return
     */
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {
        if (sourceFieldValue == null) {
            if (StringUtils.isNotBlank(param)) {
                try {
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(param, srcObject.getClass());
                    Method get = propertyDescriptor.getReadMethod();
                    return primitiveConverter.convert(get.invoke(srcObject), destinationClass, new DateFormatContainer(""));
                } catch (IntrospectionException e) {
                    LOGGER.error("获得目标源数据报错", e);
                } catch (IllegalAccessException e) {
                    LOGGER.error("获得目标源数据报错", e);
                } catch (InvocationTargetException e) {
                    LOGGER.error("获得目标源数据报错", e);
                }
            } else {
                return existingDestinationFieldValue;
            }
        }
        return sourceFieldValue;
    }

}
