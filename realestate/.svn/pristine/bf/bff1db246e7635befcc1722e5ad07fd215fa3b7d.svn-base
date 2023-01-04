package cn.gtmap.realestate.building.ui;

import cn.gtmap.gtc.starter.gscas.GTMapSecCloudApplication;
import cn.gtmap.realestate.common.config.FilterCustom;
import cn.gtmap.realestate.common.core.support.gxhpz.CommonGxhpzController;
import cn.gtmap.realestate.common.core.support.i18n.SpringMessageProvider;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.core.support.spring.ControllerExceptionHandler;
import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;
import cn.gtmap.realestate.common.core.support.thread.ThreadEngine;
import cn.gtmap.realestate.common.util.RedisUtils;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

/**
 * 楼盘表管理系统
 *
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version V1.0, 2017/10/9 15:36
 */
@GTMapSecCloudApplication(feignClientsPackages = {"cn.gtmap.gtc.clients"
        , "cn.gtmap.realestate.common.core.service.feign"
        , "cn.gtmap.gtc.storage.clients"
        , "cn.gtmap.gtc.workflow.clients","cn.gtmap.gtc.formclient"})
@Import({ControllerExceptionHandler.class,
        SpringMessageProvider.class,
        Container.class, EnvironmentConfig.class, ThreadEngine.class
        , UserManagerUtils.class, RedisUtils.class})
@ComponentScan(basePackages = "cn.gtmap.realestate",excludeFilters={@ComponentScan.Filter(type = FilterType.CUSTOM, value = FilterCustom.class),@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {CommonGxhpzController.class})})
@DependsOn("automaticLoadingConfig")
public class BuildIngUiApp {

    /**
     * @param args
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 主方法
     */
    public static void main(String[] args) {
        SpringApplication.run(BuildIngUiApp.class, args);
    }

}
