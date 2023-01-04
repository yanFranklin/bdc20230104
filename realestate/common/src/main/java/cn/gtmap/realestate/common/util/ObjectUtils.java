package cn.gtmap.realestate.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @program: realestate
 * @description: 反射获取filed值
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-01-21 10:23
 **/
public class ObjectUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectUtils.class);

    public static Object getClassValue(Object obj, String fieldName) {
        if (obj == null) {
            return null;
        }
        try {
            Class beanClass = obj.getClass();
            Method[] ms = beanClass.getMethods();
            for (int i = 0; i < ms.length; i++) {
                // 非get方法不取
                if (!ms[i].getName().startsWith("get")) {
                    continue;
                }
                Object objValue = null;
                try {
                    objValue = ms[i].invoke(obj);
                } catch (Exception e) {
                    LOGGER.error("反射取值出错{}", e.toString());
                    continue;
                }
                if (objValue == null) {
                    continue;
                }
                if (ms[i].getName().equalsIgnoreCase(fieldName.toUpperCase()) || ms[i].getName().substring(3).equalsIgnoreCase(fieldName.toUpperCase())) {
                    return objValue;
                } else if (fieldName.equalsIgnoreCase("SID") && (ms[i].getName().equalsIgnoreCase("ID") || ms[i].getName().substring(3).equalsIgnoreCase("ID")))
                    return objValue;
            }
        } catch (Exception e) {
            LOGGER.error("取方法出错！{}", e.toString());
        }
        return null;
    }


    /**
     * @param source 目标对象
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取对象中数值为null 的字段名
     * @date : 2021/4/14 10:19
     */
    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            // 此处判断可根据需求修改
            if (srcValue == null || Objects.equals(srcValue, "")) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
