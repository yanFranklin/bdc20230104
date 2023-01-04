package cn.gtmap.realestate.common.core.support.mybatis.utils;

import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Id;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zx
 * Date: 15-3-25
 * Time: 下午8:14
 * Des:获取注释
 * To change this template use File | Settings | File Templates.
 */
public final class AnnotationsUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(EntityMapper.class);

    private AnnotationsUtils() {
    }

    /**
     * 返回vo对象中主键的get方法
     *
     * @param vo
     * @return
     */
    public static Method getAnnotationsName(Object vo) {
        Method method = null;
        String name = null;
        Class<?> entity = vo.getClass();
        if (entity != null) {
            /**返回类中所有字段，包括公共、保护、默认（包）访问和私有字段，但不包括继承的字段
             * entity.getFields();只返回对象所表示的类或接口的所有可访问公共字段
             * 在class中getDeclared**()方法返回的都是所有访问权限的字段、方法等；
             * 可看API
             * */
            Field[] fields = entity.getDeclaredFields();
            for (Field f : fields) {
                //获取字段中包含fieldMeta的注解
                if (f.isAnnotationPresent(Id.class)) {
                    name = f.getName();
                    break;
                }
            }
            if (StringUtils.isEmpty(name) && entity.getSuperclass() != null) {
                Field[] fieldsSuper = entity.getSuperclass().getDeclaredFields();
                for (Field f : fieldsSuper) {
                    //获取字段中包含fieldMeta的注解
                    if (f.isAnnotationPresent(Id.class)) {
                        name = f.getName();
                        break;
                    }
                }
            }
        }

        try {
            if (name != null && StringUtils.isNoneBlank(name)) {
                name = "get" + StringUtils.substring(name, 0, 1).toUpperCase() + StringUtils.substring(name, 1, name.length());
                method = entity.getMethod(name);
            }

        } catch (NoSuchMethodException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return method;
    }


    /**
     * 返回vo对象中主键的set方法
     *
     * @param vo
     * @return
     */
    public static Method setAnnotationsName(Object vo) {
        Method method = null;
        String name = null;
        Class<?> entity = vo.getClass();
        if (entity != null) {
            /**返回类中所有字段，包括公共、保护、默认（包）访问和私有字段，但不包括继承的字段
             * entity.getFields();只返回对象所表示的类或接口的所有可访问公共字段
             * 在class中getDeclared**()方法返回的都是所有访问权限的字段、方法等；
             * 可看API
             * */
            Field[] fields = entity.getDeclaredFields();
            for (Field f : fields) {
                //获取字段中包含fieldMeta的注解
                if (f.isAnnotationPresent(Id.class)) {
                    name = f.getName();
                    break;
                }
            }
            if (StringUtils.isEmpty(name) && entity.getSuperclass() != null) {
                Field[] fieldsSuper = entity.getSuperclass().getDeclaredFields();
                for (Field f : fieldsSuper) {
                    //获取字段中包含fieldMeta的注解
                    if (f.isAnnotationPresent(Id.class)) {
                        name = f.getName();
                        break;
                    }
                }
            }
        }

        try {
            if (name != null && StringUtils.isNoneBlank(name)) {
                name = "set" + StringUtils.substring(name, 0, 1).toUpperCase() + StringUtils.substring(name, 1, name.length());
                method = entity.getMethod(name);
            }

        } catch (NoSuchMethodException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return method;
    }

    /**
     * zdd 获取对象上注解类为annotationClass的 对象属性名称
     *
     * @param obj
     * @param annotationClass
     * @return
     */
    public static String getAnnotationFieldName(Object obj, Class annotationClass) {
        String name = "";
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field f : fields) {
            if (f.isAnnotationPresent(annotationClass)) {
                name = f.getName();
                break;
            }
        }
        return name;
    }

    /**
     * zdd 获取对象上注解类为annotationClass的 对象属性字段
     *
     * @param obj
     * @param annotationClass
     * @return
     */
    public static List<Field> getAnnotationField(Object obj, Class annotationClass) {
        List<Field> annotationfieldsfieldList = new ArrayList();

        List<Field> fieldList = new ArrayList();
        //zdd 获取类本身的字段
        Field[] fields = obj.getClass().getDeclaredFields();
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                fieldList.add(field);
            }
        }
        //zdd 获取父类字段
        if (obj.getClass().getSuperclass() != null) {
            fields = obj.getClass().getSuperclass().getDeclaredFields();
            if (fields != null && fields.length > 0) {
                for (Field field : fields) {
                    fieldList.add(field);
                }
            }
        }
        if (!fieldList.isEmpty()) {
            for (Field f : fieldList) {
                if (f.isAnnotationPresent(annotationClass)) {
                    annotationfieldsfieldList.add(f);
                }
            }
        }

        return annotationfieldsfieldList;
    }
}
