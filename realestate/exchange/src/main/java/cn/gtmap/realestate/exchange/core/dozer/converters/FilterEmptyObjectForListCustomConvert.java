package cn.gtmap.realestate.exchange.core.dozer.converters;

import cn.gtmap.realestate.common.core.dozer.GtmapCompareableCustomConverter;
import cn.gtmap.realestate.common.core.dozer.converters.gtmap.GetMjCustomConverter;
import cn.gtmap.realestate.common.util.CheckParameter;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2020-02-27
 * @description 过滤目标集合中 某个参数 为某值   param key,value
 * 注意：当集合需要map-id，则需要先执行对照，再过滤
 */
public class FilterEmptyObjectForListCustomConvert implements GtmapCompareableCustomConverter {
    private String param;
    private Object destObject;
    private Object srcObject;
    private static final Logger LOGGER = LoggerFactory.getLogger(GetMjCustomConverter.class);


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
        List filterList = new ArrayList();
        if(existingDestinationFieldValue != null && existingDestinationFieldValue instanceof List
                && CollectionUtils.isNotEmpty((List)existingDestinationFieldValue)){
            List existingList = (List)existingDestinationFieldValue;
            for(Object temp:existingList){
                if(CheckParameter.checkAnyParameter(temp)){
                    filterList.add(temp);
                }
            }
            if(CollectionUtils.isEmpty(filterList)){
                filterList = null;
            }
        }
        return filterList;
    }

}
