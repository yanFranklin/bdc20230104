package cn.gtmap.realestate.exchange.service.impl.national;

import cn.gtmap.realestate.exchange.config.ConfigInit;
import cn.gtmap.realestate.exchange.core.ex.AppException;
import cn.gtmap.realestate.exchange.service.national.access.AccessLogProvinceHandlerServise;
import cn.gtmap.realestate.exchange.util.XmlEntityConvertUtil;
import cn.gtmap.realestate.exchange.util.webservice.JrrzService;
import cn.gtmap.realestate.exchange.util.webservice.JrrzServiceService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.BindingProvider;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-11-14
 * @description
 */
@Service("accessLog_jiangsu")
public class AccessLogProvinceJiangsuImpl implements AccessLogProvinceHandlerServise {

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
            logger.info("江苏开始接入省级日志");
            try {
//            result = WebServiceClientHelper.axis2Rpc(provinceUrl, xml);
                JrrzServiceService service_service = new JrrzServiceService(new URL(provinceUrl));
                JrrzService port = service_service.getJrrzServicePort();
                BindingProvider bp = (BindingProvider) port;
                bp.getRequestContext().put(
                        BindingProvider.ENDPOINT_ADDRESS_PROPERTY, provinceUrl.replace("?wsdl", ""));
                result = port.exchangeInfo(xml);
                logger.info("江苏结束接入省级日志：{}", result);
            } catch (MalformedURLException ex) {
                throw new AppException("上报日志失败：" + ex.getMessage());
            }
        }
        return result;
    }

}
