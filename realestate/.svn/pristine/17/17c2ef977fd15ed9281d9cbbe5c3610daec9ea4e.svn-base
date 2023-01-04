package cn.gtmap.realestate.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/12/10
 * @description 实体转换为Map工具类
 */
@Component
public class Object2MapUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(Object2MapUtils.class);

    /**
     * @param obj 实体对象
     * @return Map<String                               ,                                                               String> 转换后的Map
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 将实体对象不为null和不为空字符串的属性转换为Map
     */
    public static Map<String, Object> object2MapExceptNull(Object obj) {
        Map<String, Object> map = new HashMap<>();
        if (obj == null) {
            return map;
        }
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.get(obj) != null && field.get(obj) != "") {
                    map.put(field.getName(), field.get(obj));
                }
            }
        } catch (Exception e) {
            LOGGER.error("实体转换Map异常!", e);
        }
        return map;
    }

}
