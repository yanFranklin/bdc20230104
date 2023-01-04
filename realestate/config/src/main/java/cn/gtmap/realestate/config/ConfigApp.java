package cn.gtmap.realestate.config;

import cn.gtmap.gtc.starter.gscas.GTMapSecCloudApplication;
import cn.gtmap.realestate.common.config.FilterPdfCustom;
import cn.gtmap.realestate.common.config.interceptor.SendMsgInterceptor;
import cn.gtmap.realestate.common.core.support.i18n.SpringMessageProvider;
import cn.gtmap.realestate.common.core.support.spring.ControllerExceptionHandler;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

/**
 * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @description config配置子系统入口类
 */
@ComponentScan(basePackages = "cn.gtmap.realestate", excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {SendMsgInterceptor.class}),
        @ComponentScan.Filter(type = FilterType.CUSTOM, classes = {FilterPdfCustom.class})})
@GTMapSecCloudApplication(feignClientsPackages = {"cn.gtmap.realestate.common.core.service.feign", "cn.gtmap.gtc.clients", "cn.gtmap.gtc.workflow", "cn.gtmap.gtc.storage.clients", "cn.gtmap.gtc.formclient"})
@Import({ControllerExceptionHandler.class, SpringMessageProvider.class})
@MapperScan("cn.gtmap.realestate.config.core.mapper")
@DependsOn("automaticLoadingConfig")
public class ConfigApp {

    public static void main(String[] args) {
        SpringApplication.run(ConfigApp.class, args);
    }
}
