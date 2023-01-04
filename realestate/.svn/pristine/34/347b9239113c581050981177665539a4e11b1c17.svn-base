package cn.gtmap.realestate.building;

import cn.gtmap.gtc.starter.gscas.GTMapSecCloudApplication;
import cn.gtmap.realestate.common.config.FilterPdfCustom;
import cn.gtmap.realestate.common.config.interceptor.SendMsgInterceptor;
import cn.gtmap.realestate.common.config.mq.Config.MQBindingConfig;
import cn.gtmap.realestate.common.config.mq.Config.MQExchangeConfig;
import cn.gtmap.realestate.common.config.mq.Config.MQQueueConfig;
import cn.gtmap.realestate.common.config.mq.consumer.MQConsumerFactory;
import cn.gtmap.realestate.common.core.service.EntityService;
import cn.gtmap.realestate.common.core.service.Impl.EntityServiceImpl;
import cn.gtmap.realestate.common.core.support.i18n.SpringMessageProvider;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.core.support.spring.ControllerExceptionHandler;
import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;
import cn.gtmap.realestate.common.core.support.thread.ThreadEngine;
import cn.gtmap.realestate.common.util.EntityZdConvertUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 楼盘表管理系统
 *
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version V1.0, 2017/10/9 15:36
 */
@GTMapSecCloudApplication(feignClientsPackages = {"cn.gtmap.gtc.clients"
        , "cn.gtmap.realestate.common.core.service.feign"
        , "cn.gtmap.gtc.storage.clients","cn.gtmap.realestate.common.config","cn.gtmap.gtc.workflow.clients","cn.gtmap.gtc.category.client","cn.gtmap.gtc.formclient"})
@Import({EntityZdConvertUtils.class,ControllerExceptionHandler.class,
        SpringMessageProvider.class, Container.class, EnvironmentConfig.class,
        ThreadEngine.class, MQConsumerFactory.class, MQQueueConfig.class, MQExchangeConfig.class,
        MQBindingConfig.class, UserManagerUtils.class
})
@MapperScan("cn.gtmap.realestate.building.core.mapper")
@EnableSwagger2
@EnableRabbit
@ComponentScan(basePackages = "cn.gtmap.realestate", excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {SendMsgInterceptor.class}),
        @ComponentScan.Filter(type = FilterType.CUSTOM, classes = {FilterPdfCustom.class})})
@DependsOn("automaticLoadingConfig")
public class App {

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param args
     * @return void
     * @description 主方法
     */
    public static void main(String[] args) { SpringApplication.run(App.class, args); }

    @Bean
    public EntityService entityService(){
        return new EntityServiceImpl();
    }
}
