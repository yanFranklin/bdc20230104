package cn.gtmap.realestate.exchange.util;

import org.apache.commons.collections.CollectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/7 自定义注解工具类
 * @description
 */
public class AnnotationsUtils {
    /**
     * @param obj
     * @param annotationClass
     * @return java.util.List<java.lang.reflect.Field>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 获取对象上注解类为annotationClass的 对象属性字段
     */
    public static List<Field> getAnnotationField(Object obj, Class annotationClass) {
        List<Field> annotationfieldsfieldList = new ArrayList<>();
        List<Field> fieldList = getClassFields(obj);
        if (CollectionUtils.isNotEmpty(fieldList)) {
            for (Field f : fieldList) {
                if (f.isAnnotationPresent(annotationClass)) {
                    annotationfieldsfieldList.add(f);
                }
            }
        }
        return annotationfieldsfieldList;
    }

    /**
     * @param obj
     * @return java.util.List<java.lang.reflect.Field>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 获取对象信息
     */
    private static final List<Field> getClassFields(Object obj) {
        List<Field> fieldList = new ArrayList<>();
        if (obj != null) {
            getClassFields(obj.getClass(), fieldList);
        }
        return fieldList;
    }

    /**
     * @param objectClass
     * @param fieldList
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 获取对象信息
     */
    public static void getClassFields(Class<?> objectClass, List<Field> fieldList) {
        if (objectClass != null) {
            Field[] fields = objectClass.getDeclaredFields();
            if (fields != null && fields.length > 0) {
                for (Field field : fields) {
                    fieldList.add(field);
                }
            }
            if (objectClass.getSuperclass() != null) {
                getClassFields(objectClass.getSuperclass(), fieldList);
            }
        }
    }
}