package cn.gtmap.realestate.check;

import cn.gtmap.gtc.starter.gscas.GTMapSecCloudApplication;
import cn.gtmap.realestate.common.config.FilterPdfCustom;
import cn.gtmap.realestate.common.config.interceptor.SendMsgInterceptor;
import cn.gtmap.realestate.common.config.mq.config.RabbitMqConfigMatcher;
import cn.gtmap.realestate.common.core.support.i18n.SpringMessageProvider;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.core.support.spring.ControllerExceptionHandler;
import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;
import cn.gtmap.realestate.common.core.support.spring.TsHtmlFilter;
import cn.gtmap.realestate.common.util.EntityZdConvertUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.common.util.WorkFlowUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author  <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @description check应用入口类
 */
@ComponentScan(basePackages = "cn.gtmap.realestate", excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {SendMsgInterceptor.class}),
        @ComponentScan.Filter(type = FilterType.CUSTOM, classes = {FilterPdfCustom.class})})
@GTMapSecCloudApplication(feignClientsPackages = {"cn.gtmap.gtc.storage.clients","cn.gtmap.gtc.formclient.common.client","cn.gtmap.realestate.common.core.service.feign", "cn.gtmap.gtc.clients", "cn.gtmap.gtc.workflow.clients", "cn.gtmap.gtc.storage","cn.gtmap.gtc.msg.client", "cn.gtmap.gtc.formclient"})
@Import({TsHtmlFilter.class,ControllerExceptionHandler.class,EnvironmentConfig.class,Container.class, SpringMessageProvider.class, UserManagerUtils.class, RabbitMqConfigMatcher.class, WorkFlowUtils.class, EntityZdConvertUtils.class})
@EnableAsync
@DependsOn("automaticLoadingConfig")
public class CheckApp {
    /**
     * 启动类
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(CheckApp.class, args);
    }

}
