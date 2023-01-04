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
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/6/15.
 * @description 拼接字符串
 */

public class ConnectFieldCustomConvert implements GtmapCompareableCustomConverter {

    private String param;
    private Object destObject;
    private Object srcObject;
    private static final Logger LOGGER = LoggerFactory.getLogger(ContainCustomConvert.class);
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
            StringBuilder builder = new StringBuilder();
            try {
                for(String s:str){
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(s, srcObject.getClass());
                    Method get = propertyDescriptor.getReadMethod();
                    if (get.invoke(srcObject) != null) {
                        builder.append(get.invoke(srcObject).toString()+",") ;
                    }
                }
                if(builder.length()> 0){
                    return primitiveConverter.convert(StringUtils.substring(builder.toString(),0,builder.length()-1), destinationClass, new DateFormatContainer(""));
                }
            } catch (Exception e) {
                LOGGER.error("获得目标源数据报错", e);
            }
        }
        return existingDestinationFieldValue;
    }
}
