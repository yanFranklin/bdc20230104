package cn.gtmap.realestate.common.core.dozer.converters.gtmap;

import org.dozer.CustomConverter;
import org.dozer.converters.DateFormatContainer;
import org.dozer.converters.PrimitiveOrWrapperConverter;

/**
 * @author <a herf="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2018/11/8
 * @description
 */
public class NotNullNoMapCustomConverter implements CustomConverter {

    private final PrimitiveOrWrapperConverter primitiveConverter = new PrimitiveOrWrapperConverter();

    /**
     * @param existingDestinationFieldValue
     * @param sourceFieldValue
     * @param destinationClass
     * @param sourceClass
     * @return
     */
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {
        if (existingDestinationFieldValue != null) {
            return existingDestinationFieldValue;
        }
        sourceFieldValue = primitiveConverter.convert(sourceFieldValue, destinationClass, new DateFormatContainer(""));
        return sourceFieldValue;
    }
}
