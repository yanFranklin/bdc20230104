package cn.gtmap.realestate.common.core.dozer.converters.gtmap;

import cn.gtmap.realestate.common.core.dozer.GtmapCompareableCustomConverter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;


/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-21
 * @description 时间戳类型的时间转换成字符串类型
 */
public class TimestampToStringDateCustomConverter implements GtmapCompareableCustomConverter {
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
     * Setter for converter static parameter. Method is guaranteed to be called before the first execution.
     *
     * @param parameter - converter instance, which is injected via custom-converter-param attribute
     */
    @Override
    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    @Override
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {
        Long date = null;
        if(sourceFieldValue != null){
            date = (Long)sourceFieldValue;
        }
        if(date != null && StringUtils.isNotBlank(parameter)){
            FastDateFormat formater = FastDateFormat.getInstance(parameter);
            return formater.format(date);
        }else{
            return "";
        }
    }
}
