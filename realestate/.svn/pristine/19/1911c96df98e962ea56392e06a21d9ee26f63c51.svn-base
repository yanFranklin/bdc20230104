package cn.gtmap.realestate.common.core.dozer.converters.gtmap;

import cn.gtmap.realestate.common.core.dozer.GtmapCompareableCustomConverter;
import org.apache.commons.lang.math.NumberUtils;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-06-05
 * @description
 */
public class StringParseIntegerCustomConvert implements GtmapCompareableCustomConverter {
    /**
     * Setter for converter static parameter. Method is guaranteed to be called before the first execution.
     *
     * @param destinationObject
     */
    @Override
    public void setDestinationObject(Object destinationObject) {

    }

    @Override
    public void setSourceObject(Object sourceObject) {

    }

    /**
     * Setter for converter static parameter. Method is guaranteed to be called before the first execution.
     *
     * @param parameter - converter instance, which is injected via custom-converter-param attribute
     */
    @Override
    public void setParameter(String parameter) {

    }

    @Override
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {
        if(sourceFieldValue != null){
            String str = sourceFieldValue.toString();
            if(NumberUtils.isNumber(str) && str.contains(".")){
                Double db = Double.parseDouble(str);
                return db.intValue();
            }
            if(NumberUtils.isNumber(str)){
                return Integer.parseInt(str);
            }
        }
        return sourceFieldValue;
    }
}
