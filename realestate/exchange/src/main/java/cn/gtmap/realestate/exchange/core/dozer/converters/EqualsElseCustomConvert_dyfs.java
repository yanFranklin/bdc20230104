package cn.gtmap.realestate.exchange.core.dozer.converters;

import cn.gtmap.realestate.common.core.dozer.GtmapCompareableCustomConverter;
import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.core.dto.common.DyQlWithXmQlrDTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.dozer.converters.DateFormatContainer;
import org.dozer.converters.PrimitiveOrWrapperConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.text.DecimalFormat;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0, 2018/12/20
 * @descriptionv 某属性的值等于某值走 否则走某属性值
 */
public class EqualsElseCustomConvert_dyfs implements GtmapCompareableCustomConverter {
    private String param;
    private Object destObject;
    private Object srcObject;

    private static final Logger LOGGER = LoggerFactory.getLogger(EqualsElseCustomConvert_dyfs.class);
    private final PrimitiveOrWrapperConverter primitiveConverter = new PrimitiveOrWrapperConverter();
    private final DoubleToStringCustomConvert doubleToStringCustomConvert = new DoubleToStringCustomConvert();

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
        if (srcObject != null && StringUtils.isNotBlank(param)) {

            String version = EnvironmentConfig.getEnvironment().getProperty("data.version");

            if (CommonConstantUtils.SYSTEM_VERSION_HF.equals(version) && srcObject instanceof DyQlWithXmQlrDTO) {
                DyQlWithXmQlrDTO dyQlWithXmQlrDTO = (DyQlWithXmQlrDTO) srcObject;
                String[] str = StringUtils.split(param, ",");
                try {
                    //取第1个数据为字段
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(str[0], dyQlWithXmQlrDTO.getBdcql().getClass());
                    Method get = propertyDescriptor.getReadMethod();
                    if (get.invoke(dyQlWithXmQlrDTO.getBdcql()) != null && get.invoke(dyQlWithXmQlrDTO.getBdcql()).toString().equals(str[1])) {
                        //取前一个数据为字段
                        PropertyDescriptor propertyElse = new PropertyDescriptor(str[2], dyQlWithXmQlrDTO.getBdcql().getClass());
                        Method elseGet = propertyElse.getReadMethod();
                        return primitiveConverter.convert(bigConvert(elseGet.invoke(dyQlWithXmQlrDTO.getBdcql())), destinationClass, new DateFormatContainer(""));
                    } else {
                        //取最后一个数据为字段
                        PropertyDescriptor propertyElse = new PropertyDescriptor(str[3], dyQlWithXmQlrDTO.getBdcql().getClass());
                        Method elseGet = propertyElse.getReadMethod();
                        return primitiveConverter.convert(bigConvert(elseGet.invoke(dyQlWithXmQlrDTO.getBdcql())), destinationClass, new DateFormatContainer(""));
                    }
                } catch (Exception e) {
                    LOGGER.error("获得目标源数据报错", e);
                }
            } else if (CommonConstantUtils.SYSTEM_VERSION_HF.equals(version)) {
                String[] str = StringUtils.split(param, ",");
                try {
                    //取第1个数据为字段
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(str[0], srcObject.getClass());
                    Method get = propertyDescriptor.getReadMethod();
                    if (get.invoke(srcObject) != null && get.invoke(srcObject).toString().equals(str[1])) {
                        //取前一个数据为字段
                        PropertyDescriptor propertyElse = new PropertyDescriptor(str[2], srcObject.getClass());
                        Method elseGet = propertyElse.getReadMethod();
                        return primitiveConverter.convert(bigConvert(elseGet.invoke(srcObject)), destinationClass, new DateFormatContainer(""));
                    } else {
                        //取最后一个数据为字段
                        PropertyDescriptor propertyElse = new PropertyDescriptor(str[3], srcObject.getClass());
                        Method elseGet = propertyElse.getReadMethod();
                        return primitiveConverter.convert(bigConvert(elseGet.invoke(srcObject)), destinationClass, new DateFormatContainer(""));
                    }
                } catch (Exception e) {
                    LOGGER.error("获得目标源数据报错", e);
                }
            } else {
                //除合肥版本外，其他地区目前都取bdbzzqse
                String[] str = StringUtils.split(param, ",");
                if (srcObject instanceof DyQlWithXmQlrDTO) {
                    DyQlWithXmQlrDTO dyQlWithXmQlrDTO = (DyQlWithXmQlrDTO) srcObject;
                    try {
                        //取第1个数据为字段
                        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(str[0], dyQlWithXmQlrDTO.getBdcql().getClass());
                        Method get = propertyDescriptor.getReadMethod();
                        //取前一个数据为字段
                        PropertyDescriptor propertyElse = new PropertyDescriptor(str[2], dyQlWithXmQlrDTO.getBdcql().getClass());
                        Method elseGet = propertyElse.getReadMethod();
                        return primitiveConverter.convert(bigConvert(elseGet.invoke(dyQlWithXmQlrDTO.getBdcql())), destinationClass, new DateFormatContainer(""));

                    } catch (Exception e) {
                        LOGGER.error("获得目标源数据报错", e);
                    }
                }else {
                    try {
                        //取第1个数据为字段
                        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(str[0], srcObject.getClass());
                        Method get = propertyDescriptor.getReadMethod();
                        //取前一个数据为字段
                        PropertyDescriptor propertyElse = new PropertyDescriptor(str[2], srcObject.getClass());
                        Method elseGet = propertyElse.getReadMethod();
                        return primitiveConverter.convert(bigConvert(elseGet.invoke(srcObject)), destinationClass, new DateFormatContainer(""));

                    } catch (Exception e) {
                        LOGGER.error("获得目标源数据报错", e);
                    }
                }
            }

        }
        return existingDestinationFieldValue;
    }

    public Object bigConvert(Object sourceFieldValue) {
        if (sourceFieldValue != null) {
            try {
                Double doubleVal = NumberUtils.createDouble(sourceFieldValue.toString());
                if (doubleVal != null) {
                    if (true) {
                        param = "#.########";
                    }
                    DecimalFormat df = new DecimalFormat(param);
                    return df.format(doubleVal);
                }
            } catch (Exception e) {
                LOGGER.error("double转string失败,{}", sourceFieldValue, e);
            }
        }
        return sourceFieldValue;
    }
}
