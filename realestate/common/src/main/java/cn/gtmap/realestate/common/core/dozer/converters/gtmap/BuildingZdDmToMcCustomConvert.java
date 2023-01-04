package cn.gtmap.realestate.common.core.dozer.converters.gtmap;

import cn.gtmap.realestate.common.core.dozer.GtmapCompareableCustomConverter;
import cn.gtmap.realestate.common.core.service.feign.building.BuildingZdConvertFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.spring.Container;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-07-25
 * @description
 */
public class BuildingZdDmToMcCustomConvert implements GtmapCompareableCustomConverter {

    private String param;
    private Object destObject;
    private Object srcObject;
    private static final Logger LOGGER = LoggerFactory.getLogger(GetMjCustomConverter.class);


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
            String[] paramArr = param.split(",");
            Object bean = Container.getBean(BuildingZdConvertFeignService.class);
            if(bean != null){
                BuildingZdConvertFeignService zdService = (BuildingZdConvertFeignService) bean;
                List<Map> zdMapList = null;
                try {
                    zdMapList = zdService.getZdTable(paramArr[0],Class.forName(paramArr[1]));
                } catch (ClassNotFoundException e) {
                    LOGGER.error("找不到类：{}",param);
                }
                Object value = null;
                for(Map map : zdMapList){
                    if(StringUtils.equals(sourceFieldValue.toString(), MapUtils.getString(map,"DM"))){
                        value = map.get("MC");
                        break;
                    }
                }
                if(value != null){
                    return value;
                }
            }
        }
        return sourceFieldValue;
    }
}
