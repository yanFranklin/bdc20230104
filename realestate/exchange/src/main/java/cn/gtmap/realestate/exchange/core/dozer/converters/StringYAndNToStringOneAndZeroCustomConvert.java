package cn.gtmap.realestate.exchange.core.dozer.converters;

import cn.gtmap.realestate.common.core.dozer.GtmapCompareableCustomConverter;
import cn.gtmap.realestate.common.core.dozer.converters.gtmap.DefaultValueCustomConvert;
import org.apache.commons.lang3.math.NumberUtils;
import org.dozer.converters.PrimitiveOrWrapperConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 1.0  2020-08-12
 * @description 转换integer中的1和0至Y和N
 */
public class StringYAndNToStringOneAndZeroCustomConvert implements GtmapCompareableCustomConverter {
    private String param;
    private Object destObject;
    private Object srcObject;
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultValueCustomConvert.class);
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
        if (sourceFieldValue != null) {
            try {
                Integer integerVal;
                if ("Y".equals(sourceFieldValue.toString())) {
                    return "1";
                }
                if ("N".equals(sourceFieldValue.toString())) {
                    return "0";
                }
            } catch (Exception e) {
                LOGGER.error("是否转换失败,{}", sourceFieldValue, e);
            }
        }
        return sourceFieldValue;
    }
}