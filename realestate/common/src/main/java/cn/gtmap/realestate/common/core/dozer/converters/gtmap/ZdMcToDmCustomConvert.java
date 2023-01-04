package cn.gtmap.realestate.common.core.dozer.converters.gtmap;

import cn.gtmap.realestate.common.core.dozer.GtmapCompareableCustomConverter;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.spring.Container;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.converters.PrimitiveOrWrapperConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-06-05
 * @description 读取审核登簿字典服务 做 字典项转换 从名称转换成DM
 */
public class ZdMcToDmCustomConvert implements GtmapCompareableCustomConverter {

    private String param;
    private Object destObject;
    private Object srcObject;
    private static final Logger LOGGER = LoggerFactory.getLogger(ZdMcToDmCustomConvert.class);

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
        try{
            if (srcObject != null && sourceFieldValue != null && StringUtils.isNotBlank(param)) {
                Object bean = Container.getBean(BdcZdFeignService.class);
                if(bean != null){
                    BdcZdFeignService zdService = (BdcZdFeignService) bean;
                    List<Map> zdMapList = zdService.queryBdcZd(param);
                    Object value = null;
                    for(Map map : zdMapList){
                        if(StringUtils.equals(sourceFieldValue.toString(),MapUtils.getString(map,"MC"))){
                            value = map.get("DM");
                            break;
                        }
                    }
                    if(value != null){
                        return primitiveConverter.convert(value, destinationClass, null);
                    }
                }
            }
            return primitiveConverter.convert(sourceFieldValue, destinationClass, null);
        } catch (Exception e){
            LOGGER.error("字典转换失败",e);
        }
        return existingDestinationFieldValue;
    }
}
