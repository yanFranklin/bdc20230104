package cn.gtmap.realestate.exchange.core.support.mybatis.mapper;

import org.apache.commons.beanutils.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.SQLException;

/**
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2016/6/16
 * @description MyBatis的EntityMapper的Blob转换
 */
public class BlobConvert implements Converter {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlobConvert.class);

    @Override
    public Object convert(Class type, Object value) {
        byte[] result = null;
        if (value == null) {
            return null;
        }
        if (value instanceof Blob) {
            try {
                Blob blob = (Blob) value;
                result = blob.getBytes(1, (int) (blob.length()));
            } catch (SQLException e) {
                LOGGER.error(e.getMessage(), e);
            }
        } else if (value instanceof byte[]) {
            result = (byte[]) value;
        } else {
            try {
                result = value.toString().getBytes("utf-8");
            } catch (UnsupportedEncodingException e) {
                LOGGER.error(e.getMessage(), e);
            }
            if (result == null || result.length == 0) {
                return null;
            }
        }

        return result;
    }
}
