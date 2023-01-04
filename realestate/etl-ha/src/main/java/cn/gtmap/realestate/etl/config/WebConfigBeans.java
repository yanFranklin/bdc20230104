package cn.gtmap.realestate.etl.config;

import cn.gtmap.realestate.common.core.support.spring.LayuiPageableArgumentResolver;
import cn.gtmap.realestate.common.core.support.spring.StringToDateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
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


    /**
     * @param
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 处理实体中 String 转 Date 格式问题
     */
    @PostConstruct
    public void initEditableValidation() {
        ConfigurableWebBindingInitializer configurableWebBindingInitializer = (ConfigurableWebBindingInitializer) requestMappingHandlerAdapter.getWebBindingInitializer();
        if (configurableWebBindingInitializer.getConversionService() != null) {
            GenericConversionService service = (GenericConversionService) configurableWebBindingInitializer.getConversionService();
            service.addConverter(new StringToDateConverter());
        }
    }

    /**
     * @param argumentResolvers
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 处理layui分页页码问题
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new LayuiPageableArgumentResolver());
        super.addArgumentResolvers(argumentResolvers);
    }

}
