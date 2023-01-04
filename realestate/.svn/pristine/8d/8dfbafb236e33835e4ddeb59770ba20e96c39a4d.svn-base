package cn.gtmap.realestate.common.core.dozer.converters.gtmap;

import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.dozer.CustomConverter;


/**
 * @author <a herf="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2018/11/8
 * @description 为需要赋值的字段赋UUID
 */
public class UUIDCustomConverter implements CustomConverter {

    /**
     * @param existingDestinationFieldValue
     * @param sourceFieldValue
     * @param destinationClass
     * @param sourceClass
     * @return
     */
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {
        return UUIDGenerator.generate();
    }
}
