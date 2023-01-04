package cn.gtmap.realestate.exchange.core.dozer.converters;

import cn.gtmap.realestate.common.core.dozer.GtmapCompareableCustomConverter;
import org.apache.commons.collections.CollectionUtils;
import org.dozer.converters.DateFormatContainer;
import org.dozer.converters.PrimitiveOrWrapperConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/8/12.
 * @description 判断集合是不是为空  返回  true false 对应的枚举值
 * 空判断属性是否已存在值，存在返回已存在的值，不存在返回false 非空返回true
 */

public class CheckListIsEmptyAndExistFieldValueCustomConverters implements GtmapCompareableCustomConverter {

    private String param;
    private Object destObject;
    private Object srcObject;
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckListIsEmptyAndExistFieldValueCustomConverters.class);
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
        String[] params = param.split(",");
        String trueFlag = params[0];
        String falseFlag = params[1];
        if(sourceFieldValue != null){
            List temp = (List)sourceFieldValue;
            if(CollectionUtils.isNotEmpty(temp)){
                return primitiveConverter.convert(trueFlag, destinationClass, new DateFormatContainer(""));
            }
        }else if(existingDestinationFieldValue != null){
            return existingDestinationFieldValue;
        }
        return primitiveConverter.convert(falseFlag, destinationClass, new DateFormatContainer(""));
    }
}

