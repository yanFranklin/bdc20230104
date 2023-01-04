package cn.gtmap.realestate.init.converters;

import cn.gtmap.realestate.common.core.dozer.GtmapCompareableCustomConverter;
import cn.gtmap.realestate.common.core.dozer.converters.gtmap.GetMjCustomConverter;
import cn.gtmap.realestate.init.util.CommonUtils;
import cn.gtmap.realestate.init.util.Constants;
import org.apache.commons.lang3.StringUtils;
import org.dozer.converters.PrimitiveOrWrapperConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/9/11.
 * @description 面积单位转换   只适用于承包经营权
 */
public class DwCustomConverter implements GtmapCompareableCustomConverter {
    private String param;
    private Object destObject;
    private Object srcObject;
    private static final Logger LOGGER = LoggerFactory.getLogger(GetMjCustomConverter.class);
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
        if(StringUtils.isNotBlank(param) && CommonUtils.isSameQllx(Constants.PFM_ZH_M,destObject)){
            return Integer.valueOf(param);
        }
        return Integer.valueOf(sourceFieldValue.toString());
    }
}
