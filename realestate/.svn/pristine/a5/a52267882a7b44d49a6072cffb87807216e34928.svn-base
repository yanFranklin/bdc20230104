package cn.gtmap.realestate.common.core.dozer.converters.gtmap;

import cn.gtmap.realestate.common.core.dozer.GtmapCompareableCustomConverter;
import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @version 1.0  2022-05-07
 * @description 从配置文件中根据区县代码读取常量
 */
public class ApplicationConfigQxdmConvert implements GtmapCompareableCustomConverter {

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

    /**
     * 根据区县代码获取配置项
     * @param existingDestinationFieldValue
     * @param sourceFieldValue 区县代码，<a>qxdm</a>
     * @param destinationClass
     * @param sourceClass
     * @return 配置项数据 例如 （fs.billTypeCode.340124 = test2，fs.billTypeCode = test1，如果区县代码为340124，返回test2；区县代码为空，返回test1；区县代码为340000且未配置，返回test1）
     */
    @Override
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {
        // parameter为配置项，sourceFieldValue为区县代码
        Object configValue = null;
        if(StringUtils.isNotBlank(parameter)){
            if (null != sourceFieldValue){
                configValue =  EnvironmentConfig.getEnvironment().getProperty(parameter+ "." + sourceFieldValue);
                if (configValue == null){
                    configValue = EnvironmentConfig.getEnvironment().getProperty(parameter);
                }
            }else{
                return EnvironmentConfig.getEnvironment().getProperty(parameter);
            }
        }
        return configValue;
    }
}
