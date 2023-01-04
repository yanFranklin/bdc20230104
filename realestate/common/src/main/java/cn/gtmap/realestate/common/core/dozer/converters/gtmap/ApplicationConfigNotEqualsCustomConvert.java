package cn.gtmap.realestate.common.core.dozer.converters.gtmap;

import cn.gtmap.realestate.common.core.dozer.GtmapCompareableCustomConverter;
import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;
import org.apache.commons.lang3.StringUtils;
import org.dozer.converters.PrimitiveOrWrapperConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/9/16
 * @description 如果配置项等于参数值时不对照
 */
public class ApplicationConfigNotEqualsCustomConvert implements GtmapCompareableCustomConverter {

    private String parameter;

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationConfigNotEqualsCustomConvert.class);

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
        if(StringUtils.isNotBlank(parameter)) {
            try {
                String[] str = StringUtils.split(parameter, ",");
                if (str != null && str.length > 0) {
                    //配置项名称
                    String pzmc = str[0];
                    //配置值
                    String pzz = str[1];
                    if (StringUtils.isNoneBlank(pzmc, pzz)) {
                        String sjz = EnvironmentConfig.getEnvironment().getProperty(pzmc);
                        if (StringUtils.equals(pzz, sjz)) {
                            return existingDestinationFieldValue;
                        }
                    }
                }
            } catch (Exception e) {
                LOGGER.error("获得目标源数据报错", e);
            }
        }
        return  primitiveConverter.convert(sourceFieldValue, destinationClass, null);
    }
}
