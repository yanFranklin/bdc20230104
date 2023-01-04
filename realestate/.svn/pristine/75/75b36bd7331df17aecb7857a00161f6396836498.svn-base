package cn.gtmap.realestate.exchange.core.dozer.converters;

import cn.gtmap.realestate.common.core.dozer.GtmapCompareableCustomConverter;
import cn.gtmap.realestate.common.core.ex.AppException;
import org.apache.commons.lang.math.NumberUtils;

import java.util.regex.Pattern;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0 2021-07-09
 * @description
 */
public class StringregularNumberCustomConvert implements GtmapCompareableCustomConverter {
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

    }

    @Override
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {
        if (sourceFieldValue != null) {
            String str = sourceFieldValue.toString();
            //正则，过滤字符，只剩数字
            String REGEX = "[^(0-9)]";
            Pattern pattern = Pattern.compile(REGEX);
            String ticket = pattern.matcher(str).replaceAll("").trim();
            if (NumberUtils.isNumber(ticket) && str.contains(".")) {
                Double db = Double.parseDouble(ticket);
                return db.intValue();
            } else if (NumberUtils.isNumber(ticket)) {
                return Integer.parseInt(ticket);
            }else {
                throw new AppException("改字符串无法转换为数字!");
            }

        }
        return sourceFieldValue;
    }
}
