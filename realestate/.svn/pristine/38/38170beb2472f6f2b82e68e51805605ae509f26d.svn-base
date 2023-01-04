package cn.gtmap.realestate.exchange.core.dozer.converters;

import cn.gtmap.realestate.common.core.dozer.GtmapCompareableCustomConverter;
import org.apache.commons.collections.CollectionUtils;
import org.dozer.converters.PrimitiveOrWrapperConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-09-19
 * @description 向list 中填充某一属性{一对多} 两个参数  目标List泛型类全名称，目标方法名
 */
public class SetParamToListCustomConvert implements GtmapCompareableCustomConverter {

    private String param;
    private Object destObject;
    private Object srcObject;
    private static final Logger LOGGER = LoggerFactory.getLogger(SetParamToListCustomConvert.class);

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
        if(existingDestinationFieldValue == null || sourceFieldValue == null){
            return null;
        }
        try {
            String[] paramArr = param.split(",");
            // 获取目标List的泛型实体类
            Class tarClass = Class.forName(paramArr[0]);
            Method[] methods = tarClass.getMethods();
            Method method = null;
            for(Method temp : methods){
                if(temp.getName().equals(paramArr[1])){
                    method = temp;
                }
            }
            if(method != null){
                Class[] paramClass = method.getParameterTypes();
                List resultList = (List) existingDestinationFieldValue;
                if(CollectionUtils.isNotEmpty(resultList)){
                    Object value = primitiveConverter.convert(sourceFieldValue,paramClass[0],null);
                    // 向List中填充
                    for(Object temp : resultList){
                        method.invoke(temp,value);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return existingDestinationFieldValue;
    }
}
