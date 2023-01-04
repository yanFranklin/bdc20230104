package cn.gtmap.realestate.exchange.core.dozer.converters;

import cn.gtmap.realestate.common.core.domain.BdcZdDsfzdgxDO;
import cn.gtmap.realestate.common.core.dozer.GtmapCompareableCustomConverter;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.converters.PrimitiveOrWrapperConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/9/21 16:07
 * @description 根据当前登录用户获取设备号
 */
public class AssignDeviceByUsernameCustomConvert implements GtmapCompareableCustomConverter {
    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(AssignDeviceByUsernameCustomConvert.class);

    private final PrimitiveOrWrapperConverter primitiveConverter = new PrimitiveOrWrapperConverter();

    private String parameter;

    @Override
    public void setDestinationObject(Object destinationObject) {

    }

    @Override
    public void setSourceObject(Object sourceObject) {

    }

    @Override
    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    @Override
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {
        UserManagerUtils userManagerUtils = Container.getBean(UserManagerUtils.class);
        String username = userManagerUtils.getCurrentUserName();
        try{
            if (StringUtils.isNotBlank(parameter) && parameter.contains(",")) {
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
                        bdcZdDsfzdgxDO.setBdczdz(username);
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
