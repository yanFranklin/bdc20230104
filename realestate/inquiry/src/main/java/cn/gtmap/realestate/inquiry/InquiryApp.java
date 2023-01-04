package cn.gtmap.realestate.inquiry;

import cn.gtmap.gtc.starter.gscas.GTMapSecCloudApplication;
import cn.gtmap.realestate.common.config.interceptor.SendMsgInterceptor;
import cn.gtmap.realestate.common.core.support.i18n.SpringMessageProvider;
import cn.gtmap.realestate.common.core.support.spring.ControllerExceptionHandler;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/05/15
 * @description 不动产登记3.0查询子系统V2.0
 */
@ComponentScan(basePackages = "cn.gtmap.realestate", excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {SendMsgInterceptor.class})})
@GTMapSecCloudApplication(feignClientsPackages = {"cn.gtmap.realestate.common.core.service.feign","cn.gtmap.gtc.clients","cn.gtmap.gtc.workflow","cn.gtmap.gtc.storage.clients", "cn.gtmap.gtc.formclient"})
@Import({ControllerExceptionHandler.class,SpringMessageProvider.class})
@MapperScan("cn.gtmap.realestate.inquiry.core.mapper")
@DependsOn("automaticLoadingConfig")
public class InquiryApp{
    public static void main(String[] args){
        SpringApplication.run(InquiryApp.class,args);
    }
}

