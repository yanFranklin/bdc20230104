package cn.gtmap.realestate.common.util.jaxb;

import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
 * @version 1.0, 2017/7/25
 * @description 序列化对象适配器  格式化日期
 */
public class JaxbDateYMDAdapter extends XmlAdapter<String, Date> {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");


    @Override
    public Date unmarshal(String s) throws Exception {
        if (StringUtils.isNoneBlank(s)) {
            return dateFormat.parse(s);
        }
        return null;
    }

    @Override
    public String marshal(Date date) throws Exception {
        String str = null;
        if (date != null) {
            str = dateFormat.format(date);
        }
        return str;
    }
}
