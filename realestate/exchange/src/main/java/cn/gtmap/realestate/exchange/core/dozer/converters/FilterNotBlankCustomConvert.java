package cn.gtmap.realestate.exchange.core.dozer.converters;

import cn.gtmap.realestate.common.core.dozer.GtmapCompareableCustomConverter;
import cn.gtmap.realestate.common.core.dozer.converters.gtmap.GetMjCustomConverter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;


/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 1.0  2019-06-06
 * @description 如果对照的这个值不为空，则清空之前的对照
 */
public class FilterNotBlankCustomConvert implements GtmapCompareableCustomConverter {
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
            if (sourceFieldValue != null && StringUtils.isNotBlank(sourceFieldValue.toString())) {
                setNewDestObj();
            }
        }
        return existingDestinationFieldValue;
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
