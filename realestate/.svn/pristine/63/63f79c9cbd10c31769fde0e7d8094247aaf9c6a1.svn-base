package cn.gtmap.realestate.exchange.core.dozer.converters;

import cn.gtmap.realestate.common.core.dozer.GtmapCompareableCustomConverter;
import cn.gtmap.realestate.common.core.dozer.converters.gtmap.NvlOtherColumnCustomConverter;
import cn.gtmap.realestate.exchange.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.converters.PrimitiveOrWrapperConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-10-24
 * @description 拼接 字段  {0}/{1},szc,zcs
 */
public class JointColumnCustomConvert implements GtmapCompareableCustomConverter {

    private String param;
    private static final Logger LOGGER = LoggerFactory.getLogger(NvlOtherColumnCustomConverter.class);
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
        if (StringUtils.isNotBlank(param)) {
            try {

                String[] arr = param.split(",");
                String format = arr[0];
                List<String> columValueList = new ArrayList<>();
                for(int i = 1;i < arr.length;i++){
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(arr[i], srcObject.getClass());
                    Method get = propertyDescriptor.getReadMethod();
                    Object val = get.invoke(srcObject);
                    String valStr = "";
                    if(val != null){
                        if(val instanceof Date){
                            valStr = DateUtil.formateTimeYmd((Date) val);
                        }else{
                            valStr = val.toString();
                        }
                    }
                    columValueList.add(valStr);
                }
                Object[] objArr = columValueList.toArray();
                return MessageFormat.format(format,objArr);
            } catch (IntrospectionException e) {
                LOGGER.error("获得目标源数据报错", e);
            } catch (IllegalAccessException e) {
                LOGGER.error("获得目标源数据报错", e);
            } catch (InvocationTargetException e) {
                LOGGER.error("获得目标源数据报错", e);
            }
        } else {
            return existingDestinationFieldValue;
        }
        return null;
    }

}