package cn.gtmap.realestate.exchange.core.dozer.converters;

import cn.gtmap.realestate.common.core.domain.BdcZdDsfzdgxDO;
import cn.gtmap.realestate.common.core.dozer.GtmapCompareableCustomConverter;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.spring.Container;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.converters.PrimitiveOrWrapperConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-06-14
 * @description 第三方字典项对照 先读第三方对照表，如果没有再读我们自己的字典表
 */
public class DsfZdToBdcMcCustomConvert implements GtmapCompareableCustomConverter {

    private String parameter;

    private static final Logger LOGGER = LoggerFactory.getLogger(DsfZdToBdcMcCustomConvert.class);

    private final PrimitiveOrWrapperConverter primitiveConverter = new PrimitiveOrWrapperConverter();

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

    @Override
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {
        try {
            if (sourceFieldValue != null && StringUtils.isNotBlank(parameter) && parameter.contains(",")) {
                String[] arr = parameter.split(",");
                if (arr.length == 2) {
                    // 字典表名
                    String zdTable = arr[0];
                    String xtbs = arr[1];
                    Object bean = Container.getBean(BdcZdFeignService.class);
                    if (bean != null) {
                        BdcZdFeignService zdService = (BdcZdFeignService) bean;
                        BdcZdDsfzdgxDO bdcZdDsfzdgxDO = new BdcZdDsfzdgxDO();
                        bdcZdDsfzdgxDO.setZdbs(zdTable);
                        bdcZdDsfzdgxDO.setDsfzdz(sourceFieldValue.toString());
                        bdcZdDsfzdgxDO.setDsfxtbs(xtbs);
                        BdcZdDsfzdgxDO result = zdService.dsfZdgx(bdcZdDsfzdgxDO);
//                        LOGGER.info("入参{}",sourceFieldValue.toString());
                        if (result != null) {
//                            LOGGER.info("第三方表：{}", JSON.toJSONString(result));
                            return primitiveConverter.convert(result.getBdczdz(), destinationClass, null);
                        } else {
                            // 读我们自己的字典项
                            String zdKey = getZdKeyByZdTable(zdTable);
                            List<Map> zdMapList = zdService.queryBdcZd(zdKey);
                            if (CollectionUtils.isNotEmpty(zdMapList)) {
//                                LOGGER.info("非第三方表:{}",JSON.toJSONString(zdMapList));
                                Object value = null;
                                for (Map map : zdMapList) {
                                    if (StringUtils.equals(sourceFieldValue.toString(), MapUtils.getString(map, "DM"))) {
                                        value = map.get("MC");
                                        break;
                                    }
                                }
                                if (value != null) {
                                    return primitiveConverter.convert(value, destinationClass, null);
                                }
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            LOGGER.error("第三方字典转换失败",e);
        }
        return existingDestinationFieldValue;
    }

    private static String getZdKeyByZdTable(String zdTable){
        String[] arr = zdTable.split("_");
        String zdkey = arr[arr.length-1];
        return StringUtils.lowerCase(zdkey);
    }
}
