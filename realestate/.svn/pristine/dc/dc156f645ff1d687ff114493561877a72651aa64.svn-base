package cn.gtmap.realestate.common.util.jaxb;

import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created by zdd on 2015/11/28.
 * 序列化对象适配器 处理Integer 空值问题
 */
public class JaxbIntegerAdapter extends XmlAdapter<String, Integer> {
    @Override
    public Integer unmarshal(String s) throws Exception {
        if (StringUtils.isNoneBlank(s)) {
            return Integer.parseInt(s);
        }
        return null;
    }

    @Override
    public String marshal(Integer integer) throws Exception {
        String str = "0";
        if (integer != null) {
            str = integer.toString();
        }
        return str;
    }
}
