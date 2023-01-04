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
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-06-05
 * @description 读取审核登簿字典服务 做 字典项转换 从DM转换成MC
 */
public class SlZdDmToMcCustomConvert implements GtmapCompareableCustomConverter {

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
            Object bean = Container.getBean(BdcSlZdFeignService.class);
            if(bean != null){
                BdcSlZdFeignService zdService = (BdcSlZdFeignService) bean;
                List<Map> zdMapList = zdService.queryBdcSlzd(param);
                Object value = null;
                for(Map map : zdMapList){
                    if(StringUtils.equals(sourceFieldValue.toString(), MapUtils.getString(map,"DM"))){
                        value = map.get("MC");
                        break;
                    }
                }
                if(value != null){
                    return primitiveConverter.convert(value, destinationClass, new DateFormatContainer(""));
                }
            }
        }
        return primitiveConverter.convert(sourceFieldValue, destinationClass, new DateFormatContainer(""));
    }
}
