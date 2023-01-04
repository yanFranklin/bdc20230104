package cn.gtmap.realestate.common.core.dozer.converters.gtmap;

import cn.gtmap.realestate.common.core.dozer.GtmapCompareableCustomConverter;
import cn.gtmap.realestate.common.util.BdcdyhToolUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.converters.DateFormatContainer;
import org.dozer.converters.PrimitiveOrWrapperConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * @program: realestate
 * @description: 抵押权土地抵押面积取值
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-06-15 15:59
 **/
public class GetTddyMjConvert implements GtmapCompareableCustomConverter {
    private String param;
    private Object destObject;
    private Object srcObject;
    private static final Logger LOGGER = LoggerFactory.getLogger(GetTddyMjConvert.class);
    private final PrimitiveOrWrapperConverter primitiveConverter = new PrimitiveOrWrapperConverter();


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
            String[] strMj = StringUtils.split(param, ",");
            try {
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(strMj[0], srcObject.getClass());
                Method get = propertyDescriptor.getReadMethod();
                if (get.invoke(srcObject) != null && StringUtils.equals(strMj[1], BdcdyhToolUtils.getDzwTzm(get.invoke(srcObject).toString()))) {
                    PropertyDescriptor propertyDescriptor2 = new PropertyDescriptor(strMj[2], srcObject.getClass());
                    Method get2 = propertyDescriptor2.getReadMethod();
                    return primitiveConverter.convert(get2.invoke(srcObject), destinationClass, new DateFormatContainer(""));
                }
            } catch (Exception e) {
                LOGGER.error("获得目标源面积数据报错", e);
            }
        }
        return existingDestinationFieldValue;
    }
}
