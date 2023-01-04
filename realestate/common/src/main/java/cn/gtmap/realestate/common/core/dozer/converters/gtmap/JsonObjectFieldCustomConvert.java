package cn.gtmap.realestate.common.core.dozer.converters.gtmap;

import cn.gtmap.realestate.common.core.dozer.GtmapCompareableCustomConverter;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.dozer.converters.DateFormatContainer;
import org.dozer.converters.PrimitiveOrWrapperConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019-06-14
 * @description jsonobject数据通过key取值
 */
public class JsonObjectFieldCustomConvert implements GtmapCompareableCustomConverter {

    private String param;
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonObjectFieldCustomConvert.class);
    private final PrimitiveOrWrapperConverter primitiveConverter = new PrimitiveOrWrapperConverter();
    private Object srcObject;


    /**
     * @param existingDestinationFieldValue
     * @param sourceFieldValue
     * @param destinationClass
     * @param sourceClass
     * @return
     */
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {
        Object result=null;
        if (StringUtils.isNotBlank(param) && srcObject!=null) {
            try {
                JSONObject jsonObject=(JSONObject)srcObject;
                String[] fields=param.split("\\.");
                for(String str:fields){
                    if(jsonObject.containsKey(str)){
                        result=jsonObject.get(str);
                        if(result instanceof JSONObject){
                            jsonObject=(JSONObject)result;
                        }
                    }else{
                        result=null;
                    }
                }
                return primitiveConverter.convert(result, destinationClass, new DateFormatContainer(""));
            } catch (Exception e) {
                LOGGER.error("JsonObject转换错误", e);
            }
        }
        return result;
    }


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
        srcObject = sourceObject;
    }

    @Override
    public void setParameter(String parameter) {
        param = parameter;
    }

}
