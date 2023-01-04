package cn.gtmap.realestate.common.core.dozer.converters.gtmap;

import cn.gtmap.realestate.common.core.dozer.GtmapCompareableCustomConverter;
import org.apache.commons.lang3.StringUtils;
import org.dozer.converters.DateFormatContainer;
import org.dozer.converters.PrimitiveOrWrapperConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0, 2018/12/20
 * @descriptionv 某属性的值等于某值走 否则走某属性值
 */
public class EqualsElseCustomConvert implements GtmapCompareableCustomConverter {
    private String param;
    private Object destObject;
    private Object srcObject;

    private static final Logger LOGGER = LoggerFactory.getLogger(EqualsElseCustomConvert.class);
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

    /**
     * @param existingDestinationFieldValue
     * @param sourceFieldValue
     * @param destinationClass
     * @param sourceClass
     * @return
     */
    @Override
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {
        if (srcObject != null && StringUtils.isNotBlank(param)) {
            String[] str = StringUtils.split(param, ",");
            try {
                //取第1个数据为字段
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(str[0], srcObject.getClass());
                Method get = propertyDescriptor.getReadMethod();
                if (get.invoke(srcObject) != null && get.invoke(srcObject).toString().equals(str[1])) {
                    //取前一个数据为字段
                    PropertyDescriptor propertyElse = new PropertyDescriptor(str[2], srcObject.getClass());
                    Method elseGet = propertyElse.getReadMethod();
                    return primitiveConverter.convert(elseGet.invoke(srcObject), destinationClass, new DateFormatContainer(""));
                }else{
                    //取最后一个数据为字段
                    PropertyDescriptor propertyElse = new PropertyDescriptor(str[3], srcObject.getClass());
                    Method elseGet = propertyElse.getReadMethod();
                    return primitiveConverter.convert(elseGet.invoke(srcObject), destinationClass, new DateFormatContainer(""));
                }
            } catch (Exception e) {
                LOGGER.error("获得目标源数据报错", e);
            }
        }
        return existingDestinationFieldValue;
    }
}
