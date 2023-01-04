package cn.gtmap.realestate.common.core.dozer.converters.gtmap;

import cn.gtmap.realestate.common.core.dozer.GtmapCompareableCustomConverter;
import org.apache.commons.lang3.StringUtils;
import org.dozer.converters.DateFormatContainer;
import org.dozer.converters.PrimitiveOrWrapperConverter;

/**
 * @author <a herf="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2018/11/19
 * @description 截取字符串 并将截取字符串用0填充到指定位数
 */
public class SubStringAndAddZeroCustomConverter implements GtmapCompareableCustomConverter {
    private String param;
    private final PrimitiveOrWrapperConverter primitiveConverter = new PrimitiveOrWrapperConverter();

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
        if (sourceFieldValue != null && String.class.isAssignableFrom(sourceClass)) {
            if (StringUtils.isNotBlank(param)) {
                String[] strLen = StringUtils.split(param, ",");
                if (strLen.length == 1) {
                    return primitiveConverter.convert(StringUtils.substring(sourceFieldValue.toString(), Integer.parseInt(strLen[0])), destinationClass, new DateFormatContainer(""));
                } else if (strLen.length == 2) {
                    return primitiveConverter.convert(StringUtils.substring(sourceFieldValue.toString(), Integer.parseInt(strLen[0]), Integer.parseInt(strLen[1])), destinationClass, new DateFormatContainer(""));
                } else if (strLen.length == 3) {
                    StringBuilder result = new StringBuilder();
                    result.append(StringUtils.substring(sourceFieldValue.toString(), Integer.parseInt(strLen[0]), Integer.parseInt(strLen[1])));
                    for (int i = 0; i < Integer.parseInt(strLen[2]); i++) {
                        result.append("0");
                    }
                    return primitiveConverter.convert(result.toString(), destinationClass, new DateFormatContainer(""));
                }
            } else {
                return primitiveConverter.convert(sourceFieldValue, destinationClass, new DateFormatContainer(""));
            }
        }
        return existingDestinationFieldValue;
    }

    @Override
    public void setDestinationObject(Object destinationObject) {

    }

    @Override
    public void setSourceObject(Object sourceObject) {

    }

}
