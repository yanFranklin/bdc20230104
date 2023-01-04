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
 * @version 1.0  2018/11/10
 * @description 加和处理
 */
public class PlusCustomConverter implements GtmapCompareableCustomConverter {
    private String param;
    private Object destObject;
    private Object srcObject;
    private static final Logger LOGGER = LoggerFactory.getLogger(PlusCustomConverter.class);
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
                Double sumMj=0.0;
                boolean isAllNull=true;
                for (int i = 0; i < strMj.length; i++) {
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(strMj[i], srcObject.getClass());
                    Method get = propertyDescriptor.getReadMethod();
                    if (get.invoke(srcObject) != null) {
                        isAllNull=false;
                        if(Double.parseDouble(get.invoke(srcObject).toString()) > 0){
                            sumMj+=Double.parseDouble(get.invoke(srcObject).toString());
                        }
                    }
                }
                if(isAllNull){
                    return existingDestinationFieldValue;
                }
                return primitiveConverter.convert(sumMj, destinationClass, new DateFormatContainer(""));
            } catch (Exception e) {
                LOGGER.error("加和处理数据报错", e);
            }
        }
        return existingDestinationFieldValue;
    }
}
