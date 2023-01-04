package cn.gtmap.realestate.common.core.dto;

import org.apache.commons.collections.map.HashedMap;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 对象字段信息实体
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 */
public class ObjectFieldsDTO {
    /**
     * 字段Field名称集合
     */
    private Set<String> fieldNameSet;

    /**
     * 字段名：Field 对照Map集合
     */
    private Map<String, Field> fieldNameWithFieldMap;


    /**
     * 解析目标实体，处理Field信息
     * @param object 目标实体
     * @return ObjectFieldsDTO
     */
    public ObjectFieldsDTO(Object object) {
        if(null == object) {
            return;
        }

        Field[] fields = object.getClass().getDeclaredFields();
        if(null == fields || 0 == fields.length) {
            return;
        }

        this.fieldNameSet = new HashSet<>(fields.length);
        this.fieldNameWithFieldMap = new HashedMap(fields.length);

        for(Field field : fields) {
            this.fieldNameSet.add(field.getName());
            this.fieldNameWithFieldMap.put(field.getName(), field);
        }
    }

    public Set<String> getFieldNameSet() {
        return fieldNameSet;
    }

    public Map<String, Field> getFieldNameWithFieldMap() {
        return fieldNameWithFieldMap;
    }
}
