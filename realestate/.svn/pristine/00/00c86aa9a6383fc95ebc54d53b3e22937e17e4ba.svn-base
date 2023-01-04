package cn.gtmap.realestate.exchange.config.dozer;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.MappingException;
import org.dozer.fieldmap.FieldMap;
import org.dozer.fieldmap.HintContainer;
import org.dozer.propertydescriptor.MapPropertyDescriptor;
import org.dozer.util.CollectionUtils;
import org.dozer.util.DozerConstants;
import org.dozer.util.MappingUtils;
import org.dozer.util.ReflectionUtils;


/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2020-02-12
 * @description 第三方子系统自定义JSON结构处理器
 */
public class ExchangeJsonPropertyDescriptor extends MapPropertyDescriptor {

    private final String key;

    private Class<?> propertyType;

    public ExchangeJsonPropertyDescriptor(Class<?> clazz,String key){
        super(clazz, "this", false, 0, "put", "get", key, null, null);
        this.key = key;
    }

    public ExchangeJsonPropertyDescriptor(Class<?> clazz, String fieldName, boolean isIndexed, int index, String setMethod, String getMethod, String key, HintContainer srcDeepIndexHintContainer, HintContainer destDeepIndexHintContainer) {
        super(clazz, fieldName, isIndexed, index, setMethod, getMethod, key, srcDeepIndexHintContainer, destDeepIndexHintContainer);
        this.key = key;
    }


