package cn.gtmap.realestate.common.core.dozer.converters.gtmap;

import cn.gtmap.realestate.common.core.dozer.GtmapCompareableCustomConverter;
import org.apache.commons.lang3.StringUtils;


/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-21
 * @description 将当前时间转换为时间戳类型
 */
public class NewDateToTimestampCustomConverter implements GtmapCompareableCustomConverter {

    private String parameter;

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
     *
     * @param parameter
     */
    @Override
    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    @Override
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {
        //默认到毫秒,如果传参s 精确到秒
        if(StringUtils.equals("s",parameter)){
            return String.valueOf(System.currentTimeMillis() / 1000);
        }
        return String.valueOf(System.currentTimeMillis());
    }
}
