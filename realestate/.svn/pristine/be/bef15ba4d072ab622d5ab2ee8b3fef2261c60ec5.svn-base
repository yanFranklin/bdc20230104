package cn.gtmap.realestate.certificate.config;

import cn.gtmap.realestate.common.config.interceptor.SendMsgInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0  2019-9-10
 * @description 配置拦截器
 */
@Configuration
public class WebConfigBeans extends WebMvcConfigurerAdapter {
    @Autowired
    private SendMsgInterceptor sendMsgInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        sendMsgInterceptor.initMap();
        registry.addInterceptor(sendMsgInterceptor);
        super.addInterceptors(registry);
    }
}
