package cn.gtmap.realestate.exchange.core.dozer.converters;

import cn.gtmap.realestate.common.core.dozer.GtmapCompareableCustomConverter;
import cn.gtmap.realestate.common.core.dozer.converters.gtmap.DefaultValueCustomConvert;
import com.alibaba.fastjson.JSONObject;
import jodd.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.converters.PrimitiveOrWrapperConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-10-12
 * @description 将List中的某属性 按逗号拼接
 */
public class ListToArrayCustomConvert implements GtmapCompareableCustomConverter{

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
        if(StringUtils.isNotBlank(param) && sourceFieldValue != null){
            List tempList = (List)sourceFieldValue;
            StringBuilder sb = new StringBuilder("");
            if(CollectionUtils.isNotEmpty(tempList)){
                for(Object temp:tempList){
                    JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(temp));
                    if(StringUtil.isNotBlank(jsonObject.getString(param))){
                        sb.append(jsonObject.getString(param)).append(",");
                    }
                }
            }
            String result = sb.toString();
            if(result.endsWith(",")){
                result = result.substring(0,result.length()-1);
            }
            return result;
        }
        return null;
    }
}
