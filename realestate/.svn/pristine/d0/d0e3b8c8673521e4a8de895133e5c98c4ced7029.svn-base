package cn.gtmap.realestate.exchange.core.dozer.converters;

import cn.gtmap.realestate.common.core.dozer.GtmapCompareableCustomConverter;
import cn.gtmap.realestate.common.core.dozer.converters.gtmap.DefaultValueCustomConvert;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.dozer.converters.PrimitiveOrWrapperConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
 * @Description //取list中的第一个中某字段
 * @Date 2022/5/17 11:10
 **/
public class ListOneCustomConvert implements GtmapCompareableCustomConverter{

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
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue,
                          Class<?> destinationClass, Class<?> sourceClass) {
        if(StringUtils.isNotBlank(param) && srcObject != null){
            try {
                String[] split = param.split("\\.");
                JSONObject srcJsonObject = (JSONObject) srcObject;
                return srcJsonObject.getJSONArray(split[0]).getJSONObject(0).get(split[1]);
            } catch (Exception e) {
                e.getMessage();
            }
        }
        return null;
    }
}
