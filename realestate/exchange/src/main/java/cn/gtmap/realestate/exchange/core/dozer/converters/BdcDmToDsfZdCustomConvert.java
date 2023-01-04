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
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-07-25
 * @description
 */
public class BdcDmToDsfZdCustomConvert implements GtmapCompareableCustomConverter {

    private String parameter;

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcDmToDsfZdCustomConvert.class);

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
        try{
            if (sourceFieldValue != null &&StringUtils.isNotBlank(parameter) && parameter.contains(",") && sourceFieldValue != null) {
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
                        bdcZdDsfzdgxDO.setBdczdz(sourceFieldValue.toString());
                        bdcZdDsfzdgxDO.setDsfxtbs(xtbs);
                        BdcZdDsfzdgxDO result = zdService.dsfZdgx(bdcZdDsfzdgxDO);
                        if (result != null) {
                            return primitiveConverter.convert(result.getDsfzdz(), destinationClass, null);
                        }
                    }
                }
            }
            return primitiveConverter.convert(sourceFieldValue, destinationClass, null);
        }catch (Exception e){
            LOGGER.error("第三方字典转换失败",e);
        }
        return existingDestinationFieldValue;
    }
}
