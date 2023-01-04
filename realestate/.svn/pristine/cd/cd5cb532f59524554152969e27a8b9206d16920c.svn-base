package cn.gtmap.realestate.common.core.dozer.converters.gtmap;

import cn.gtmap.realestate.common.core.dozer.GtmapCompareableCustomConverter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.util.Date;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-15
 * @description DATE转换String 默认日期格式：yyyy-MM-dd hh:mm:ss
 */
public class DateToStringCustomConverter implements GtmapCompareableCustomConverter {

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
        Date date = null;
        if(sourceFieldValue != null){
            date = (Date)sourceFieldValue;
        }
        if(StringUtils.isBlank(parameter)){
            parameter = "yyyy-MM-dd HH:mm:ss";
        }
        if(date != null && StringUtils.isNotBlank(parameter)){
            FastDateFormat formater = FastDateFormat.getInstance(parameter);
            return formater.format(date);
        }else{
            return "";
        }
    }
}
