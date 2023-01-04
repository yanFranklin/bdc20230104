package cn.gtmap.realestate.exchange.core.dozer.converters;

import cn.gtmap.realestate.common.core.dozer.GtmapCompareableCustomConverter;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.exchange.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 1.0  2020-06-30
 * @description DATE格式的String 转换为其他格式
 */
public class FormatDateStringCustomConverter implements GtmapCompareableCustomConverter {
    private static final Logger LOGGER = LoggerFactory.getLogger(FormatDateStringCustomConverter.class);
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
        try {
            if (sourceFieldValue != null) {
                date = DateUtils.formatDate(sourceFieldValue.toString());
            }
            if (StringUtils.isBlank(parameter)) {
                parameter = "yyyy-MM-dd HH:mm:ss";
            }
            if (date != null && StringUtils.isNotBlank(parameter)) {
                FastDateFormat formater = FastDateFormat.getInstance(parameter);
                return formater.format(date);
            }
        } catch (Exception e) {
            LOGGER.info("", e);
        }
        return "";
    }
}
