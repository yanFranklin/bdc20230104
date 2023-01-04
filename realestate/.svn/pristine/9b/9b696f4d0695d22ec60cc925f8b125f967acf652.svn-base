package cn.gtmap.realestate.init.converters;

import cn.gtmap.realestate.common.core.dozer.GtmapCompareableCustomConverter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:chenchunxue@gtmap.cn">chenchunxue</a>
 * @version 1.0, 2020/08/20
 * @descriptionv 根据不动产单元号中的特征码做对照
 */
public class ConvertByBdcdyh implements GtmapCompareableCustomConverter {
    // 用，分开  第一个传取不动产单元号的第几位   后面参数类似于键值对
    // 例如：13,G,1,J,2    取不动产单元号第13位 如果为G返回1  如果为J返回2
    private String param;
    private Object destObject;
    private Object srcObject;
    private static final Logger LOGGER = LoggerFactory.getLogger(ConvertByBdcdyh.class);

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
        if(sourceFieldValue != null && sourceFieldValue.toString().length()==28 && param!=null){
            String tzm ="";
            String[] params = StringUtils.split(param, ",");
            try{
                // 第一个参数为取不动产单元号的第几位
                int index = Integer.parseInt(params[0]);
                // 获取不动产单元号的特征码
                tzm = sourceFieldValue.toString().substring(index-1,index);
            }catch (Exception e){
                LOGGER.error("获取不动产单元号特征码异常：",e);
            }
            try {
                int i = 1;
                while (i < params.length - 1) {
                    if (StringUtils.equals(tzm, params[i])) {
                        return Integer.parseInt(params[i + 1]);
                    }
                    i = i + 2;
                }
            }catch (Exception e){
                LOGGER.error("根据不动产单元号中的特征码对照异常：",e);
            }
        }
        return existingDestinationFieldValue;
    }
}
