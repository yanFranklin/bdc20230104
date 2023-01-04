package cn.gtmap.realestate.register;

import cn.gtmap.gtc.starter.gscas.GTMapSecCloudApplication;
import cn.gtmap.realestate.common.config.mq.Config.MQBindingConfig;
import cn.gtmap.realestate.common.config.mq.Config.MQConfig;
import cn.gtmap.realestate.common.config.mq.Config.MQExchangeConfig;
import cn.gtmap.realestate.common.config.mq.Config.MQQueueConfig;
import cn.gtmap.realestate.common.core.support.i18n.SpringMessageProvider;
import cn.gtmap.realestate.common.core.support.spring.ControllerExceptionHandler;
import cn.gtmap.realestate.common.core.support.thread.ThreadEngine;
import cn.gtmap.realestate.common.matcher.MessageMatcher;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

/**
 * . Demo Cloud App
 *
 * @author <a href="mailto:lanxy88@gmail.com">NelsonXu</a>
 * @version V1.0, 2017/10/9 15:36
 */
@ComponentScan(basePackages = "cn.gtmap.realestate")
@EnableAsync
@GTMapSecCloudApplication(feignClientsPackages = {"cn.gtmap.realestate.common.core.service.feign", "cn.gtmap.gtc.clients", "cn.gtmap.gtc.workflow", "cn.gtmap.gtc.storage.clients.v1", "cn.gtmap.gtc.clients","cn.gtmap.gtc.formclient"})
@Import({ControllerExceptionHandler.class, SpringMessageProvider.class, ThreadEngine.class, MQConfig.class, MQExchangeConfig.class, MQQueueConfig.class, MQBindingConfig.class, MessageMatcher.class})
@MapperScan("cn.gtmap.realestate.register.core.mapper")
@DependsOn("automaticLoadingConfig")
public class App {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
