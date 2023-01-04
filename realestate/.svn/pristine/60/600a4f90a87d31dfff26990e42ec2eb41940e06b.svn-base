package cn.gtmap.realestate.etl.config;

import cn.gtmap.realestate.etl.service.PhotoForOrderInterface;
import cn.gtmap.realestate.etl.service.TransactionInquiryExternalInterface;
import cn.gtmap.realestate.etl.service.impl.TransactionInquiryExternalInterfaceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import javax.xml.ws.Endpoint;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 2020/06/23,1.0
 * @description
 */
@Slf4j
@Configuration
public class WebServiceConfig {

    @Autowired
    private Bus bus;

    @Autowired
    private TransactionInquiryExternalInterface transactionInquiryExternalInterface;

    @Autowired
    private PhotoForOrderInterface photoForOrderInterface;

    @Bean("cxfServletRegistration")
    public ServletRegistrationBean dispatcherServlet() {
        return new ServletRegistrationBean(new CXFServlet(), "/ws-api/*");
    }

    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, transactionInquiryExternalInterface);
        endpoint.publish("/transactionInquiry");
        return endpoint;
    }


}
