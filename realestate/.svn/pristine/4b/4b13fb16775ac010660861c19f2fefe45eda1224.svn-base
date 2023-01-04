package cn.gtmap.interchange.core.support.mybatis.mapper;

import org.apache.commons.beanutils.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zx on 2015/5/25.
 * des:整型解析
 */
public class IntegerConvert implements Converter {
    private static final Logger LOGGER = LoggerFactory.getLogger(EntityMapper.class);

    @Override
    public Object convert(Class arg0, Object arg1) {
        String p;
        if (arg1 == null) {
            return null;
        }
        p = arg1.toString();
        if (p == null || p.trim().length() == 0) {
            return null;
        }
        try {
            return Integer.parseInt(p.trim());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }

    }

}