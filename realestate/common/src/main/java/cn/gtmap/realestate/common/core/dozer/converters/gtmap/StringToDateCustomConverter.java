package cn.gtmap.realestate.common.core.dozer.converters.gtmap;

import cn.gtmap.realestate.common.core.dozer.GtmapCompareableCustomConverter;
import cn.gtmap.realestate.common.util.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-06-12
 * @description String 类型 转Date类型（可自定义转换模板） 默认 yyyy-MM-dd hh:mm:ss
 */
public class StringToDateCustomConverter implements GtmapCompareableCustomConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger(StringToDateCustomConverter.class);


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
        Date result = null;
        if (sourceFieldValue != null && sourceClass.isAssignableFrom(String.class)) {
            try {
                if (StringUtils.isNotBlank(parameter)) {
                    result = new SimpleDateFormat(parameter).parse(sourceFieldValue.toString());
                } else {
                    // 使用 yyyy-MM-dd hh:mm:ss
                    result = new SimpleDateFormat(DateUtils.sdf_ymdhms).parse(sourceFieldValue.toString());
                }
            } catch (Exception e) {
                LOGGER.info("日期转换失败：{},转换格式：{}", sourceFieldValue.toString(), StringUtils.defaultString(parameter, DateUtils.sdf_ymdhms));
            }
            if (result == null) {
                // 如有异常  使用yyyy-MM-dd
                result = DateUtils.formatDate(sourceFieldValue.toString());
            }
        }
        return result;
    }
}
