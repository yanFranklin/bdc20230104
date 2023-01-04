package cn.gtmap.realestate.exchange.core.dozer.converters;

import cn.gtmap.realestate.common.core.domain.BdcZdDsfzdgxDO;
import cn.gtmap.realestate.common.core.dozer.GtmapCompareableCustomConverter;
import cn.gtmap.realestate.common.core.dozer.converters.gtmap.DefaultValueCustomConvert;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlSqrSwDTO;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.spring.Container;
import net.bytebuddy.asm.Advice.This;

import org.apache.commons.lang3.StringUtils;
import org.docx4j.model.datastorage.XPathEnhancerParser.primaryExpr_return;
import org.dozer.converters.DateFormatContainer;
import org.dozer.converters.PrimitiveOrWrapperConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0  2020-10-15
 * @description 南通推送税务 房屋套次字典对照转换
 */
public class FwtcCustomConvert implements GtmapCompareableCustomConverter {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultValueCustomConvert.class);
    
    private String parameter;
    private Object destObject;
    private Object srcObject;
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
        this.parameter = parameter;
    }

    @Override
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {
        try{
            if(StringUtils.isBlank(parameter)) {
                return primitiveConverter.convert(sourceFieldValue, destinationClass, null);
            }
            
            if (sourceFieldValue != null) {
                Object bean = Container.getBean(BdcZdFeignService.class);
                if (bean != null) {
                    BdcZdFeignService zdService = (BdcZdFeignService) bean;
                    BdcZdDsfzdgxDO bdcZdDsfzdgxDO = new BdcZdDsfzdgxDO();
                    bdcZdDsfzdgxDO.setZdbs("BDC_SL_ZD_FWTC");
                    bdcZdDsfzdgxDO.setBdczdz(sourceFieldValue.toString());
                    bdcZdDsfzdgxDO.setDsfxtbs(parameter.split(",")[0]);
                    BdcZdDsfzdgxDO result = zdService.dsfZdgx(bdcZdDsfzdgxDO);
                    
                    if (result != null && StringUtils.isNotBlank(result.getDsfzdz())) {
                        return primitiveConverter.convert(result.getDsfzdz(), destinationClass, null);
                    } else {
                        // 如果字典对照没有匹配上同时 dozer 传递了默认值采用默认值
                        return this.defaultValue(destinationClass);
                    }
                }
            } else {
                // 原字段没值，直接设置默认值
                return this.defaultValue(destinationClass);
            }
        } catch (Exception e){
            LOGGER.error("BDC_SL_ZD_FWTC字典转换失败", e);
        }
        
        return existingDestinationFieldValue;
    }
    
    private Object defaultValue(Class<?> destinationClass) {
        return primitiveConverter.convert(String.valueOf(parameter.split(",")[1]), destinationClass, null);
    }
}
