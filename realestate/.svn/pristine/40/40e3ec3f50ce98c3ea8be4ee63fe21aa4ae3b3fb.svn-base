package cn.gtmap.realestate.common.util.jaxb;

import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zdd on 2015/11/28.
 * 序列化对象适配器  格式化日期  处理空值问题等,格式化成日期格式，不要时分秒
 */
public class JaxbDateTsAdapter extends XmlAdapter<String, Date> {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


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
