package cn.gtmap.realestate.supervise;

import cn.gtmap.gtc.msg.rabbitmq.produce.MessageProducer;
import cn.gtmap.gtc.starter.gscas.GTMapSecCloudApplication;
import cn.gtmap.realestate.common.config.FilterPdfCustom;
import cn.gtmap.realestate.common.config.interceptor.SendMsgInterceptor;
import cn.gtmap.realestate.common.config.mq.Config.MQBindingConfig;
import cn.gtmap.realestate.common.config.mq.Config.MQConfig;
import cn.gtmap.realestate.common.config.mq.Config.MQExchangeConfig;
import cn.gtmap.realestate.common.config.mq.Config.MQQueueConfig;
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
 * 盐城市区不动产登记廉防体系信息化平台 应用
 * @version V1.0, 2021/08/24 10:05
 * @author <a href="mailto:zhuyong@gmail.com">zhuyong</a>
 */
@ComponentScan(basePackages = "cn.gtmap.realestate", excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {SendMsgInterceptor.class}),
        @ComponentScan.Filter(type = FilterType.CUSTOM, classes = {FilterPdfCustom.class})})
@GTMapSecCloudApplication(feignClientsPackages = {"cn.gtmap.realestate.common.core.service.feign", "cn.gtmap.gtc.clients", "cn.gtmap.gtc.workflow", "cn.gtmap.gtc.storage.clients.v1", "cn.gtmap.gtc.clients","cn.gtmap.gtc.formclient"})
@Import({ControllerExceptionHandler.class, SpringMessageProvider.class, ThreadEngine.class, MQConfig.class, MQExchangeConfig.class, MQQueueConfig.class, MQBindingConfig.class, MessageProducer.class})
@MapperScan("cn.gtmap.realestate.supervise.core.mapper")
@DependsOn("automaticLoadingConfig")
public class SuperviseApplication {

    public static void main(String[] args) {
        SpringApplication.run(SuperviseApplication.class, args);
    }

}
