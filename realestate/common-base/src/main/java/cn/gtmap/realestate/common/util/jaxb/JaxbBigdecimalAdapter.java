package cn.gtmap.realestate.common.util.jaxb;

import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.math.BigDecimal;

/*
 * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
 * @version 1.0, 16-9-28
 * @description 序列化对象适配器  处理科学计算式格式问题
 */
public class JaxbBigdecimalAdapter extends XmlAdapter<String, Double> {
    @Override
    public Double unmarshal(String s) throws Exception {
        if (StringUtils.isNoneBlank(s)) {
            BigDecimal bigDecimal = new BigDecimal(s);
            return Double.parseDouble(bigDecimal.toString());
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
