package cn.gtmap.realestate.common.util.jaxb;

import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.math.BigDecimal;

/**
 * Created by zdd on 2015/11/28.
 * 序列化对象适配器 处理Double 空值问题
 */
public class JaxbDoubleAdapter extends XmlAdapter<String, Double> {
    @Override
    public Double unmarshal(String s) throws Exception {
        if (StringUtils.isNoneBlank(s)) {
            return Double.parseDouble(s);
        }
        return null;
    }

    @Override
    public String marshal(Double dbl) throws Exception {
        String str = "0.0";
        if (dbl != null) {
            BigDecimal bigDecimal = new BigDecimal(dbl);
            str = bigDecimal.setScale(4, BigDecimal.ROUND_HALF_UP).toString();
        }
        return str;
    }
}
