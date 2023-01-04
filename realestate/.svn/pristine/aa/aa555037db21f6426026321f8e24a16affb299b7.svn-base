package cn.gtmap.realestate.exchange.core.dozer.converters;

import cn.gtmap.realestate.common.core.dozer.GtmapCompareableCustomConverter;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.dozer.converters.PrimitiveOrWrapperConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-09-19
 * @description 向list 中填充某一属性{一对多} 两个参数  目标List为jsonarray，内部为jsonobject
 */
public class SetParamToJsonArrayCustomConvert implements GtmapCompareableCustomConverter {

    private String param;
    private Object destObject;
    private Object srcObject;
    private static final Logger LOGGER = LoggerFactory.getLogger(SetParamToJsonArrayCustomConvert.class);

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
        if (existingDestinationFieldValue == null || sourceFieldValue == null) {
            return null;
        }
        try {
            if (StringUtils.isNotBlank(param)) {
                String[] params = StringUtils.split(param, ",");
                List resultList = (JSONArray) existingDestinationFieldValue;
                if (CollectionUtils.isNotEmpty(resultList)) {
                    // 向List中填充
                    for (Object temp : resultList) {
                        if (params.length == 1) {
                            ((JSONObject) temp).put(param, sourceFieldValue);
                        } else if (params.length > 1) {
                            FastDateFormat formater = FastDateFormat.getInstance(params[1]);
                            ((JSONObject) temp).put(params[0], formater.format(sourceFieldValue));
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return existingDestinationFieldValue;
    }
}
