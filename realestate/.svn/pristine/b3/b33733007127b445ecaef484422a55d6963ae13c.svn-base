package cn.gtmap.realestate.exchange.service.impl.national;

import cn.gtmap.realestate.exchange.config.ConfigInit;
import cn.gtmap.realestate.exchange.core.dto.accessLog.AccessRespondModel;
import cn.gtmap.realestate.exchange.service.national.access.AccessLogProvinceHandlerServise;
import cn.gtmap.realestate.exchange.util.WebServiceClientHelper;
import cn.gtmap.realestate.exchange.util.XmlEntityConvertUtil;
import org.apache.commons.lang3.CharEncoding;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;

/**
 * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
 * @return
 * @description 安徽省级webservice日志上报
 **/
@Service("accessLog_anhui")
public class AccessLogProvinceAnhuiImpl implements AccessLogProvinceHandlerServise {
    @Autowired
    XmlEntityConvertUtil xmlEntityConvertUtil;
    @Autowired
    ConfigInit configInit;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public String provinceAccessLog(String xml) {
        String provinceUrl = configInit.getProvinceUrl();
        String result = null;
        if (StringUtils.isNotBlank(provinceUrl)) {
            logger.info("安徽开始接入省级日志");
            String sb = WebServiceClientHelper.axisCallService(provinceUrl, "http://tempuri.org/UploadLog", "http://tempuri.org/", "UploadLog", "xmlTxt", xml);
            logger.info("安徽接入省级日志响应结果：{}", sb);
            if (StringUtils.isNotBlank(sb)) {
                AccessRespondModel respondModel = (AccessRespondModel) xmlEntityConvertUtil.xmlToEntity(AccessRespondModel.class, new ByteArrayInputStream(sb.getBytes(Charset.forName(CharEncoding.UTF_8))));
                if (respondModel != null) {
                    result = "0200".equals(respondModel.getResponseLogCode()) ? "success" : respondModel.getResponseLogInfo();
                    //错误响应大与500的做截取
                    if (result.length() > 500) {
                        result = result.substring(0, 500);
                    }
                }
            }
        }
        return result;
    }
}
