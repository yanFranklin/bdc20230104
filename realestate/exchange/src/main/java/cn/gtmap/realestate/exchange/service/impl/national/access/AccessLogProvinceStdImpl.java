package cn.gtmap.realestate.exchange.service.impl.national.access;

import cn.gtmap.realestate.exchange.config.ConfigInit;
import cn.gtmap.realestate.exchange.service.national.access.AccessLogProvinceHandlerServise;
import cn.gtmap.realestate.exchange.util.WebServiceClientHelper;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;

/**
 * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
 * @return
 * @description 标准省级webservice日志上报
 **/
@Service("accessLog_standard")
public class AccessLogProvinceStdImpl implements AccessLogProvinceHandlerServise {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    ConfigInit configInit;

    @Override
    public String provinceAccessLog(String xml) {
        String provinceUrl = configInit.getProvinceUrl();
        String result = null;
        String resultCode = "2";
        try {
            xml = URLEncoder.encode(URLEncoder.encode(xml, CharEncoding.UTF_8), CharEncoding.UTF_8);
            String param = MessageFormat.format("logxml={0}", xml);
            StringBuilder sb = WebServiceClientHelper.httpConnection(provinceUrl, param);
            if (sb != null) {
                JSONObject jsonObject = JSONObject.parseObject(sb.toString());
                if (jsonObject != null) {
                    result = resultCode.equals(jsonObject.get("code").toString()) ? "success" : jsonObject.get("msg").toString();
                }
            }
        } catch (UnsupportedEncodingException e) {
            logger.error("errorMsg:", e);
        }
        return result;
    }
}
