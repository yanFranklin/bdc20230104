package cn.gtmap.realestate.register.ui;

import cn.gtmap.realestate.common.core.service.Impl.HttpClientServiceImpl;
import cn.gtmap.gtc.starter.gscas.GTMapSecCloudApplication;
import cn.gtmap.realestate.common.config.FilterCustom;
import cn.gtmap.realestate.common.core.support.i18n.SpringMessageProvider;
import cn.gtmap.realestate.common.core.support.spring.ControllerExceptionHandler;
import cn.gtmap.realestate.common.core.support.spring.TsHtmlFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/10/30
 * @description 启动类
 */
@ComponentScan(basePackages = "cn.gtmap.realestate",excludeFilters={@ComponentScan.Filter(type = FilterType.CUSTOM, value = FilterCustom.class)})
@GTMapSecCloudApplication(feignClientsPackages = {"cn.gtmap.gtc.storage", "cn.gtmap.realestate.common.core.service.feign", "cn.gtmap.gtc.clients", "cn.gtmap.gtc.workflow.clients", "cn.gtmap.gtc.formclient"})
@Import({ControllerExceptionHandler.class, SpringMessageProvider.class, TsHtmlFilter.class, HttpClientServiceImpl.class})
@DependsOn("automaticLoadingConfig")
@EnableRedisHttpSession
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
