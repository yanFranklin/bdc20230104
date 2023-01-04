package cn.gtmap.realestate.exchange.core.dozer.converters;

import cn.gtmap.realestate.common.core.dozer.GtmapCompareableCustomConverter;

import java.math.BigDecimal;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-07-25
 * @description 强转为 String 类型
 */
public class CastStringCustomConvert implements GtmapCompareableCustomConverter {
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
        //如果是BigDecimal格式，返回不是科学计数法
        if(sourceFieldValue instanceof BigDecimal){
            return ((BigDecimal) sourceFieldValue).toPlainString();
        }
        if(sourceFieldValue != null){
            return sourceFieldValue.toString();
        }
        return null;
    }
}
