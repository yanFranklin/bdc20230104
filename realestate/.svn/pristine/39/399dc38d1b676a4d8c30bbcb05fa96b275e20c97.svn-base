package cn.gtmap.realestate.etl.config;

import cn.gtmap.realestate.etl.service.FaceCheckInterface;
import cn.gtmap.realestate.etl.service.TransactionInquiryExternalInterface;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 2020/06/23,1.0
 * @description
 */
@Configuration
public class WebServiceConfig {

    @Autowired
    private Bus bus;

    @Autowired
    private FaceCheckInterface faceCheckInterface;

    @Autowired
    private TransactionInquiryExternalInterface transactionInquiryExternalInterface;

    @Bean("cxfServletRegistration")
    public ServletRegistrationBean dispatcherServlet() {
        return new ServletRegistrationBean(new CXFServlet(), "/ws-api/*");
    }

    @Bean
    public Endpoint nt_endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, faceCheckInterface);
        endpoint.publish("/faceCheck");
        return endpoint;
    }

    @Bean
    public Endpoint bb_endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, transactionInquiryExternalInterface);
        endpoint.publish("/transactionInquiry");
        return endpoint;
    }
}
