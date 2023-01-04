package cn.gtmap.realestate.common.core.dozer.converters.gtmap;

import cn.gtmap.realestate.common.core.dozer.GtmapCompareableCustomConverter;
import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;
import org.apache.commons.lang3.StringUtils;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-06-14
 * @description 从配置文件中读取常量
 */
public class ApplicationConfigCustomWithSplicingParamConvert implements GtmapCompareableCustomConverter {

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
        if(StringUtils.isNotBlank(parameter)){
            return EnvironmentConfig.getEnvironment().getProperty(parameter + sourceFieldValue.toString());
        }
        return null;
    }
}
