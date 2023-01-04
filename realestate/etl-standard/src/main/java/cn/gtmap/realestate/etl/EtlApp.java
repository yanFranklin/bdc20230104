package cn.gtmap.realestate.etl;

import cn.gtmap.gtc.starter.gscas.GTMapSecCloudApplication;
import cn.gtmap.realestate.common.config.FilterCustom;
import cn.gtmap.realestate.common.config.interceptor.SendMsgInterceptor;
import cn.gtmap.realestate.common.core.support.i18n.SpringMessageProvider;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.core.support.spring.ControllerExceptionHandler;
import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;
import cn.gtmap.realestate.common.core.support.spring.TsHtmlFilter;
import cn.gtmap.realestate.common.util.EntityZdConvertUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @description portal应用入口类
 */
@ComponentScan(basePackages = "cn.gtmap.realestate", excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {SendMsgInterceptor.class})})
@GTMapSecCloudApplication(feignClientsPackages = {"cn.gtmap.realestate.common.core.service.feign",
        "cn.gtmap.gtc.clients",
        "cn.gtmap.gtc.storage",
        "cn.gtmap.gtc.workflow.clients",
        "cn.gtmap.gtc.formclient.common.client"})
@Import({ControllerExceptionHandler.class, Container.class, SpringMessageProvider.class, EnvironmentConfig.class, EntityZdConvertUtils.class, TsHtmlFilter.class})
@DependsOn("automaticLoadingConfig")
public class EtlApp {

    public static void main(String[] args) {
        SpringApplication.run(EtlApp.class, args);
    }


}
