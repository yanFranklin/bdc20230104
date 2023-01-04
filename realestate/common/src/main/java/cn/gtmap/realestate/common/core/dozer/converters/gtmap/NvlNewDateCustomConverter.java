package cn.gtmap.realestate.common.core.dozer.converters.gtmap;

import org.dozer.CustomConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author <a herf="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2018/11/8
 * @description
 */
public class NvlNewDateCustomConverter implements CustomConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger(NvlNewDateCustomConverter.class);


    /**
     * @param existingDestinationFieldValue
     * @param sourceFieldValue
     * @param destinationClass
     * @param sourceClass
     * @return
     */
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {
        if (sourceFieldValue == null) {
            if (Date.class.isAssignableFrom(sourceClass)) {
                return new Date();
            } else {
                return existingDestinationFieldValue;
            }
        }
        return sourceFieldValue;
    }
}
