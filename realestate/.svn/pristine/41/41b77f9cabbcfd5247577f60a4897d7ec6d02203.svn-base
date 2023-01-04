package cn.gtmap.realestate.accept;

import cn.gtmap.gtc.starter.gscas.GTMapSecCloudApplication;
import cn.gtmap.realestate.common.config.interceptor.SendMsgInterceptor;
import cn.gtmap.realestate.common.core.support.i18n.SpringMessageProvider;
import cn.gtmap.realestate.common.core.support.spring.ControllerExceptionHandler;
import cn.gtmap.realestate.common.core.support.thread.ThreadEngine;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/10/30
 * @description
 */
@ComponentScan(basePackages = "cn.gtmap.realestate", excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {SendMsgInterceptor.class})})
@GTMapSecCloudApplication(feignClientsPackages = {"cn.gtmap.realestate.common.core.service.feign", "cn.gtmap.gtc.clients","cn.gtmap.gtc.workflow.clients","cn.gtmap.gtc.storage.clients", "cn.gtmap.gtc.formclient.common.client"})
@Import({ControllerExceptionHandler.class, SpringMessageProvider.class, ThreadEngine.class})
@MapperScan("cn.gtmap.realestate.accept.core.mapper")
@DependsOn("automaticLoadingConfig")
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
