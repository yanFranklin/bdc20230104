package cn.gtmap.realestate.common.core.dozer.converters.gtmap;

import cn.gtmap.realestate.common.core.dozer.GtmapCompareableCustomConverter;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlZdFeignService;
import cn.gtmap.realestate.common.core.support.spring.Container;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.converters.DateFormatContainer;
import org.dozer.converters.PrimitiveOrWrapperConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * @param
 * @return
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @description  读取受理字典,存在默认值,将默认值转换为代码；否则将对照字段名称转换为代码
 */
public class SlZdMcToDmWithDefaultValueCustomConvert implements GtmapCompareableCustomConverter {

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
        if (srcObject != null && sourceFieldValue != null && StringUtils.isNotBlank(param)) {
            String[] arr = param.split(",");
            if(StringUtils.isNotBlank(arr[0])) {
                if (arr.length == 2) {
                    sourceFieldValue = arr[1];
                }
                Object bean = Container.getBean(BdcSlZdFeignService.class);
                if (bean != null) {
                    BdcSlZdFeignService zdService = (BdcSlZdFeignService) bean;
                    List<Map> zdMapList = zdService.queryBdcSlzd(arr[0]);
                    Object value = null;
                    for (Map map : zdMapList) {
                        if (StringUtils.equals(sourceFieldValue.toString(), MapUtils.getString(map, "MC"))) {
                            value = map.get("DM");
                            break;
                        }
                    }
                    if (value != null) {
                        return primitiveConverter.convert(value, destinationClass, new DateFormatContainer(""));
                    }
                }
            }
        }
        return primitiveConverter.convert(sourceFieldValue, destinationClass, new DateFormatContainer(""));
    }
}
