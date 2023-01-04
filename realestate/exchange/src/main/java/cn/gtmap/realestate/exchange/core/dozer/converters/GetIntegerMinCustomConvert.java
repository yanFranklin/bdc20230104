package cn.gtmap.realestate.exchange.core.dozer.converters;

import cn.gtmap.realestate.common.core.dozer.GtmapCompareableCustomConverter;
import cn.gtmap.realestate.exchange.config.dozer.ExchangeJsonPropertyDescriptor;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.converters.PrimitiveOrWrapperConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2020-03-03
 * @description 比较两个值的 自定义转换器
 * param 为 属性 属性名 用逗号隔开，取最小值 为返回值
 */
public class GetIntegerMinCustomConvert implements GtmapCompareableCustomConverter {

    private String param;
    private static final Logger LOGGER = LoggerFactory.getLogger(GetIntegerMinCustomConvert.class);
    private final PrimitiveOrWrapperConverter primitiveConverter = new PrimitiveOrWrapperConverter();
    private Object srcObject;
    private Object destObject;

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
        if (StringUtils.isNotBlank(param) && srcObject!= null) {
            String[] arr = param.split(",");
            List<Integer> numList = new ArrayList();
            JSONObject jsonObject = null;
            if(srcObject instanceof JSONObject){
                jsonObject = (JSONObject)srcObject;
            }else{
                jsonObject = JSONObject.parseObject(JSONObject.toJSONString(srcObject));
            }
            for(int i = 0 ; i < arr.length;i++){
                ExchangeJsonPropertyDescriptor propertyDescriptor = new ExchangeJsonPropertyDescriptor(JSONObject.class, arr[i]);
                Object val = propertyDescriptor.getPropertyValue(jsonObject);
                if(val != null && NumberUtils.isNumber(val.toString()) ){
                    numList.add(Integer.parseInt(val.toString()));
                }
            }
            if(numList.size() > 0){
                int[] temp = new int[numList.size()];
                for(int i = 0 ;i < numList.size() ;i++){
                    temp[i] = numList.get(i);
                }
                return NumberUtils.min(temp);
            }
        }
        return existingDestinationFieldValue;
    }
}
