package cn.gtmap.realestate.common.core.support.mybatis.mapper;

import org.apache.commons.beanutils.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;

/**
 * @author <a href="mailto:zhangxing@gtmap.cn">zx</a>
 * @version 1.0, 2016/6/16
 * @description Clob类型转换器
 */
public class ClobConvert implements Converter {
    private static final Logger LOGGER = LoggerFactory.getLogger(EntityMapper.class);

    @Override
    public Object convert(Class arg0, Object arg1) {
        String p = null;
        if (arg1 == null) {
            return null;
        }
        if (arg1 instanceof Clob) {
            /**
             *    Clob处理
             */

            try (Reader is = ((Clob)arg1).getCharacterStream();
                 BufferedReader br = new BufferedReader(is)){
                String s = br.readLine();
                StringBuilder sb = new StringBuilder();
                while (s != null) {
                    /**
                     *   执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
                     */
                    sb.append(s);
                    s = br.readLine();
                }

                p = sb.toString();
            } catch (SQLException | IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }else {
            p = arg1.toString();
            if (p == null || p.trim().length() == 0) {
                return null;
            }
        }

        return p;
    }

}