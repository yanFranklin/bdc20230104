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
 * @descriptionv 某属性的值经过第三方字典对照成不动产值后，等于第二个值 就进行读取否则不读取
 */
public class OtherFelidDsfToBdcDmEqualsElseNotCustomConvert implements GtmapCompareableCustomConverter {
    private String param;
    private Object destObject;
    private Object srcObject;

    private static final Logger LOGGER = LoggerFactory.getLogger(OtherFelidDsfToBdcDmEqualsElseNotCustomConvert.class);
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
                if (str.length == 4) {
                    //属性name
                    String equalsName = str[0];
                    //第三方字典表
                    String zdTable = str[1];
                    //标识
                    String xtbs = str[2];
                    //等于的值
                    String equalsValue = str[3];
                    //取第1个数据为字段
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(equalsName, srcObject.getClass());
                    Method get = propertyDescriptor.getReadMethod();
                    if (get.invoke(srcObject) != null) {
                        String bdczdz ="";
                        Object bean = Container.getBean(BdcZdFeignService.class);
                        if (bean != null &&get.invoke(srcObject) != null) {
                            BdcZdFeignService zdService = (BdcZdFeignService) bean;
                            BdcZdDsfzdgxDO bdcZdDsfzdgxDO = new BdcZdDsfzdgxDO();
                            bdcZdDsfzdgxDO.setZdbs(zdTable);
                            bdcZdDsfzdgxDO.setDsfzdz(get.invoke(srcObject).toString());
                            bdcZdDsfzdgxDO.setDsfxtbs(xtbs);
                            BdcZdDsfzdgxDO result = zdService.dsfZdgx(bdcZdDsfzdgxDO);
                            if (result != null) {
                                bdczdz= result.getBdczdz();
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
                                        bdczdz= value.toString();
                                    }
                                }
                            }
                        }
                        if(bdczdz != null &&bdczdz.equals(equalsValue)) {
                            return sourceFieldValue;
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
}
