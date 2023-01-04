package cn.gtmap.realestate.inquiry.ui;

import cn.gtmap.gtc.starter.gscas.GTMapSecCloudApplication;
import cn.gtmap.realestate.common.config.FilterCustom;
import cn.gtmap.realestate.common.core.service.Impl.HttpClientServiceImpl;
import cn.gtmap.realestate.common.core.support.i18n.SpringMessageProvider;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.core.support.spring.ControllerExceptionHandler;
import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;
import cn.gtmap.realestate.common.core.support.spring.TsHtmlFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/05/16
 * @description 不动产登记3.0查询子系统UI
 */
@ComponentScan(basePackages = "cn.gtmap.realestate",excludeFilters={@ComponentScan.Filter(type = FilterType.CUSTOM, value = FilterCustom.class)})
@GTMapSecCloudApplication(feignClientsPackages = {"cn.gtmap.realestate.common.core.service.feign", "cn.gtmap.gtc.clients","cn.gtmap.gtc.workflow.clients","cn.gtmap.gtc.storage.clients", "cn.gtmap.gtc.formclient.common.client"})
@Import({TsHtmlFilter.class, ControllerExceptionHandler.class, SpringMessageProvider.class, Container.class, HttpClientServiceImpl.class,
        EnvironmentConfig.class})
@DependsOn("automaticLoadingConfig")
@EnableRedisHttpSession
@EnableScheduling
public class InquiryUIApp {
    public static void main(String[] args) {
        SpringApplication.run(InquiryUIApp.class, args);
    }
}
