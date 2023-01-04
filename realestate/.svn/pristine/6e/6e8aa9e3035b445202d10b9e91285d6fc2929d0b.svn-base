package cn.gtmap.realestate.exchange.core.support.mybatis.mapper;

import org.apache.commons.beanutils.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2015/5/25.
 */
public class DateConvert implements Converter {
    private static final Logger LOGGER = LoggerFactory.getLogger(DateConvert.class);

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
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return df.parse(p.trim());
        } catch (Exception e) {
            try {
                LOGGER.error(e.getMessage(), e);
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                return df.parse(p.trim());
            } catch (ParseException ex) {
                return null;
            }
        }

    }

}