package cn.gtmap.realestate.exchange.core.dozer.converters;

import cn.gtmap.realestate.common.core.dozer.GtmapCompareableCustomConverter;
import cn.gtmap.realestate.common.core.dozer.converters.gtmap.GetMjCustomConverter;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.exchange.web.rest.ExchangeInterfaceRestController;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.converters.PrimitiveOrWrapperConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;


/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-28
 * @description 请求 exchangeBean 参数  为  beanName,是否将源值作为参数传递
 */
public class SourceServiceCustomConvert implements GtmapCompareableCustomConverter {

    private String param;
    private Object destObject;
    private Object srcObject;
    private static final Logger LOGGER = LoggerFactory.getLogger(GetMjCustomConverter.class);

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

    @Override
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {
        if (StringUtils.isNotBlank(param) && param.contains(",")) {
            String[] arr = param.split(",");
            String beanName = arr[0];
            boolean setRequestObject = BooleanUtils.toBoolean(arr[1]);
            Object bean = Container.getBean(ExchangeInterfaceRestController.class);
            if (bean != null) {
                ExchangeInterfaceRestController restController = (ExchangeInterfaceRestController) bean;
                Object result = restController.requestInterface(beanName,setRequestObject?sourceFieldValue:new HashMap<>());
                if (result != null) {
                    return primitiveConverter.convert(result, destinationClass, null);
                }
            }
        }
        return null;
    }
}
