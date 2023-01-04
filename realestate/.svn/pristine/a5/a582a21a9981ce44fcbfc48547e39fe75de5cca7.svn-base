package cn.gtmap.realestate.exchange.util.jaxb;

import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created by zdd on 2015/11/30.
 */
public class JaxbStringAdapter extends XmlAdapter<String, String> {
    @Override
    public String unmarshal(String s) throws Exception {
        return s;
    }

    @Override
    public String marshal(String s) throws Exception {
        String str = null;
        if (StringUtils.isNotBlank(s))
            str = s;

        return str;
    }
}
