package cn.gtmap.realestate.etl;

import cn.gtmap.gtc.starter.gscas.GTMapSecCloudApplication;
import cn.gtmap.realestate.common.config.FilterPdfCustom;
import cn.gtmap.realestate.common.config.interceptor.SendMsgInterceptor;
import cn.gtmap.realestate.common.core.support.i18n.SpringMessageProvider;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.core.support.spring.ControllerExceptionHandler;
import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;
import cn.gtmap.realestate.common.core.support.thread.ThreadEngine;
import cn.gtmap.realestate.common.matcher.FormStateClientMatcher;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * @author  <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @description portal应用入口类
 */
@ComponentScan(basePackages = "cn.gtmap.realestate", excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {SendMsgInterceptor.class}),
        @ComponentScan.Filter(type = FilterType.CUSTOM, classes = {FilterPdfCustom.class}),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {FormStateClientMatcher.class})})
@GTMapSecCloudApplication(feignClientsPackages = {"cn.gtmap.realestate.common.core.service.feign", "cn.gtmap.gtc.clients", "cn.gtmap.gtc.workflow", "cn.gtmap.gtc.storage", "cn.gtmap.gtc.msg.client"})
@Import({ControllerExceptionHandler.class, Container.class, SpringMessageProvider.class,
        ThreadEngine.class, EnvironmentConfig.class})
@EnableAutoConfiguration(exclude = {
        JpaRepositoriesAutoConfiguration.class//禁止springboot自动加载持久化bean
})
@MapperScan("cn.gtmap.realestate.etl.core.mapper")
@DependsOn("automaticLoadingConfig")
public class EtlApp {

    public static void main(String[] args) {
        SpringApplication.run(EtlApp.class, args);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
