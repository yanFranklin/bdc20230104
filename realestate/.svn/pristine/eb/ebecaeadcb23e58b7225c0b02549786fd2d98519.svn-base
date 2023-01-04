package cn.gtmap.realestate.accept.ui;

import cn.gtmap.gtc.starter.gscas.GTMapSecCloudApplication;
import cn.gtmap.realestate.common.config.FilterCustom;
import cn.gtmap.realestate.common.core.service.Impl.HttpClientServiceImpl;
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
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/10/30
 * @description
 */
@ComponentScan(basePackages = "cn.gtmap.realestate",excludeFilters={@ComponentScan.Filter(type = FilterType.CUSTOM, value = FilterCustom.class)})
@GTMapSecCloudApplication(feignClientsPackages = {"cn.gtmap.realestate.common.core.service.feign", "cn.gtmap.gtc.clients","cn.gtmap.gtc.workflow","cn.gtmap.gtc.storage.clients", "cn.gtmap.gtc.formclient.common.client"})
@Import({ControllerExceptionHandler.class, SpringMessageProvider.class,TsHtmlFilter.class, HttpClientServiceImpl.class})
@DependsOn("automaticLoadingConfig")
@EnableRedisHttpSession
public class AcceptUIApp {

    public static void main(String[] args) {
        SpringApplication.run(AcceptUIApp.class, args);
    }

}