    @Override
    public Object getPropertyValue(Object bean) {
        Object result;
        if(bean != null && bean instanceof JSONObject) {
            bean = JSONObject.parseObject(JSONObject.toJSONString(bean));
        }
        if (MappingUtils.isDeepMapping(key)) {
            String[] keys = StringUtils.split(key,DozerConstants.DEEP_FIELD_DELIMITER);
            result = recursionGetDeepFieldValue(bean,keys);
        } else {
            result = invokeReadMethod(bean);
            if (isIndexed) {
                result = MappingUtils.getIndexedValue(result, index);
            }
        }
        return result;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bean
     * @return java.lang.Object
     * @description 递归获取 嵌套值
     */
    private Object recursionGetDeepFieldValue(Object bean,String[] keys){
        if(bean == null){
            return bean;
        }
        String innerKey = keys[0];
        Object result = null;
        // 判断是否是集合 取集合中的值
        if(isIndexed(innerKey)){
            int index = getIndexOfIndexedField(innerKey);
            String collectionKey = innerKey.replace("["+index+"]","");
            Object collection = invokeReadMethod(bean,collectionKey);
            result = MappingUtils.getIndexedValue(collection, index);
        }else{
            result = invokeReadMethod(bean,innerKey);
        }
        if(keys.length == 1){
            // 最后一层如果是集合 则会被dozer处理为去掉[] index为配置中的下标值
            if (isIndexed) {
                result = MappingUtils.getIndexedValue(result, index);
            }
            return result;
        }else{
            String[] nextKeys = ArrayUtils.subarray(keys,1,keys.length);
            return recursionGetDeepFieldValue(result,nextKeys);
        }
    }

    @Override
    public void setPropertyValue(Object bean, Object value, FieldMap fieldMap) {
        if (MappingUtils.isDeepMapping(key)) {
            String[] keys = StringUtils.split(key,DozerConstants.DEEP_FIELD_DELIMITER);
            recursionSetDeepFieldValue(bean, value,keys);
        } else {
            // 单层对照，如果带下标值
            if(isIndexed){
                String collectKey = key + "[" + index + "]";
                String[] tempKeys = new String[]{collectKey};
                recursionSetDeepFieldValue(bean,value,tempKeys);
            }else{
                if (!getPropertyType().isPrimitive() || value != null) {
                    // Check if dest value is already set and is equal to src value. If true, no need to rewrite the dest value
                    try {
                        // We should map null values to create a new key in the map
                        if (value != null && getPropertyValue(bean) == value) {
                            return;
                        }
                    } catch (Exception e) {
                        // if we failed to read the value, assume we must write, and continue...
                    }
                    invokeWriteMethod(bean, value);
                }
            }
        }
    }


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bean
     * @param value
     * @param keys
     * @return void
     * @description 递归存数据
     */
    private void recursionSetDeepFieldValue(Object bean, Object value,String[] keys){
        String curKey = keys[0];
        // 判断是否是集合中的值
        Object destValue = null;
        if(isIndexed(curKey)){
            int index = getIndexOfIndexedField(curKey);
            String collectionKey = curKey.replace("["+index+"]","");
            // 从bean中获取collectionKey对应值
            Object oldValue = invokeReadMethod(bean,collectionKey);
            JSONArray jsonArray = null;
            if(oldValue == null){
                // 如果原值不存在，在bean中先创建一个
                jsonArray = new JSONArray();
                if(keys.length == 1){
                    jsonArray.add(value);
                }else{
                    jsonArray.add(new JSONObject());
                }
                invokeWriteMethod(bean,jsonArray,collectionKey);
            } else if (CollectionUtils.isCollection(oldValue.getClass())) {
                // 不为空时，根据index追加
                jsonArray = (JSONArray) oldValue;
                if (jsonArray.size() == index) {
                    jsonArray.add(new JSONObject());
                }
            }
            if(jsonArray != null && keys.length > 1){
                String[] nextKeys = ArrayUtils.subarray(keys,1,keys.length);
                recursionSetDeepFieldValue(jsonArray.get(index),value,nextKeys);
            }
        }else{
            // 非集合
            // 当是最里一层时
            if(keys.length == 1){
                // 最后一层如果是数组场景 补齐key的下标值 走递归数组处理
                if(isIndexed){
                    String collectKey = curKey + "[" + index + "]";
                    String[] tempKeys = new String[]{collectKey};
                    recursionSetDeepFieldValue(bean,value,tempKeys);
                }else{
                    // 非集合 直接赋值
                    invokeWriteMethod(bean,value,curKey);
                }
            }else{
                // 仍然存在嵌套层级
                // 获取已有数据
                Object oldValue = invokeReadMethod(bean,curKey);
                if(oldValue == null){
                    // 如果已有数据为null，则需要新增
                    oldValue = new JSONObject();
                    invokeWriteMethod(bean,oldValue,curKey);
                }
                // 继续递归处理
                String[] nextKeys = ArrayUtils.subarray(keys,1,keys.length);
                recursionSetDeepFieldValue(oldValue,value,nextKeys);
            }
        }
    }


    private Object invokeReadMethod(Object target,String key){
        if (key == null) {
            throw new MappingException("key must be specified");
        }
        Object result = null;
        try {
            result = ReflectionUtils.invoke(getReadMethod(), target, new Object[]{key});
        } catch (NoSuchMethodException e) {
            MappingUtils.throwMappingException(e);
        }
        return result;
    }

    private static int getIndexOfIndexedField(String fieldName) {
        return Integer.parseInt(fieldName.replaceAll(".*\\[", "").replaceAll("\\]", ""));
    }

    private static boolean isIndexed(String fieldName) {
        return (fieldName != null) && (fieldName.matches(".+\\[\\d+\\]"));
    }

    @Override
    public Class<?> getPropertyType() {
        if (propertyType == null) {
            if(this.destDeepIndexHintContainer != null
                    && JSONArray.class.getName().equals(this.destDeepIndexHintContainer.getHint().getName())){
                propertyType = JSONArray.class;
            }else{
                propertyType = JSONObject.class;
            }
        }
        return propertyType;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param target
     * @param value
     * @param key
     * @return void
     * @description 目标集合
     */
    private void invokeWriteMethod(Object target, Object value,String key){
        if (key == null) {
            throw new MappingException("key must be specified");
        }
        try {
            ReflectionUtils.invoke(getWriteMethod(), target, new Object[]{key, value});
        } catch (NoSuchMethodException e) {
            MappingUtils.throwMappingException(e);
        }
    }

}
