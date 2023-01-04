package cn.gtmap.realestate.exchange;

import cn.gtmap.gtc.starter.gscas.GTMapSecCloudApplication;
import cn.gtmap.realestate.common.config.interceptor.SendMsgInterceptor;
import cn.gtmap.realestate.common.core.support.i18n.SpringMessageProvider;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.core.support.spring.ControllerExceptionHandler;
import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;
import cn.gtmap.realestate.common.core.support.thread.ThreadEngine;
import cn.gtmap.realestate.exchange.config.ExOAuth2FeignRequestInterceptor;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.client.RestTemplate;


/**
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @description exchange 应用入口类
 */
@ComponentScan(basePackages = "cn.gtmap.realestate", excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {SendMsgInterceptor.class})})
@GTMapSecCloudApplication(feignClientsPackages = {"cn.gtmap.realestate.common.core.service.feign", "cn.gtmap.gtc.clients", "cn.gtmap.gtc.workflow", "cn.gtmap.gtc.storage","cn.gtmap.gtc.msg.client", "cn.gtmap.gtc.formclient"})
@Import({ControllerExceptionHandler.class,Container.class, SpringMessageProvider.class,
        ThreadEngine.class, EnvironmentConfig.class})
@EnableAutoConfiguration(exclude = {
        JpaRepositoriesAutoConfiguration.class//禁止springboot自动加载持久化bean
})
@EnableScheduling
@DependsOn("automaticLoadingConfig")
@EnableRedisHttpSession
public class ExchangeApp {

    public static void main(String[] args) {
        SpringApplication.run(ExchangeApp.class, args);
    }

    @Bean(name = "exchangeRestTemplate")
    public RestTemplate exchangeRestTemplate(){
        return new RestTemplate();
    }
    @Bean(name = "restTemplate")
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
    @Bean({"gtOAuth2FeignRequestInterceptor"})
    public RequestInterceptor gtOAuth2FeignRequestInterceptor(@Qualifier("paascloudOAuth2RestTemplate") OAuth2RestTemplate oAuth2RestTemplate,OAuth2ProtectedResourceDetails details) {
        return new ExOAuth2FeignRequestInterceptor(oAuth2RestTemplate, details);
    }

    @Bean
    OAuth2RestTemplate oAuth2RestTemplate(OAuth2ClientContext oAuth2ClientContext, OAuth2ProtectedResourceDetails details){
        return new OAuth2RestTemplate(details,oAuth2ClientContext);
    }
}
