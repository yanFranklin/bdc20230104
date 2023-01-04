package cn.gtmap.realestate.exchange.core.dozer.converters;

import cn.gtmap.realestate.common.core.dozer.GtmapCompareableCustomConverter;
import cn.gtmap.realestate.common.core.dozer.converters.gtmap.GetMjCustomConverter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;


/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-06-06
 * @description 过滤条件 使用方式，需要将需要做过滤条件的字段放在最后一个fileld
 */
public class FilterEmptyCustomConvert implements GtmapCompareableCustomConverter {
    private String param;
    private Object destObject;
    private Object srcObject;
    private static final Logger LOGGER = LoggerFactory.getLogger(GetMjCustomConverter.class);


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
        if (srcObject != null) {
            if (sourceFieldValue == null || StringUtils.isBlank(sourceFieldValue.toString())) {
                setNewDestObj();
            }
        }
        return null;
    }

    private void setNewDestObj() {
        try {
            Object newDest = destObject.getClass().newInstance();
            BeanUtils.copyProperties(newDest, destObject);
        } catch (InstantiationException e) {
            LOGGER.error("实例化异常", e);
        } catch (IllegalAccessException e) {
            LOGGER.error("实例化异常", e);
        }
    }
}
