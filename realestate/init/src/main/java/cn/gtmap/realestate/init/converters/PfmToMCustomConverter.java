package cn.gtmap.realestate.init.converters;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dozer.GtmapCompareableCustomConverter;
import cn.gtmap.realestate.common.core.dozer.converters.gtmap.GetMjCustomConverter;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.CommonUtil;
import cn.gtmap.realestate.init.util.CommonUtils;
import cn.gtmap.realestate.init.util.Constants;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.converters.DateFormatContainer;
import org.dozer.converters.PrimitiveOrWrapperConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/9/11.
 * @description 面积平方米转成亩   只适用于承包经营权
 */
public class PfmToMCustomConverter implements GtmapCompareableCustomConverter {
    private String param;
    private Object destObject;
    private Object srcObject;
    private static final Logger LOGGER = LoggerFactory.getLogger(GetMjCustomConverter.class);
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
        Object returnResult;
        if (srcObject != null && StringUtils.isNotBlank(param)) {
            String[] strMj = StringUtils.split(param, ",");
            try {
                for (int i = 0; i < strMj.length; i++) {
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(strMj[i], srcObject.getClass());
                    Method get = propertyDescriptor.getReadMethod();
                    if (get.invoke(srcObject) != null && Double.parseDouble(get.invoke(srcObject).toString()) > 0) {
                        returnResult = primitiveConverter.convert(get.invoke(srcObject), destinationClass, new DateFormatContainer(""));
                        if(CommonUtils.isSameQllx(Constants.PFM_ZH_M,destObject) && CommonConstantUtils.MJDW_PFM.equals(Integer.valueOf(sourceFieldValue.toString()))){
                            returnResult = CommonUtil.convertPfmToM((double)returnResult);
                        }
                        return returnResult;
                    }
                }
            } catch (Exception e) {
                LOGGER.error("获得目标源面积数据报错", e);
            }
        }
        return existingDestinationFieldValue;
    }
}
