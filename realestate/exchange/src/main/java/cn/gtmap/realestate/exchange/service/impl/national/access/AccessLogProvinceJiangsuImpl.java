package cn.gtmap.realestate.exchange.service.impl.national.access;

import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.exchange.config.ConfigInit;
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
        logger.info("登簿日志上报，获取到省厅地址：{}", provinceUrl);
        String result = null;
        if (StringUtils.isNotBlank(provinceUrl)) {
            logger.info("登簿日志上报，江苏开始接入省级日志");
            try {
                JrrzServiceService jrrzService = new JrrzServiceService(new URL(provinceUrl));
                JrrzService port = jrrzService.getJrrzServicePort();
                BindingProvider bp = (BindingProvider) port;
                bp.getRequestContext().put(
                        BindingProvider.ENDPOINT_ADDRESS_PROPERTY, provinceUrl.replace("?wsdl", ""));
                //设置连接时间10s
                bp.getRequestContext().put("com.sun.xml.internal.ws.connect.timeout", 10 * 1000);
                //设置等待返回时间5分钟
                bp.getRequestContext().put("com.sun.xml.internal.ws.request.timeout", 10 * 60 * 1000);
                result = port.exchangeInfo(xml);
                logger.info("登簿日志上报，江苏结束接入省级日志：{}", result);
            } catch (Exception ex) {
                logger.error("登簿日志上报接入省厅失败", ex);
                throw new AppException("登簿日志上报，上报失败：" + ex.getMessage());
            }
        }
        return result;
    }

}
