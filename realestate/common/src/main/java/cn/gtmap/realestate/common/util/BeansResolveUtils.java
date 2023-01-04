package cn.gtmap.realestate.common.util;

import cn.gtmap.realestate.common.core.dto.ObjectFieldsDTO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.dozer.metadata.ClassMappingMetadata;
import org.dozer.metadata.FieldMappingMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/11/14
 * @description Spring Bean解析处理工具类
 */
@Component
public class BeansResolveUtils implements ApplicationContextAware {
    /**
     * 日期格式化
     */
    private static String DATE_FORMAT = new String("yyyy-MM-dd HH:mm:ss");
    /**
     * 日志操作
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BeansResolveUtils.class);

    /**
     * BeansResolveUtils限定类名
     */
    private static final String CLASS_NAME = BeansResolveUtils.class.getName();

    /**
     * Spring应用上下文
     */
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext){
        if(null == this.applicationContext){
            this.applicationContext = applicationContext;
        }
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   name bean名称
     * @return  bean对象
     * @description 根据bean名称获取对应bean
     */
    public <T> T getBeanByName(String name){
        if(null == this.applicationContext || StringUtils.isBlank(name)){
            return null;
        }

        return (T) this.applicationContext.getBean(name);
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   beansNameMap
     * @param   key 目标bean对应键值
     * @return  bean对象
     * @description 获取bean名称键值对Map中指定key对应的bean
     */
    public <T> T getBeanOfMapItem(Map<Object, String> beansNameMap, Object key){
        if(MapUtils.isEmpty(beansNameMap) || StringUtils.isBlank(String.valueOf(key))){
            LOGGER.warn("{}：获取配置项Bean集合失败，原因可能：入参Bean名称集合为空、未指定要获取bean对应的键值！", CLASS_NAME);
            return null;
        }

        String beanName = MapUtils.getString(beansNameMap, key);
        return this.getBeanByName(beanName);
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   beansNameList bean名称集合
     * @return  {List} bean集合
     * @description  获取bean名称对应的bean集合
     */
    public <T> List<T> listBeans(List<String> beansNameList){
        if(CollectionUtils.isEmpty(beansNameList)){
            LOGGER.warn("{}：获取配置项Bean集合失败，原因：入参Bean名称集合为空！", CLASS_NAME);
            return Collections.emptyList();
        }

        //存储bean结果集
        List<T> beansList = new ArrayList<>(beansNameList.size());
        for (String beanName : beansNameList){
            if(StringUtils.isNotBlank(beanName)){
                //获取配置项对应bean
                T bean = this.getBeanByName(beanName);
                if(null == bean){
                    LOGGER.warn("{}：获取配置项Bean集合中止，原因：未找到{}对应bean！", CLASS_NAME, beanName);
                    continue;
                }

                beansList.add(bean);
            }
        }

        return beansList;
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   beansNameMap bean名称键值对集合
     * @return  {Map} bean键值对集合
     * @description
     *     <p>
     *        1、获取键值对形式bean名称对应的bean集合
     *        2、key-value中的key为索引，value为bean名称
     *     </p>
     */
    public <T> Map<Object, T> listBeansOfMapType(Map<Object, String> beansNameMap){
        if(MapUtils.isEmpty(beansNameMap)){
            LOGGER.warn("{}：获取配置项Bean集合失败，原因：入参Bean名称集合为空！", CLASS_NAME);
            return Collections.emptyMap();
        }

        //存储bean结果集
        Map<Object, T> beansMap = new HashMap<>(beansNameMap.size());
        for(Map.Entry<Object, String> entry : beansNameMap.entrySet()){
            if(StringUtils.isNotBlank(entry.getValue())){
                //获取配置项对应bean
                T bean = this.getBeanByName(entry.getValue());
                if(null == bean){
                    LOGGER.warn("{}：获取配置项Bean集合中止，原因：未找到{}对应bean！", CLASS_NAME, entry.getValue());
                    continue;
                }

                beansMap.put(entry.getKey(), bean);
            }
        }

        return beansMap;
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param  originObject 源对象
     * @param  targetObject 目标对象
     * @description
     *      将源对象属性值赋值给目标对象
     *    1、相当于属性值对应复制
     *    2、值非空才赋值
     *    3、对应属性名称要一致，有get方法、set方法
     */
    public static void clonePropertiesValue(Object originObject, Object targetObject){
        // 获取源对象所有属性
        Field[] originFields = originObject.getClass().getDeclaredFields();
        List<String> originFieldsNameList = BeansResolveUtils.getClassFieldsName(originFields);

        // 循环设置属性值
        Field[] targetFields = targetObject.getClass().getDeclaredFields();
        try {
            for(Field targetField : targetFields){
                if(originFieldsNameList.contains(targetField.getName())){
                    // 获取源对象属性值
                    Field originField = originObject.getClass().getDeclaredField(targetField.getName());
                    originField.setAccessible(true);
                    Object value = originField.get(originObject);

                    if (StringUtils.isNotBlank(String.valueOf(value)) && Objects.nonNull(value)) {
                        // 赋值给目标属性
                        targetField.setAccessible(true);
                        targetField.set(targetObject, value);
                    }
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e){
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param  originObject 源对象
     * @param  targetObject 目标对象
     * @description
     *      将源对象属性值赋值给目标对象(包括目标对象父类属性)
     */
    public static void clonePropsValueWithParentProps(Object originObject, Object targetObject){
        // 获取源对象所有属性
        Field[] originFields = originObject.getClass().getDeclaredFields();
        List<String> originFieldsNameList = BeansResolveUtils.getClassFieldsName(originFields);

        // 循环设置属性值
        Field[] targetFields = targetObject.getClass().getDeclaredFields();
        Field[] parentFields = targetObject.getClass().getSuperclass().getDeclaredFields();
        targetFields = ArrayUtils.addAll(targetFields, parentFields);

        try {
            for(Field targetField : targetFields){
                if(originFieldsNameList.contains(targetField.getName()) && !Modifier.isStatic(targetField.getModifiers())){
                    // 获取源对象属性值
                    Field originField = originObject.getClass().getDeclaredField(targetField.getName());
                    originField.setAccessible(true);
                    Object value = originField.get(originObject);

                    if(StringUtils.isNotBlank(String.valueOf(value))){
                        // 赋值给目标属性
                        targetField.setAccessible(true);
                        targetField.set(targetObject, value);
                    }
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e){
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * @author  <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param  source 源对象
     * @param  target 目标对象
     * @description  相同对象的属性值拷贝，将源对象属性值不为空的拷贝到目标对象为空的属性上去
     *    1、对应属性名称要一致，有get方法、set方法
     *    2、只会将源对象属性值不为空的，拷贝到目标对象为空的属性上去
     */
    public static void cloneSourceNotNullToTargetNullProperty(Object source, Object target){
        try{
            Class targetClass = target.getClass();
            Field[] targetFields = targetClass.getDeclaredFields();
            for(Field field : targetFields){
                field.setAccessible(true);
                if (null == field.get(target) && null != field.get(source)) {
                    field.set(target,field.get(source));
                }
            }
        }catch(IllegalAccessException e){
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   fields class对象成员变量数组
     * @return  {List} 成员变量名称集合
     * @description  获取class对象成员变量对应的名称集合
     */
    public static List<String> getClassFieldsName(Field[] fields){
        if(null == fields || 0 == fields.length){
            return Collections.emptyList();
        }

        List<String> fieldsNameList = new ArrayList<>(fields.length);
        for(Field field : fields){
            if(null != field){
                fieldsNameList.add(field.getName());
            }
        }
        return fieldsNameList;
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   list 目标对象集合
     * @return  {String} 非空字段属性JSON字符串
     * @description  获取集合中的对象非空字段内容，序列化字符串
     */
    public static <T> String toNotNullPropertyListJSON(List<T> list) throws IllegalAccessException {
        if(CollectionUtils.isEmpty(list)){
            return "";
        }

        JSONArray jsonArray = new JSONArray(list.size());
        for(T object : list){
            JSONObject jsonObject = new JSONObject();

            Field[] fields = object.getClass().getDeclaredFields();
            for(Field field : fields){
                field.setAccessible(true);
                Object obj = field.get(object);

                if(null != obj && StringUtils.isNotBlank(String.valueOf(obj))){
                    if (obj instanceof  Object[]){
                        jsonObject.put(field.getName(), StringUtils.join((Object[])obj, ","));
                    } else if (obj instanceof Date){
                        jsonObject.put(field.getName(), DateFormatUtils.format((Date)obj, DATE_FORMAT));
                    } else {
                        jsonObject.put(field.getName(), String.valueOf(obj));
                    }
                }
            }

            jsonArray.add(jsonObject);
        }
        return JSON.toJSONString(jsonArray);
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   object 目标对象
     * @return  {String} 非空字段属性JSON字符串
     * @description  获取对象非空字段内容，序列化字符串
     */
    public static <T extends Object> String toNotNullPropertyJSON(T object) throws IllegalAccessException {
        if(null == object){
            return "";
        }

        JSONObject jsonObject = new JSONObject();

        Field[] fields = object.getClass().getDeclaredFields();
        for(Field field : fields){
            field.setAccessible(true);
            Object obj = field.get(object);

            if(null != obj && StringUtils.isNotBlank(String.valueOf(obj))){
                if (obj instanceof  Object[]){
                    jsonObject.put(field.getName(), StringUtils.join((Object[])obj, ","));
                } else if (obj instanceof Date){
                    jsonObject.put(field.getName(), DateFormatUtils.format((Date)obj, DATE_FORMAT));
                } else {
                    jsonObject.put(field.getName(), String.valueOf(obj));
                }
            }
        }
        return JSON.toJSONString(jsonObject);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param collection 实体集合
     * @return  {Collection} 去重后的实体集合
     * @description  对实体集合进行去重处理
     */
    public static <T> Collection<T> deDuplicateCollection(Collection<T> collection) {
        if(CollectionUtils.isEmpty(collection)) {
            return collection;
        }

        // 针对实体去重采用转换字符串处理，不需要实体重写 equals 方法
        Set<String> jsonSet = new LinkedHashSet<>(collection.size());
        Class clazz = null;
        for(T item : collection) {
            if(null == item) {
                continue;
            }

            clazz = item.getClass();
            jsonSet.add(JSON.toJSONString(item));
        }

        if(CollectionUtils.isNotEmpty(jsonSet)) {
            // 清空原集合
            collection.clear();

            // 将实体重新放入集合
            for(String jsonItem : jsonSet) {
                T item = (T) JSON.parseObject(jsonItem, clazz);
                collection.add(item);
            }
        }
        return collection;
    }

    /**
     * 获取源对象和目标对象相同名称的字段中 源对象字段值为空，但是目标字段有值的字段
     * @param sourceObj 源对象
     * @param targetedObj 目标对象
     * @return List 字段名称集合
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    public static Set<String> getSourceNullButTargetedNotNullField(Object sourceObj, Object targetedObj) {
        try {
            Field[] sourceFields = sourceObj.getClass().getDeclaredFields();
            Field[] targetedFields = targetedObj.getClass().getDeclaredFields();

            if(null != sourceFields && null != targetedFields) {
                ObjectFieldsDTO sourceObjFieldsDTO = new ObjectFieldsDTO(sourceObj);
                ObjectFieldsDTO targetedObjFieldsDTO = new ObjectFieldsDTO(targetedObj);

                // 找出两个对象相同属性字段
                Set<String> sameFieldNamesSet = BeansResolveUtils.getSameFieldNames(sourceObjFieldsDTO, targetedObjFieldsDTO);
                if(CollectionUtils.isEmpty(sameFieldNamesSet)) {
                    return Collections.emptySet();
                }

                // 处理源对象字段值为空、目标字段有值的字段
                Set<String> result = new HashSet<>();
                for(String sameFieldName : sameFieldNamesSet) {
                    // 源字段
                    Field sourceField = sourceObjFieldsDTO.getFieldNameWithFieldMap().get(sameFieldName);
                    sourceField.setAccessible(true);
                    if(null != sourceField.get(sourceObj)) {
                        continue;
                    }

                    // 目标字段
                    Field targetedField = targetedObjFieldsDTO.getFieldNameWithFieldMap().get(sameFieldName);
                    targetedField.setAccessible(true);
                    if(null == targetedField.get(targetedObj)) {
                        continue;
                    }

                    // 到这里，源对象字段值为空、目标字段有值
                    result.add(sameFieldName);
                }

                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("处理实体数据对照出现问题");
        }

        return Collections.emptySet();
    }


    /**
     * 获取源对象和目标对象相同名称的字段中 源对象字段值为空，但是目标字段有值的字段
     * @param sourceObj 源对象
     * @param targetedObj 目标对象
     * @return List 字段名称集合
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    public static Set<String> getSourceNullButTargetedNotNullFieldWithDozer(Object sourceObj,
                                                                            Object targetedObj,
                                                                            List<ClassMappingMetadata> metaMappingList) {
        try {
            //存储dozer目标的字段配置
            Set<String> dozerFieldName =new HashSet<>();
            if(CollectionUtils.isNotEmpty(metaMappingList)){
                for(ClassMappingMetadata classMappingMetadata:metaMappingList){
                    //在数据对象中的
                    List<FieldMappingMetadata> fieldList=classMappingMetadata.getFieldMappings();
                    if(CollectionUtils.isNotEmpty(fieldList)){
                        //获取目标的字段名
                        for(FieldMappingMetadata fieldMappingMetadata:fieldList){
                            dozerFieldName.add(fieldMappingMetadata.getDestinationName());
                        }
                    }
                }
            }
            Field[] sourceFields = sourceObj.getClass().getDeclaredFields();
            Field[] targetedFields = targetedObj.getClass().getDeclaredFields();

            if(null != sourceFields && null != targetedFields) {
                ObjectFieldsDTO sourceObjFieldsDTO = new ObjectFieldsDTO(sourceObj);
                ObjectFieldsDTO targetedObjFieldsDTO = new ObjectFieldsDTO(targetedObj);

                // 找出两个对象相同属性字段
                Set<String> sameFieldNamesSet = BeansResolveUtils.getSameFieldNames(sourceObjFieldsDTO, targetedObjFieldsDTO);
                if(CollectionUtils.isEmpty(sameFieldNamesSet)) {
                    return Collections.emptySet();
                }

                // 处理源对象字段值为空、目标字段有值的字段
                Set<String> result = new HashSet<>();
                for(String sameFieldName : sameFieldNamesSet) {
                    if(!dozerFieldName.contains(sameFieldName)){
                        continue;
                    }
                    // 源字段
                    Field sourceField = sourceObjFieldsDTO.getFieldNameWithFieldMap().get(sameFieldName);
                    sourceField.setAccessible(true);

                    // 目标字段
                    Field targetedField = targetedObjFieldsDTO.getFieldNameWithFieldMap().get(sameFieldName);
                    targetedField.setAccessible(true);

                    if(null != sourceField.get(sourceObj)) {
                        continue;
                    }
                    if(null == targetedField.get(targetedObj)) {
                        continue;
                    }

                    // 到这里，源对象字段值为空、目标字段有值
                    result.add(sameFieldName);
                }

                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("处理实体数据对照出现问题");
        }

        return Collections.emptySet();
    }


    /**
     * 获取源对象和目标对象相同名称的字段
     * @param sourceObjFieldsDTO 源对象Field信息对象
     * @param targetedObjFieldsDTO 目标对象Field信息对象
     * @return List 字段名称集合
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    public static Set<String> getSameFieldNames(ObjectFieldsDTO sourceObjFieldsDTO, ObjectFieldsDTO targetedObjFieldsDTO) {
        if(CollectionUtils.isEmpty(sourceObjFieldsDTO.getFieldNameSet()) || CollectionUtils.isEmpty(targetedObjFieldsDTO.getFieldNameSet())) {
            return Collections.EMPTY_SET;
        }

        Set<String> sameFieldNamesSet = new HashSet<>();
        for (String sourceFieldName : sourceObjFieldsDTO.getFieldNameSet()) {
            if (targetedObjFieldsDTO.getFieldNameSet().contains(sourceFieldName)) {
                sameFieldNamesSet.add(sourceFieldName);
            }
        }
        return sameFieldNamesSet;
    }

    public static <T> List<T> convertMapListToBeanList(List<Map> mapList, Class<T> clazz) {
        List<T> list = new ArrayList<T>();
        for (Map map : mapList) {
            try {
                T obj = clazz.newInstance();//创建bean的实例对象
                for (Object o : map.keySet()) {//遍历map的key
                    for (Method m : clazz.getMethods()) {//遍历bean的类中的方法，找到set方法进行赋值
                        if (m.getName().toLowerCase().equals("set" + o.toString().toLowerCase())) {
                            m.invoke(obj, map.get(o));
                        }
                    }
                }
                list.add(obj);
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                e.printStackTrace();
                LOGGER.error("map转换为实体失败");
            }
        }
        return list;
    }

    public static <T> T convertMap2Bean(Map<String, Object> map, Class<T> T) {
        if (map == null || map.size() == 0) {
            return null;
        }
        //获取map中所有的key值，全部更新成大写，添加到keys集合中,与mybatis中驼峰命名匹配
        Object mvalue = null;
        Map<String, Object> newMap = new HashMap<>();
        Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            String key = it.next().getKey();
            mvalue = map.get(key);
            newMap.put(key.toUpperCase(Locale.US), mvalue);
        }

        T bean = null;
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(T);
            bean = T.newInstance();
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (int i = 0, n = propertyDescriptors.length; i < n; i++) {
                PropertyDescriptor descriptor = propertyDescriptors[i];
                String propertyName = descriptor.getName();
                String upperPropertyName = propertyName.toUpperCase();

                if (newMap.keySet().contains(upperPropertyName)) {
                    Object value = newMap.get(upperPropertyName);
                    //这个方法不会报参数类型不匹配的错误。
                    BeanUtils.copyProperty(bean, propertyName, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }
}
