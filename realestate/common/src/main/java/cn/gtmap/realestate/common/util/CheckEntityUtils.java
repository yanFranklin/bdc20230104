package cn.gtmap.realestate.common.util;

import cn.gtmap.realestate.common.core.annotations.RequiredFk;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Id;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/7
 * @description 判断实体工具类
 */
public class CheckEntityUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckEntityUtils.class);

    /**
     * @param entityClass
     * @return boolean
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 获取实体中主键和外键是否为空
     */
    public static boolean checkPkAndFk(Object entityClass) {
        List<Field> fieldPkList = AnnotationsUtils.getAnnotationField(entityClass, Id.class);
        List<Field> fieldFkList = AnnotationsUtils.getAnnotationField(entityClass, RequiredFk.class);
        boolean pk = checkObjectNotNull(fieldPkList, entityClass);
        boolean fk = checkObjectNotNull(fieldFkList, entityClass);
        if (pk && fk) {
            return true;
        }
        return false;
    }

    /**
     * @param entityClass
     * @return boolean
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 获取实体中主键否为空
     */
    public static boolean checkPk(Object entityClass) {
        List<Field> fieldPkList = AnnotationsUtils.getAnnotationField(entityClass, Id.class);
        boolean pk = checkObjectNotNull(fieldPkList, entityClass);
        if (pk) {
            return true;
        }
        return false;
    }


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param entity
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @description 获取 实体主键值
     */
    public static Map<String,Object> getPkValue(Object entity){
        List<Field> fieldPkList = AnnotationsUtils.getAnnotationField(entity, Id.class);
        Map<String,Object> map = new HashMap();
        for (Field field : fieldPkList) {
            map.put(field.getName(),getFieldValue(field,entity));
        }
        return map;
    }

    /**
     * @param entityClass
     * @return boolean
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 获取实体中外键是否为空
     */
    public static boolean checkFk(Object entityClass) {
        List<Field> fieldFkList = AnnotationsUtils.getAnnotationField(entityClass, RequiredFk.class);
        boolean fk = checkObjectNotNull(fieldFkList, entityClass);
        if (fk) {
            return true;
        }
        return false;
    }

    /**
     * @param list
     * @param entityClass
     * @return boolean
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 判断注解下的对象值是否为空
     */
    private static boolean checkObjectNotNull(List<Field> list, Object entityClass) {
        for (Field field : list) {
            Object value;
            value = getFieldValue(field,entityClass);
            if (value == null) {
                return false;
            }
            if (value instanceof String && StringUtils.isBlank(value.toString())) {
                return false;
            }
        }
        return true;
    }


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param field
     * @param entityClass
     * @return java.lang.Object
     * @description 获取字段值
     */
    public static Object getFieldValue(Field field,Object entityClass) {
        Object value = null;
        String key = field.getName();
        PropertyDescriptor descriptor = null;
        try {
            descriptor = new PropertyDescriptor(key, entityClass.getClass());
            Method method = descriptor.getReadMethod();
            if (method != null) {
                value = method.invoke(entityClass);
            }
        } catch (IntrospectionException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (InvocationTargetException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return value;
    }
}