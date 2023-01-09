package cn.gtmap.realestate.exchange.service.impl.national.access;

import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.exchange.config.ConfigInit;
import cn.gtmap.realestate.exchange.core.domain.BdcJrDbrzjlDO;
import cn.gtmap.realestate.exchange.service.national.access.AccessLogCityHandlerService;
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
 * @program: bdcdj3.0
 * @description: 登簿日志市级上报实现类
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2023-01-06 09:31
 **/
@Service
public class AccessLogCityImpl implements AccessLogCityHandlerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccessLogCityImpl.class);
    @Autowired
    ConfigInit configInit;

    /**
     * @param xml           xml文件
     * @param bdcJrDbrzjlDO 日志接入表实体类
     * @return
     * @author <a href="mailto:zhuruijie@gtmap.cn">gaolining</a>
     * @description 市级登簿日志上报
     **/
    @Override
    public String cityAccessLogWeb(String xml, BdcJrDbrzjlDO bdcJrDbrzjlDO) {
        String cityUrl = configInit.getCityUrl();
        LOGGER.info("登簿日志上报，获取到市级上报地址：{}", cityUrl);
        String result = null;
        if (StringUtils.isNotBlank(cityUrl)) {
            LOGGER.info("登簿日志上报，江苏开始接入市级日志");
            try {
                JrrzServiceService jrrzService = new JrrzServiceService(new URL(cityUrl));
                JrrzService port = jrrzService.getJrrzServicePort();
                LOGGER.warn("江苏登簿日志接入市级端口{}", port);
                BindingProvider bp = (BindingProvider) port;
                bp.getRequestContext().put(
                        BindingProvider.ENDPOINT_ADDRESS_PROPERTY, cityUrl.replace("?wsdl", ""));
                //设置连接时间10s
                bp.getRequestContext().put("com.sun.xml.internal.ws.connect.timeout", 10 * 1000);
                //设置等待返回时间5分钟
                bp.getRequestContext().put("com.sun.xml.internal.ws.request.timeout", 10 * 60 * 1000);
                result = port.exchangeInfo(xml);
                LOGGER.info("登簿日志上报，江苏结束接入市级日志：{}", result);
            } catch (Exception ex) {
                LOGGER.error("登簿日志上报接入市级失败", ex);
                throw new AppException("登簿日志上报，市级上报失败：" + ex.getMessage());
            }
        }
        return result;
    }
}
