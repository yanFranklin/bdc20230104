package cn.gtmap.realestate.exchange.core.dozer.converters;

import cn.gtmap.realestate.common.core.domain.BdcZdDsfzdgxDO;
import cn.gtmap.realestate.common.core.dozer.GtmapCompareableCustomConverter;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.spring.Container;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.converters.PrimitiveOrWrapperConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 1.0, 2020/08/27
 * @descriptionv 某属性的值等于第二个值 就进行读取否则不读取
 */
public class OtherFelidEqualsElseNotCustomConvert implements GtmapCompareableCustomConverter {
    private String param;
    private Object destObject;
    private Object srcObject;

    private static final Logger LOGGER = LoggerFactory.getLogger(OtherFelidEqualsElseNotCustomConvert.class);
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
        if (srcObject != null && StringUtils.isNotBlank(param)) {
            String[] str = StringUtils.split(param, ",");
            try {
                if (str.length == 2) {
                    String equalsName = str[0];
                    String equalsValue = str[1];
                    //取第1个数据为字段
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(equalsName, srcObject.getClass());
                    Method get = propertyDescriptor.getReadMethod();
                    if (get.invoke(srcObject) != null && get.invoke(srcObject).equals(equalsValue)) {
                        return sourceFieldValue;
                    }
                }
            } catch (Exception e) {
                LOGGER.error("获得目标源数据报错", e);
            }
        }
        return existingDestinationFieldValue;
    }

    private static String getZdKeyByZdTable(String zdTable) {
        String[] arr = zdTable.split("_");
        String zdkey = arr[arr.length - 1];
        return StringUtils.lowerCase(zdkey);
    }
}
