package cn.gtmap.realestate.exchange.core.dozer.converters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.dozer.converters.PrimitiveOrWrapperConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.gtmap.realestate.common.core.dozer.GtmapCompareableCustomConverter;
import cn.gtmap.realestate.common.core.dozer.converters.gtmap.DefaultValueCustomConvert;
import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;

/**
 * @author <a href="mailto:xuzhou@gtmap.cn">xuhzou</a>
 * @version 1.0  2022-06-13
 * @description 查封登记权利类型 转成List
 */
public class ApplicationConfigCfdjQllxListConvert implements GtmapCompareableCustomConverter {
    private String param;
    private Object destObject;
    private Object srcObject;
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultValueCustomConvert.class);
    private final PrimitiveOrWrapperConverter primitiveConverter = new PrimitiveOrWrapperConverter();

    @Override
    public void setDestinationObject(Object destinationObject) {
        destObject = destinationObject;
    }

    @Override
    public void setSourceObject(Object sourceObject) {
        srcObject = sourceObject;
    }

    @Override
    public void setParameter(String parameter) {
        param = parameter;
    }

    /**
     * @param existingDestinationFieldValue
     * @param sourceFieldValue
     * @param destinationClass
     * @param sourceClass
     * @return
     */
    @Override
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {
        if (StringUtils.isNotBlank(param)) {
            String qllxs = EnvironmentConfig.getEnvironment().getProperty(param);
            if (StringUtils.isNotBlank(qllxs)) {
                String[] arr = qllxs.split(",");
                return Arrays.asList(arr);
            }
        }
        List<String> defaultResult = new ArrayList<>(3);
        defaultResult.add("4");
        defaultResult.add("6");
        defaultResult.add("8");
        return defaultResult;
    }
}
