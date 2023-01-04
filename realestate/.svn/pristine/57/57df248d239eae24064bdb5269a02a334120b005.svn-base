package cn.gtmap.realestate.exchange.core.dozer.converters;

import cn.gtmap.realestate.common.core.domain.BdcZdDsfzdgxDO;
import cn.gtmap.realestate.common.core.dozer.GtmapCompareableCustomConverter;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.spring.Container;
import org.apache.commons.lang3.StringUtils;
import org.dozer.converters.PrimitiveOrWrapperConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0  2022-11-07
 * @description 有对照配置的， 先第三方字典项对照 先读第三方对照表，如果没有返回原值
 */
public class DozerNvlDsfToBdcCustomConvert implements GtmapCompareableCustomConverter {

    private String parameter;

    private static final Logger LOGGER = LoggerFactory.getLogger(DozerNvlDsfToBdcCustomConvert.class);

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
                        LOGGER.debug("---第三方字典返回值:{},查询参数:{}", result,bdcZdDsfzdgxDO);
                        if (result != null) {
                            return primitiveConverter.convert(result.getBdczdz(), destinationClass, null);
                        } else {
                            LOGGER.debug("---第三方对照表未找到对应的对照值，返回原值:{}", result,sourceFieldValue.toString());
                            return sourceFieldValue;
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
