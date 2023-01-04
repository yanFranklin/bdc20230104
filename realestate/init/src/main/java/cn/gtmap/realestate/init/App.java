package cn.gtmap.realestate.init;

import cn.gtmap.gtc.starter.gscas.GTMapSecCloudApplication;
import cn.gtmap.realestate.common.config.FilterPdfCustom;
import cn.gtmap.realestate.common.config.interceptor.SendMsgInterceptor;
import cn.gtmap.realestate.common.config.logaop.LogApiAspect;
import cn.gtmap.realestate.common.config.logaop.LogApiOprationAspect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.FilterType;

/**
 * 加载入口
 * @author <a href="mailto:lisongtao@gtmap.cn">lst</a>
 * @version V1.0, 2018/10/30 15:36
 */
@ComponentScan(basePackages = "cn.gtmap.realestate", excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {SendMsgInterceptor.class, LogApiAspect.class, LogApiOprationAspect.class}),
        @ComponentScan.Filter(type = FilterType.CUSTOM, classes = {FilterPdfCustom.class})})
@GTMapSecCloudApplication(feignClientsPackages = {"cn.gtmap.realestate.common.core.service.feign", "cn.gtmap.gtc.workflow.clients", "cn.gtmap.gtc.clients", "cn.gtmap.gtc.storage.clients", "cn.gtmap.gtc.formclient"})
@EnableConfigurationProperties({LiquibaseProperties.class})
@DependsOn("automaticLoadingConfig")
public class App {

    /**
     * 系统加载
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
