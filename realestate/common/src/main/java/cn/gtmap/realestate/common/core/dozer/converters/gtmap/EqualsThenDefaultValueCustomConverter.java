package cn.gtmap.realestate.common.core.dozer.converters.gtmap;

import cn.gtmap.realestate.common.core.dozer.GtmapCompareableCustomConverter;
import org.apache.commons.lang3.StringUtils;
import org.dozer.converters.DateFormatContainer;
import org.dozer.converters.PrimitiveOrWrapperConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
 * @version 1.0  2021/4/8 18:52
 * @description 属性值等于第一个参数，则给属性赋值第二个参数
 */
public class EqualsThenDefaultValueCustomConverter implements GtmapCompareableCustomConverter {
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

    @Override
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {
        if (sourceFieldValue != null && StringUtils.isNotBlank(param)) {
            String[] strYt = StringUtils.split(param, ",");
            String sourceValueString = sourceFieldValue.toString();
            if(sourceValueString.equals(strYt[0])) {
                return primitiveConverter.convert(strYt[1], destinationClass, new DateFormatContainer(""));
            }
        }
        return null;
    }
}
