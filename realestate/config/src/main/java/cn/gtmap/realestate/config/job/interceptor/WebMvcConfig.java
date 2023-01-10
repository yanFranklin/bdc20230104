//package cn.gtmap.realestate.config.job.interceptor;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.format.FormatterRegistry;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.validation.MessageCodesResolver;
//import org.springframework.validation.Validator;
//import org.springframework.web.method.support.HandlerMethodArgumentResolver;
//import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
//import org.springframework.web.servlet.HandlerExceptionResolver;
//import org.springframework.web.servlet.config.annotation.*;
//
//import javax.annotation.Resource;
//import java.util.List;
//
///**
// * web mvc config
// *
// * @author  2018-04-02 20:48:20
// */
//@Configuration
//public class WebMvcConfig implements WebMvcConfigurer {
//
//    @Resource
//    private PermissionInterceptor permissionInterceptor;
//    @Resource
//    private CookieInterceptor cookieInterceptor;
//
//    @Override
//    public void configurePathMatch(PathMatchConfigurer configurer) {
//
//    }
//
//    @Override
//    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
//
//    }
//
//    @Override
//    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
//
//    }
//
//    @Override
//    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
//
//    }
//
//    @Override
//    public void addFormatters(FormatterRegistry registry) {
//
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(permissionInterceptor).addPathPatterns("/**");
//        registry.addInterceptor(cookieInterceptor).addPathPatterns("/**");
//    }
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//
//    }
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//
//    }
//
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//
//    }
//
//    @Override
//    public void configureViewResolvers(ViewResolverRegistry registry) {
//
//    }
//
//    @Override
//    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
//
//    }
//
//    @Override
//    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
//
//    }
//
//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//
//    }
//
//    @Override
//    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//
//    }
//
//    @Override
//    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
//
//    }
//
//    @Override
//    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
//
//    }
//
//    @Override
//    public Validator getValidator() {
//        return null;
//    }
//
//    @Override
//    public MessageCodesResolver getMessageCodesResolver() {
//        return null;
//    }
//
//}