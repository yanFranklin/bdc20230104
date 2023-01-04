package cn.gtmap.realestate.portal.ui.config;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author <a href=""mailto:lisongtao@gtmap.cn>lisongtao</a>
 * @version 1.0, 2019/2/25
 * @description
 */
@Configuration
public class ApiConfigurer extends WebMvcConfigurerAdapter {

    @Value("${user.check.path}")
    private String[] checkPath;

    @Value("${homepage.path:home-page.html}")
    private String homePagePath;

    /**
     * 把我们的拦截器注入为bean
     * @return
     */
    @Bean
    public HandlerInterceptor getMyInterceptor(){
        return new CommonInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if(checkPath!=null && checkPath.length>0){
            registry.addInterceptor(getMyInterceptor()).addPathPatterns(checkPath);
        }
        super.addInterceptors(registry);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/view/index").setViewName("index");
        String homePageRedirectUrl = "home/home-page.html?clientId=initialClientId&moduleCode=1&loadHome=true";
        if(StringUtils.isNotBlank(homePagePath)){
            homePageRedirectUrl = "home/" + homePagePath + "?clientId=initialClientId&moduleCode=1&loadHome=true";
        }
        registry.addRedirectViewController("/home", homePageRedirectUrl);
        super.addViewControllers(registry);
    }
}