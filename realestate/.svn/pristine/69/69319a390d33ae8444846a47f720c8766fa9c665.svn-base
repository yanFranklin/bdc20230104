package cn.gtmap.realestate.exchange.config;

import cn.gtmap.realestate.exchange.service.inf.standard.JiangSuSwWebService;
import lombok.extern.slf4j.Slf4j;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.xml.ws.Endpoint;


@Slf4j
@Configuration
public class WebServiceConfig {

    @Autowired
    private Bus bus;

    @Autowired
    private JiangSuSwWebService jiangSuSwWebService;

    @PostConstruct
    public void init() {
        System.setProperty(StaxUtils.ALLOW_INSECURE_PARSER, "1");
    }

    @Bean("cxfServletRegistration")
    public ServletRegistrationBean dispatcherServlet() {
        return new ServletRegistrationBean(new CXFServlet(), "/realestate-exchange/wsapi/*");
    }

    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, jiangSuSwWebService);
        endpoint.publish("/sw");
        return endpoint;
    }

}
