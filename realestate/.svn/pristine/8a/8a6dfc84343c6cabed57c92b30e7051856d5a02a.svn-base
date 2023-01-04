package cn.gtmap.realestate.portal.ui;

import cn.gtmap.gtc.starter.gscas.GTMapSecCloudApplication;
import cn.gtmap.realestate.common.config.FilterCustom;
import cn.gtmap.realestate.common.config.FilterPdfCustom;
import cn.gtmap.realestate.common.config.mq.config.RabbitMqConfigMatcher;
import cn.gtmap.realestate.common.core.support.i18n.SpringMessageProvider;
import cn.gtmap.realestate.common.core.support.spring.ControllerExceptionHandler;
import cn.gtmap.realestate.common.core.support.thread.ThreadEngine;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author  <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @description portal-ui应用入口类
 */
@ComponentScan(basePackages = "cn.gtmap.realestate", excludeFilters = {
        @ComponentScan.Filter(type = FilterType.CUSTOM, value = FilterCustom.class),
       })
@GTMapSecCloudApplication(feignClientsPackages = {"cn.gtmap.gtc.workflow.clients.utils", "cn.gtmap.gtc.storage.clients", "cn.gtmap.gtc.formclient.common.client", "cn.gtmap.realestate.common.core.service.feign", "cn.gtmap.gtc.clients", "cn.gtmap.gtc.workflow.clients", "cn.gtmap.gtc.storage", "cn.gtmap.gtc.msg.client"})
@Import({ControllerExceptionHandler.class, ThreadEngine.class, SpringMessageProvider.class, RabbitMqConfigMatcher.class})
@DependsOn("automaticLoadingConfig")
@EnableRedisHttpSession
public class PortalUIApp {
    /**
     * 启动类
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(PortalUIApp.class, args);
    }

}
