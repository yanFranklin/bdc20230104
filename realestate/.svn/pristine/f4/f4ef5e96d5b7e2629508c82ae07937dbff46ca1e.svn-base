package cn.gtmap.realestate.common.core.dozer.converters.gtmap;

import cn.gtmap.realestate.common.core.dozer.GtmapCompareableCustomConverter;
import org.apache.commons.lang3.StringUtils;
import org.dozer.converters.DateFormatContainer;
import org.dozer.converters.PrimitiveOrWrapperConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a herf="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2018/11/8
 * @description
 */
public class NvlConstanstCustomConverter implements GtmapCompareableCustomConverter {
    private String param;
    private static final Logger LOGGER = LoggerFactory.getLogger(NvlConstanstCustomConverter.class);
    private final PrimitiveOrWrapperConverter primitiveConverter = new PrimitiveOrWrapperConverter();


    /**
     * @param existingDestinationFieldValue
     * @param sourceFieldValue
     * @param destinationClass
     * @param sourceClass
     * @return
     */
    @Override
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {
        if (sourceFieldValue == null || (sourceFieldValue instanceof String && StringUtils.isBlank(sourceFieldValue.toString()))) {
            if (StringUtils.isNotBlank(param)) {
                return primitiveConverter.convert(param, destinationClass, new DateFormatContainer(""));
            } else {
                return existingDestinationFieldValue;
            }
        }
        sourceFieldValue = primitiveConverter.convert(sourceFieldValue, destinationClass, new DateFormatContainer(""));
        return sourceFieldValue;
    }

    @Override
    public void setParameter(String parameter) {
        param = parameter;
    }

    @Override
    public void setDestinationObject(Object destinationObject) {

    }

    @Override
    public void setSourceObject(Object sourceObject) {

    }
}
