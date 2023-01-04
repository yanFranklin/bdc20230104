package cn.gtmap.realestate.etl;

import cn.gtmap.gtc.starter.gscas.GTMapSecCloudApplication;
import cn.gtmap.realestate.common.config.FilterCustom;
import cn.gtmap.realestate.common.config.interceptor.SendMsgInterceptor;
import cn.gtmap.realestate.common.core.support.i18n.SpringMessageProvider;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.core.support.spring.ControllerExceptionHandler;
import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;
import cn.gtmap.realestate.common.core.support.spring.TsHtmlFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * @author  <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @description portal应用入口类
 */
@ComponentScan(basePackages = "cn.gtmap.realestate",excludeFilters={@ComponentScan.Filter(type = FilterType.CUSTOM, value = FilterCustom.class)})
@GTMapSecCloudApplication(feignClientsPackages = {"cn.gtmap.realestate.common.core.service.feign",
        "cn.gtmap.gtc.clients",
        "cn.gtmap.gtc.workflow.clients",
        "cn.gtmap.gtc.storage.clients","cn.gtmap.gtc.formclient"})
@Import({ControllerExceptionHandler.class,Container.class,SpringMessageProvider.class,EnvironmentConfig.class, TsHtmlFilter.class})
@MapperScan("cn.gtmap.realestate.etl.core.mapper")
@DependsOn("automaticLoadingConfig")
public class EtlApp {

    public static void main(String[] args) {
        SpringApplication.run(EtlApp.class, args);
    }

}
