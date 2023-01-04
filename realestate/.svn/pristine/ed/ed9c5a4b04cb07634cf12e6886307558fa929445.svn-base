package cn.gtmap.realestate.common.core.dozer.converters.gtmap;

import cn.gtmap.realestate.common.core.dozer.GtmapCompareableCustomConverter;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.dozer.converters.DateFormatContainer;
import org.dozer.converters.PrimitiveOrWrapperConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @Author  <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
 * @Description 对面积进行赋值方式
 * @Date 2022/5/7
 **/
public class GetMjJsonObjectCustomConverter implements GtmapCompareableCustomConverter {
    private String param;
    private Object destObject;
    private Object srcObject;
    private static final Logger LOGGER = LoggerFactory.getLogger(GetMjJsonObjectCustomConverter.class);
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

    @Override
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {
        if (srcObject != null && StringUtils.isNotBlank(param)) {
            try {
                JSONObject jsonObject=(JSONObject)srcObject;
                // class.filed
                String[] classFileds=param.split(",");
                for (String classFiled : classFileds) {
                    String[] split = classFiled.split("\\.");
                    String c = split[0];
                    String f = split[1];
                    if (!jsonObject.containsKey(c)){
                        continue;
                    }
                    JSONObject subJsonObject = jsonObject.getJSONObject(c);
                    if (Objects.nonNull(subJsonObject) || subJsonObject.containsKey(f)) {
                        return primitiveConverter.convert(subJsonObject.getDouble(f), destinationClass, new DateFormatContainer(""));
                    }
                }
            } catch (Exception e) {
                LOGGER.error("JsonObject转换错误", e);
            }
        }
        return existingDestinationFieldValue;
    }
}
