package cn.gtmap.realestate.exchange.config;

import cn.gtmap.realestate.common.core.support.spring.LayuiPageableArgumentResolver;
import cn.gtmap.realestate.common.core.support.spring.StringToDateConverter;
import cn.gtmap.realestate.exchange.service.impl.inf.nantong.NantongSwServiceImpl;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
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
    protected static final Logger LOGGER = LoggerFactory.getLogger(WebConfigBeans.class);


    @Autowired
    RequestMappingHandlerAdapter requestMappingHandlerAdapter;
    @Autowired
    GetIpInterceptor getIpInterceptor;

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

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullListAsEmpty);
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);

      /*   FormHttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
        Jackson2ObjectMapperBuilder builder1 = Jackson2ObjectMapperBuilder.xml();
        builder1.indentOutput(true);
        converters.add(new MappingJackson2XmlHttpMessageConverter(builder1.build()));
        Jackson2ObjectMapperBuilder builder = Jackson2ObjectMapperBuilder.xml();
        builder.indentOutput(true);
        converters.add(new MappingJackson2XmlHttpMessageConverter(builder.build()));*/

        converters.add(0,fastJsonHttpMessageConverter);
        for (HttpMessageConverter<?> messageConverter : converters) {
            LOGGER.info ("转换器："+messageConverter);
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

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(getIpInterceptor);
        super.addInterceptors(registry);
    }

    @Bean
    public static RefreshScope refreshScope() {
        RefreshScope refresh = new RefreshScope();
        refresh.setId("application:gtmap");
        return refresh;
    }
}
