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
 * @program: realestate
 * @description: 先取第一个，第一个没有值取第二个
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-09-09 09:46
 **/
public class NvlElseOthersCustomConvert implements GtmapCompareableCustomConverter {

    private String param;
    private Object destObject;
    private Object srcObject;

    private static final Logger LOGGER = LoggerFactory.getLogger(NvlElseOthersCustomConvert.class);
    private final PrimitiveOrWrapperConverter primitiveConverter = new PrimitiveOrWrapperConverter();

    /**
     * Setter for converter static parameter. Method is guaranteed to be called before the first execution.
     *
     * @param destinationObject
     */
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

    @Override
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {
        if (srcObject != null && StringUtils.isNotBlank(param)) {
            String[] str = StringUtils.split(param, ",");
            //先取第一个值，为空取第二个
            try {
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(str[0], srcObject.getClass());
                Method get = propertyDescriptor.getReadMethod();
                if (get.invoke(srcObject) != null) {
                    return primitiveConverter.convert(get.invoke(srcObject), destinationClass, new DateFormatContainer(""));
                } else {
                    //取第二个字段值
                    PropertyDescriptor propertyOther = new PropertyDescriptor(str[1], srcObject.getClass());
                    Method getOther = propertyOther.getReadMethod();
                    return primitiveConverter.convert(getOther.invoke(srcObject), destinationClass, new DateFormatContainer(""));
                }
            } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
                LOGGER.error("获得目标源数据报错", e);
            }

        }
        return null;
    }
}
