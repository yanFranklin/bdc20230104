package cn.gtmap.realestate.building.ui.config;

import cn.gtmap.realestate.building.ui.util.*;
import cn.gtmap.realestate.common.config.interceptor.WebLogInterceptor;
import cn.gtmap.realestate.common.core.support.spring.LayuiPageableArgumentResolver;
import cn.gtmap.realestate.common.core.support.spring.StringToDateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-12-16
 * @description
 */
@Configuration
public class WebConfigBeans extends WebMvcConfigurerAdapter {

    @Autowired
    RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    @Autowired
    private CheckInterceptor checkInterceptor;
    @Value("${print.path:/usr/local/bdc3/print/}")
    private String printPath;

    @Value("${spring.application.name:}")
    private String appName;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(checkInterceptor).addPathPatterns("/*/withcheck/**");
        registry.addInterceptor(checkInterceptor);

        // 添加页面请求拦截器
        WebLogInterceptor webLogInterceptor = new WebLogInterceptor();
        webLogInterceptor.setAppName(appName);
        InterceptorRegistration registration = registry.addInterceptor(webLogInterceptor);
        registration.addPathPatterns("/**").excludePathPatterns("/static/**");

        super.addInterceptors(registry);
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param
     * @return void
     * @description 处理实体中 String 转 Date 格式问题
     */
    @PostConstruct
    public void initEditableValidation(){
        ConfigurableWebBindingInitializer configurableWebBindingInitializer = (ConfigurableWebBindingInitializer) requestMappingHandlerAdapter.getWebBindingInitializer();
        if(configurableWebBindingInitializer.getConversionService()!=null){
            GenericConversionService service = (GenericConversionService) configurableWebBindingInitializer.getConversionService();
            service.addConverter(new StringToDateConverter());
        }
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param argumentResolvers
     * @return void
     * @description 处理layui分页页码问题
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new LayuiPageableArgumentResolver());
        super.addArgumentResolvers(argumentResolvers);
    }


    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 打印地址跳转
     * @date : 2020/12/7 16:01
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/printmodel/**")
                .addResourceLocations("file:"+printPath);
    }

}