package cn.gtmap.realestate.exchange.core.dozer.converters;

import cn.gtmap.realestate.common.core.domain.BdcZdDsfzdgxDO;
import cn.gtmap.realestate.common.core.dozer.GtmapCompareableCustomConverter;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.spring.Container;
import com.alibaba.fastjson.JSONObject;
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
 * @version 1.0, 2020/07/14
 * @descriptionv 某属性的值有值 就读取第三方字典值否则不读取
 */
public class ParamFieldNotNullDsfZdDmElseNotCustomConvert implements GtmapCompareableCustomConverter {
    private String param;
    private Object destObject;
    private Object srcObject;

    private static final Logger LOGGER = LoggerFactory.getLogger(ParamFieldNotNullDsfZdDmElseNotCustomConvert.class);
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
        if (srcObject != null && StringUtils.isNotBlank(param) &&sourceFieldValue != null && org.apache.commons.lang.StringUtils.isNotBlank(sourceFieldValue.toString())) {
            String[] str = StringUtils.split(param, ",");
            try {
                if (str.length == 3) {
                    String param = str[0];
                    String zdTable = str[1];
                    String xtbs = str[2];
                    if(srcObject instanceof JSONObject){
                        if(org.apache.commons.lang.StringUtils.isNotBlank(((JSONObject) srcObject).getString(param))){
                            return dsfzdToBdcDm(zdTable,sourceFieldValue,xtbs,destinationClass);

                        }
                    }else {
                        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(param, srcObject.getClass());
                        Method get = propertyDescriptor.getReadMethod();
                        if (get.invoke(srcObject) != null && org.apache.commons.lang.StringUtils.isNotBlank(get.invoke(srcObject).toString())) {
                            return dsfzdToBdcDm(zdTable,sourceFieldValue,xtbs,destinationClass);

                        }
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

    private Object dsfzdToBdcDm(String zdTable,Object sourceFieldValue,String xtbs,Class<?> destinationClass){
        Object bean = Container.getBean(BdcZdFeignService.class);
        if (bean != null) {
            BdcZdFeignService zdService = (BdcZdFeignService) bean;
            BdcZdDsfzdgxDO bdcZdDsfzdgxDO = new BdcZdDsfzdgxDO();
            bdcZdDsfzdgxDO.setZdbs(zdTable);
            bdcZdDsfzdgxDO.setDsfzdz(sourceFieldValue.toString());
            bdcZdDsfzdgxDO.setDsfxtbs(xtbs);
            BdcZdDsfzdgxDO result = zdService.dsfZdgx(bdcZdDsfzdgxDO);
            if (result != null) {
                return primitiveConverter.convert(result.getBdczdz(), destinationClass, null);
            } else {
                // 读我们自己的字典项
                String zdKey = getZdKeyByZdTable(zdTable);
                List<Map> zdMapList = zdService.queryBdcZd(zdKey);
                if (CollectionUtils.isNotEmpty(zdMapList)) {
                    Object value = null;
                    for (Map map : zdMapList) {
                        if (StringUtils.equals(sourceFieldValue.toString(), MapUtils.getString(map, "MC"))) {
                            value = map.get("DM");
                            break;
                        }
                    }
                    if (value != null) {
                        return primitiveConverter.convert(value, destinationClass, null);
                    }
                }
            }
        }
        return null;
    }
}
