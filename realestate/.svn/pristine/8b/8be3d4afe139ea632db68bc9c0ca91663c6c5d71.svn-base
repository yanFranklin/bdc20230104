package cn.gtmap.realestate.exchange.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * 对bean实体进行操作工具类
 */
public class BeanUtil {

    private static Logger LOGGER = LoggerFactory.getLogger(BeanUtil.class);

    /**
     * sly 通过反射赋值实体类某个字段的值
     *
     * @param entityField 实体类中的一个字段
     * @param entity      实体类
     * @param value       要赋的值
     */
    public static void setEntityFieldValue(Field entityField, Object entity, Object value) {
        if (entityField != null && entity != null) {
            try {
                Class[] parameterTypes = new Class[1];
                parameterTypes[0] = entityField.getType();
                StringBuilder sb = new StringBuilder();
                sb.append("set");
                sb.append(entityField.getName().substring(0, 1).toUpperCase());
                sb.append(entityField.getName().substring(1));
                Method method = entity.getClass().getMethod(sb.toString(), parameterTypes);
                method.invoke(entity, value);
            } catch (Exception e) {
                LOGGER.error("赋值时报错", e);
            }
        }
    }

    /**
     * sly 通过反射赋值实体类某个字段的值
     *
     * @param entityFieldClass 实体类中的一个字段的类型
     * @param entity           实体类
     * @param value            要赋的值
     */
    public static void setEntityFieldValue(Class entityFieldClass, String entityFieldName, Object entity, Object value) {
        if (entityFieldClass != null && entity != null) {
            try {
                Class[] parameterTypes = new Class[1];
                parameterTypes[0] = entityFieldClass;
                StringBuilder sb = new StringBuilder();
                sb.append("set");
                sb.append(entityFieldName.substring(0, 1).toUpperCase());
                sb.append(entityFieldName.substring(1));
                Method method = entity.getClass().getMethod(sb.toString(), parameterTypes);
                method.invoke(entity, value);
            } catch (Exception e) {
                LOGGER.error("赋值时报错", e);
            }
        }
    }
}
