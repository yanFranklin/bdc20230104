package cn.gtmap.realestate.exchange.core.dozer.converters;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.dozer.GtmapCompareableCustomConverter;
import cn.gtmap.realestate.common.core.dozer.converters.gtmap.DefaultValueCustomConvert;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import org.dozer.converters.PrimitiveOrWrapperConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-02
 * @description 当前用户名姓名
 */
public class CurrentUserAliasCustomConvert implements GtmapCompareableCustomConverter {

    private String param;
    private Object destObject;
    private Object srcObject;
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultValueCustomConvert.class);
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
        UserManagerUtils userManagerUtils = Container.getBean(UserManagerUtils.class);
        UserDto user = userManagerUtils.getCurrentUser();
        if(user != null){
            return user.getAlias();
        }
        return "李啊";
    }
}
