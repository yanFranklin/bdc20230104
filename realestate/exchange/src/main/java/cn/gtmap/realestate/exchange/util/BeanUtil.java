package cn.gtmap.realestate.exchange.util;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSqrDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;
import cn.gtmap.realestate.common.util.AnnotationsUtils;
import cn.gtmap.realestate.common.util.BdcdyhToolUtils;
import cn.gtmap.realestate.exchange.core.bo.anno.CastField;
import cn.gtmap.realestate.exchange.core.bo.anno.IgnoreCast;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import cn.hutool.core.bean.copier.CopyOptions;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jodd.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;


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
