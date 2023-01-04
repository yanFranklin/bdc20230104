package cn.gtmap.realestate.certificate;

import cn.gtmap.gtc.starter.gscas.GTMapSecCloudApplication;
import cn.gtmap.realestate.common.config.FilterPdfCustom;
import cn.gtmap.realestate.common.config.interceptor.SendMsgInterceptor;
import cn.gtmap.realestate.common.core.service.Impl.HttpClientServiceImpl;
import cn.gtmap.realestate.common.core.support.i18n.SpringMessageProvider;
import cn.gtmap.realestate.common.core.support.spring.ControllerExceptionHandler;
import cn.gtmap.realestate.common.core.support.thread.ThreadEngine;
import cn.gtmap.realestate.common.util.EntityZdConvertUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.common.util.redisson.RedissonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

/**
 * 加载入口
 * @author <a href="mailto:lisongtao@gtmap.cn">lst</a>
 * @version V1.0, 2018/10/30 15:36
 */
@ComponentScan(basePackages = "cn.gtmap.realestate", excludeFilters = {@ComponentScan.Filter(type = FilterType.CUSTOM, classes = {FilterPdfCustom.class}),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {SendMsgInterceptor.class}),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {HttpClientServiceImpl.class})})
@GTMapSecCloudApplication(feignClientsPackages = {"cn.gtmap.realestate.common.core.service.feign"
        , "cn.gtmap.gtc.clients", "cn.gtmap.gtc.workflow", "cn.gtmap.gtc.storage.clients", "cn.gtmap.gtc.formclient"})
@Import({ControllerExceptionHandler.class,RedissonConfig.class,SpringMessageProvider.class, ThreadEngine.class, EntityZdConvertUtils.class, UserManagerUtils.class})
@EnableConfigurationProperties({LiquibaseProperties.class })
public class CertificateApp {

    /**
     * 系统加载
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(CertificateApp.class, args);
    }

}
