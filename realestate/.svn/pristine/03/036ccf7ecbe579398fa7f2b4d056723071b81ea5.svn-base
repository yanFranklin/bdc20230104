package cn.gtmap.realestate.natural.common;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version: V1.0, 2019/07/16
 * @description:
 */
public class BeanHelper {

    /**
     * 去掉bean中所有属性为字符串的前后空格
     *
     * @param bean
     * @throws Exception
     */
    public static void beanValueTrim(Object bean) throws Exception {
        if (bean != null) {
            //获取所有的字段包括public,private,protected,private
            Field[] fields;
            // 如果是map
            if (bean instanceof Map || bean instanceof HashMap) {
                Map<String, Object> map = (Map) bean;
                for (String key : map.keySet()) {
                    if (map.get(key) != null && map.get(key) instanceof String) {
                        map.put(key, map.get(key).toString().trim());

                    }
                }
            } else {
                // javabean 对象
                fields = bean.getClass().getDeclaredFields();
                for (int i = 0; i < fields.length; i++) {
                    Field f = fields[i];
                    if (StringUtils.equals("java.lang.String", f.getType().getName())) {
                        String key = f.getName();//获取字段名
                        Object value = getFieldValue(bean, key);

                        if (value == null) {
                            continue;
                        }
                        setFieldValue(bean, key, value.toString().trim());
                    }
                }
            }
        }
    }

    /**
     * 利用反射通过get方法获取bean中字段fieldName的值
     *
     * @param bean
     * @param fieldName
     * @return
     * @throws Exception
     */
    private static Object getFieldValue(Object bean, String fieldName)
            throws Exception {
        StringBuffer result = new StringBuffer();
        String methodName = result.append("get")
                .append(fieldName.substring(0, 1).toUpperCase())
                .append(fieldName.substring(1)).toString();

        Object rObject = null;
        Method method = null;

        @SuppressWarnings("rawtypes")
        Class[] classArr = new Class[0];
        method = bean.getClass().getMethod(methodName, classArr);
        rObject = method.invoke(bean, new Object[0]);

        return rObject;
    }

    /**
     * 利用发射调用bean.set方法将value设置到字段
     *
     * @param bean
     * @param fieldName
     * @param value
     * @throws Exception
     */
    private static void setFieldValue(Object bean, String fieldName, Object value)
            throws Exception {
        StringBuffer result = new StringBuffer();
        String methodName = result.append("set")
                .append(fieldName.substring(0, 1).toUpperCase())
                .append(fieldName.substring(1)).toString();

        /**
         * 利用发射调用bean.set方法将value设置到字段
         */
        Class[] classArr = new Class[1];
        classArr[0] = "java.lang.String".getClass();
        Method method = bean.getClass().getMethod(methodName, classArr);
        method.invoke(bean, value);
    }
}