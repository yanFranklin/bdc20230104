package cn.gtmap.realestate.common.core.dozer.converters.gtmap;

import cn.gtmap.realestate.common.core.dozer.GtmapCompareableCustomConverter;
import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.converters.DateFormatContainer;
import org.dozer.converters.PrimitiveOrWrapperConverter;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021-10-12
 * @description 从配置文件中读取存在值,以配置文件为准,为空取常量值
 */
public class ApplicationConfigNotNullElseConstantCustomConvert implements GtmapCompareableCustomConverter {

    private String parameter;

    private final PrimitiveOrWrapperConverter primitiveConverter = new PrimitiveOrWrapperConverter();
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
            String[] params = StringUtils.split(parameter, CommonConstantUtils.ZF_YW_DH);
            if(params.length ==2) {

                String configValue = EnvironmentConfig.getEnvironment().getProperty(params[0]);
                String value = StringUtils.isNotBlank(configValue)?configValue:params[1];
                return primitiveConverter.convert(value, destinationClass, new DateFormatContainer(""));
            }
        }
        return null;
    }
}
