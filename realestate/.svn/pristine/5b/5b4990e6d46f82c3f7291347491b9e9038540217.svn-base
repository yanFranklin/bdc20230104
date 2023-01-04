package cn.gtmap.realestate.etl.config;

import cn.gtmap.realestate.common.core.support.spring.LayuiPageableArgumentResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * @program: realestate
 * @description: 配置信息
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-12-14 18:14
 **/
@Configuration
public class WebConfigBeans extends WebMvcConfigurerAdapter {
    @Value("${print.path:/usr/local/bdc3/print/}")
    private String printPath;

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


    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 打印地址跳转
     * @date : 2020/12/7 16:01
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //etl包没有contextpath。跳转地址要加上包名
        registry.addResourceHandler("/realestate-etl/static/printmodel/**")
                .addResourceLocations("file:" + printPath);
    }
}
