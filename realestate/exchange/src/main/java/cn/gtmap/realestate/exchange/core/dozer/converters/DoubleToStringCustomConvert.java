package cn.gtmap.realestate.exchange.core.dozer.converters;

import cn.gtmap.realestate.common.core.dozer.GtmapCompareableCustomConverter;
import cn.gtmap.realestate.common.core.dozer.converters.gtmap.DefaultValueCustomConvert;
import cn.gtmap.realestate.common.util.NumberUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.dozer.converters.PrimitiveOrWrapperConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;

/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 1.0  2020-08-12
 * @description 解决double转string自动科学计数法问题
 */
public class DoubleToStringCustomConvert implements GtmapCompareableCustomConverter {
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
                Double doubleVal = NumberUtils.createDouble(sourceFieldValue.toString());
                if (doubleVal != null) {
                    if (StringUtils.isBlank(param)) {
                        param = "#.########";
                    }
                    DecimalFormat df = new DecimalFormat(param);
                    return df.format(doubleVal);
                }
            } catch (Exception e) {
                LOGGER.error("double转string失败,{}", sourceFieldValue, e);
            }
        }
        return sourceFieldValue;
    }
}