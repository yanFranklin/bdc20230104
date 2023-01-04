package cn.gtmap.realestate.exchange.core.support.mybatis.mapper;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

/**
 * @author <a href="mailto:zhangxing@gtmap.cn">zx</a>
 * @version 1.0, 2016/6/16
 * @description 类转换器
 */
public class BeanUtilsEx extends BeanUtils {
    static {
        byte byteArray[] = new byte[0];
        ConvertUtils.register(new BlobConvert(), byteArray.getClass());
        ConvertUtils.register(new ClobConvert(), String.class);
        ConvertUtils.register(new DateConvert(), java.util.Date.class);
        ConvertUtils.register(new DateConvert(), java.sql.Date.class);
        ConvertUtils.register(new IntegerConvert(), Integer.class);
        ConvertUtils.register(new DoubleConvert(), Double.class);
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(BeanUtilsEx.class);

    public static void copyProperties(Object dest, Object orig) {
        try {
            BeanUtils.copyProperties(dest, orig);
        } catch (IllegalAccessException ex) {
            LOGGER.error(ex.getMessage(), ex);
        } catch (InvocationTargetException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }
}
